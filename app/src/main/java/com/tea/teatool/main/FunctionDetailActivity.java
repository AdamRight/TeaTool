package com.tea.teatool.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tea.teatool.R;
import com.tea.teatool.arcspeed.ArcSpeedActivity;
import com.tea.teatool.circleload.CircleLoadingActivity;
import com.tea.teatool.cliptext.ClipTextActivity;
import com.tea.teatool.dragmsg.DragMsgActivity;
import com.tea.teatool.flow.FlowActivity;
import com.tea.teatool.iocannotate.IOCAnnotateActivity;
import com.tea.teatool.jsconnect.JsConnectActivity;
import com.tea.teatool.keyboard.KeyBoardActivity;
import com.tea.teatool.kotlin.KotlinTestActivity;
import com.tea.teatool.letterindex.LetterIndexActivity;
import com.tea.teatool.loadingball.BallRotateActivity;
import com.tea.teatool.loadingshape.ShapeLoadActivity;
import com.tea.teatool.mazelock.MazeLockActivity;
import com.tea.teatool.mvp.MvpActivity;
import com.tea.teatool.parallax.ParallaxActivity;
import com.tea.teatool.pathMenu.PathMenuActivity;
import com.tea.teatool.rv.baseuse.BaseUseRVActivity;
import com.tea.teatool.rv.headandbottom.AddHeadRecyclerViewActivity;
import com.tea.teatool.rv.type.TypeRVActivity;
import com.tea.teatool.slidingmenu.SlidingMenuActivity;
import com.tea.teatool.spanclick.SpannableClickActivity;
import com.tea.teatool.tabmenu.TabMenuActivity;
import com.tea.teatool.teabutterknife.ButterknifeActivity;
import com.tea.teatool.teaeventbus.TeaEventBusActivity;
import com.tea.teatool.teahttp.TeaHttpActivity;
import com.tea.teatool.teahttp.TeaOkUpFileActivity;
import com.tea.teatool.tearatingbar.TeaRatingBarActivity;
import com.tea.teatool.tearx.TeaRxActivity;
import com.tea.teatool.teatextview.TeaTextViewActivity;
import com.tea.teatool.thumbup.ThumbUpActivity;
import com.tea.teatool.verticaldrag.VerticalDragActivity;
import com.tea.teatool.webshop.WebShopActivity;

import java.util.ArrayList;

public class FunctionDetailActivity extends AppCompatActivity implements MainAdapter.OnItemClickListener {

    public static void startDetailActivity(Context context, int functionType) {
        Intent intent = new Intent(context, FunctionDetailActivity.class);
        intent.putExtra("functionType", functionType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        generateDatas();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        MainAdapter adapter = new MainAdapter(this, mDatas);
        adapter.setItemClickListener(this);

        recyclerView.setAdapter(adapter);

    }

    private ArrayList<MainListBean> mDatas = new ArrayList<>();

    private void generateDatas() {
        mDatas.clear();
        Intent intent = getIntent();
        int functionType = intent.getIntExtra("functionType", 1);
        switch (functionType) {
            case 1:
                mDatas.add(new MainListBean("手动实现TextView", TeaTextViewActivity.class));
                mDatas.add(new MainListBean("自定义圆弧加载", ArcSpeedActivity.class));
                mDatas.add(new MainListBean("自定义文字变色", ClipTextActivity.class));
                mDatas.add(new MainListBean("自定义星星评分", TeaRatingBarActivity.class));
                mDatas.add(new MainListBean("自定义字母索引", LetterIndexActivity.class));
                mDatas.add(new MainListBean("自定义九宫格解锁", MazeLockActivity.class));
                mDatas.add(new MainListBean("自定义图形变化加载", ShapeLoadActivity.class));
                mDatas.add(new MainListBean("自定义列表的下拉抽屉", VerticalDragActivity.class));
                mDatas.add(new MainListBean("自定义侧滑菜单", SlidingMenuActivity.class));
                mDatas.add(new MainListBean("自定义tab菜单筛选", TabMenuActivity.class));
                mDatas.add(new MainListBean("自定义三圆点加载", CircleLoadingActivity.class));
                mDatas.add(new MainListBean("自定义消息拖拽", DragMsgActivity.class));
                mDatas.add(new MainListBean("自定义心心点赞", ThumbUpActivity.class));
                mDatas.add(new MainListBean("自定义引导页视差动画", ParallaxActivity.class));
                mDatas.add(new MainListBean("自定义小球旋转加载动画", BallRotateActivity.class));
                break;
            case 2:
                mDatas.add(new MainListBean("菜单展开路径动画", PathMenuActivity.class));
                break;
            case 3:
                mDatas.add(new MainListBean("手写实现Butterknife", ButterknifeActivity.class));
                mDatas.add(new MainListBean("手写实现EventBus", TeaEventBusActivity.class));
                mDatas.add(new MainListBean("造轮子OkHttp", TeaHttpActivity.class));
                mDatas.add(new MainListBean("OkHttp上传文件", TeaOkUpFileActivity.class));
                mDatas.add(new MainListBean("手写IOC注解", IOCAnnotateActivity.class));
                mDatas.add(new MainListBean("MVP小demo", MvpActivity.class));
                mDatas.add(new MainListBean("RecyclerView基本使用", BaseUseRVActivity.class));
                mDatas.add(new MainListBean("多种类型条目RecyclerView", TypeRVActivity.class));
                mDatas.add(new MainListBean("给RecyclerView添加头部", AddHeadRecyclerViewActivity.class));
                mDatas.add(new MainListBean("Kotlin", KotlinTestActivity.class));
                mDatas.add(new MainListBean("手写实现RxJava", TeaRxActivity.class));
                break;
            case 4:
                mDatas.add(new MainListBean("webshop", WebShopActivity.class));
                mDatas.add(new MainListBean("js相互传参", JsConnectActivity.class));
                break;
            case 5:
                mDatas.add(new MainListBean("自定义键盘", KeyBoardActivity.class));
                mDatas.add(new MainListBean("自定义流式布局", FlowActivity.class));
                mDatas.add(new MainListBean("spannable部分文字多个点击事件", SpannableClickActivity.class));
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        startActivity(new Intent(this, mDatas.get(position).getActvity()));
    }
}
