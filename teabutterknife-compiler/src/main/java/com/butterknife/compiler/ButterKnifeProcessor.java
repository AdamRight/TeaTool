package com.butterknife.compiler;

import com.butterknife.annotations.BindView;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.sun.source.tree.ClassTree;
import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeScanner;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


/**
 * Created by jiangtea on 2019/6/29.
 */
@AutoService(Processor.class)
public class ButterKnifeProcessor extends AbstractProcessor {

    private Filer mFiler;
    private Elements mElementUtils;
    private Trees trees;
    private Types mTypeUtils;
    private final Map<QualifiedId, Id> symbols = new LinkedHashMap<>();

    private static final List<String> SUPPORTED_TYPES = Arrays.asList(
            "array", "attr", "bool", "color", "dimen", "drawable", "id", "integer", "string"
    );
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mElementUtils = processingEnvironment.getElementUtils();
        mTypeUtils = processingEnvironment.getTypeUtils();
        try {
            trees = Trees.instance(processingEnv);
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(BindView.class);
        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        scanForRClasses(roundEnvironment);

        // 封裝到map,键-类名，值-注解的成员变量集合
        Map<Element, List<Element>> analysisElementMap = new LinkedHashMap<>();

        Set<? extends Element> bindViewElements = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        //遍历bindViewElements,得到所有的注解的成员变量
        for (Element bindViewElement : bindViewElements) {
            //键-类名
            Element enclosingElement = bindViewElement.getEnclosingElement();
            //值-注解的成员变量集合
            List<Element> elements = analysisElementMap.get(enclosingElement);
            if (elements == null) {
                elements = new ArrayList<>();
                analysisElementMap.put(enclosingElement, elements);
            }
            elements.add(bindViewElement);
        }

        //生成java类
        for (Map.Entry<Element, List<Element>> entry : analysisElementMap.entrySet()) {

            Element enclosingElement = entry.getKey();
            List<Element> elements = entry.getValue();
            //获取到了原始类名
            String classNameStr = enclosingElement.getSimpleName().toString();
            ClassName parameterClassName = ClassName.bestGuess(classNameStr);
            //获取Unbinder类
            ClassName unbinderClassName = ClassName.get("com.tea.butterknife", "Unbinder");

            // 组装类:  public final class xxx_ViewBinding implements Unbinder
            TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(classNameStr + "_ViewBinding")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addSuperinterface(unbinderClassName);
            //组装 成员变量 :private 类名 target
            typeSpecBuilder.addField(parameterClassName, "target", Modifier.PRIVATE);

            ClassName callSuperClassName = ClassName.get("android.support.annotation", "CallSuper");
            //组装unbind方法
            MethodSpec.Builder unbindMethodBuilder = MethodSpec.methodBuilder("unbind")
                    .addAnnotation(Override.class)
                    .addAnnotation(callSuperClassName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.VOID);

            ClassName uiThreadClassName = ClassName.get("android.support.annotation", "UiThread");
            // 组装构造函数: public xxx_ViewBinding(xxx target)
            MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                    .addAnnotation(uiThreadClassName)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(parameterClassName, "target");

            constructorBuilder.addStatement("this.target = target");
            unbindMethodBuilder.addStatement("$T target = this.target", parameterClassName);
            unbindMethodBuilder.addStatement("if (target == null) throw new IllegalStateException(\"Bindings already cleared.\")");
            unbindMethodBuilder.addStatement("this.target = null");

            // 添加 target.textView1 = Utils.findViewById(target,R.id.tv1);
            for (Element bindViewElement : elements) {
                String filedName = bindViewElement.getSimpleName().toString();
                ClassName utilClassName = ClassName.get("com.tea.butterknife", "Utils");
                int resId = bindViewElement.getAnnotation(BindView.class).value();
                QualifiedId qualifiedId = elementToQualifiedId(bindViewElement, resId);
                Id id = getId(qualifiedId);
                CodeBlock codeBlock = id.code;
                /*CodeBlock codeBlock = CodeBlock.of("$L", resId);
                System.out.println("--------------------------->" + codeBlock.toString());*/
                constructorBuilder.addStatement("target.$L = $T.findViewById(target,$L)",
                        filedName, utilClassName, codeBlock);
                unbindMethodBuilder.addStatement("target.$L = null", filedName);
            }

            typeSpecBuilder.addMethod(constructorBuilder.build());
            typeSpecBuilder.addMethod(unbindMethodBuilder.build());

            try {
                // 写入生成 java 类
                String packageName = mElementUtils.getPackageOf(enclosingElement).getQualifiedName().toString();
                JavaFile.builder(packageName, typeSpecBuilder.build())
                        .addFileComment("TeaButterKnife自动生成")
                        .build().writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("翻车了");
            }
        }


        return false;
    }

    private void scanForRClasses(RoundEnvironment env) {
        if (trees == null) return;

        RClassScanner scanner = new RClassScanner();

        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            for (Element element : env.getElementsAnnotatedWith(annotation)) {
                JCTree tree = (JCTree) trees.getTree(element, getMirror(element, annotation));
                if (tree != null) { // tree can be null if the references are compiled types and not source
                    String respectivePackageName =
                            mElementUtils.getPackageOf(element).getQualifiedName().toString();
                    scanner.setCurrentPackageName(respectivePackageName);
                    tree.accept(scanner);
                }
            }
        }

        for (Map.Entry<String, Set<String>> packageNameToRClassSet : scanner.getRClasses().entrySet()) {
            String respectivePackageName = packageNameToRClassSet.getKey();
            for (String rClass : packageNameToRClassSet.getValue()) {
                parseRClass(respectivePackageName, rClass);
            }
        }
    }

    private void parseRClass(String respectivePackageName, String rClass) {
        Element element;

        try {
            element = mElementUtils.getTypeElement(rClass);
        } catch (MirroredTypeException mte) {
            element = mTypeUtils.asElement(mte.getTypeMirror());
        }

        JCTree tree = (JCTree) trees.getTree(element);
        if (tree != null) { // tree can be null if the references are compiled types and not source
            IdScanner idScanner = new IdScanner(symbols, mElementUtils.getPackageOf(element)
                    .getQualifiedName().toString(), respectivePackageName);
            tree.accept(idScanner);
        } else {
            parseCompiledR(respectivePackageName, (TypeElement) element);
        }
    }

    private void parseCompiledR(String respectivePackageName, TypeElement rClass) {
        for (Element element : rClass.getEnclosedElements()) {
            String innerClassName = element.getSimpleName().toString();
            if (SUPPORTED_TYPES.contains(innerClassName)) {
                for (Element enclosedElement : element.getEnclosedElements()) {
                    if (enclosedElement instanceof VariableElement) {
                        VariableElement variableElement = (VariableElement) enclosedElement;
                        Object value = variableElement.getConstantValue();

                        if (value instanceof Integer) {
                            int id = (Integer) value;
                            ClassName rClassName =
                                    ClassName.get(mElementUtils.getPackageOf(variableElement).toString(), "R",
                                            innerClassName);
                            String resourceName = variableElement.getSimpleName().toString();
                            QualifiedId qualifiedId = new QualifiedId(respectivePackageName, id);
                            symbols.put(qualifiedId, new Id(id, rClassName, resourceName));
                        }
                    }
                }
            }
        }
    }

    private static class IdScanner extends TreeScanner {
        private final Map<QualifiedId, Id> ids;
        private final String rPackageName;
        private final String respectivePackageName;

        IdScanner(Map<QualifiedId, Id> ids, String rPackageName, String respectivePackageName) {
            this.ids = ids;
            this.rPackageName = rPackageName;
            this.respectivePackageName = respectivePackageName;
        }

        @Override
        public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
            for (JCTree tree : jcClassDecl.defs) {
                if (tree instanceof ClassTree) {
                    ClassTree classTree = (ClassTree) tree;
                    String className = classTree.getSimpleName().toString();
                    if (SUPPORTED_TYPES.contains(className)) {
                        ClassName rClassName = ClassName.get(rPackageName, "R", className);
                        VarScanner scanner = new VarScanner(ids, rClassName, respectivePackageName);
                        ((JCTree) classTree).accept(scanner);
                    }
                }
            }
        }
    }

    private static class VarScanner extends TreeScanner {
        private final Map<QualifiedId, Id> ids;
        private final ClassName className;
        private final String respectivePackageName;

        private VarScanner(Map<QualifiedId, Id> ids, ClassName className,
                           String respectivePackageName) {
            this.ids = ids;
            this.className = className;
            this.respectivePackageName = respectivePackageName;
        }

        @Override
        public void visitVarDef(JCTree.JCVariableDecl jcVariableDecl) {
            if ("int".equals(jcVariableDecl.getType().toString())) {
                int id = Integer.valueOf(jcVariableDecl.getInitializer().toString());
                String resourceName = jcVariableDecl.getName().toString();
                QualifiedId qualifiedId = new QualifiedId(respectivePackageName, id);
                ids.put(qualifiedId, new Id(id, className, resourceName));
            }
        }
    }

    private QualifiedId elementToQualifiedId(Element element, int id) {
        return new QualifiedId(mElementUtils.getPackageOf(element).getQualifiedName().toString(), id);
    }

    private static class RClassScanner extends TreeScanner {
        // Maps the currently evaulated rPackageName to R Classes
        private final Map<String, Set<String>> rClasses = new LinkedHashMap<>();
        private String currentPackageName;

        @Override
        public void visitSelect(JCTree.JCFieldAccess jcFieldAccess) {
            Symbol symbol = jcFieldAccess.sym;
            if (symbol != null
                    && symbol.getEnclosingElement() != null
                    && symbol.getEnclosingElement().getEnclosingElement() != null
                    && symbol.getEnclosingElement().getEnclosingElement().enclClass() != null) {
                Set<String> rClassSet = rClasses.get(currentPackageName);
                if (rClassSet == null) {
                    rClassSet = new HashSet<>();
                    rClasses.put(currentPackageName, rClassSet);
                }
                rClassSet.add(symbol.getEnclosingElement().getEnclosingElement().enclClass().className());
            }
        }

        Map<String, Set<String>> getRClasses() {
            return rClasses;
        }

        void setCurrentPackageName(String respectivePackageName) {
            this.currentPackageName = respectivePackageName;
        }
    }

    private static AnnotationMirror getMirror(Element element,
                                              Class<? extends Annotation> annotation) {
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            if (annotationMirror.getAnnotationType().toString().equals(annotation.getCanonicalName())) {
                return annotationMirror;
            }
        }
        return null;
    }

    private Id getId(QualifiedId qualifiedId) {
        if (symbols.get(qualifiedId) == null) {
            symbols.put(qualifiedId, new Id(qualifiedId.id));
        }
        return symbols.get(qualifiedId);
    }
}
