package com.tea.teatool.rv.type;

/**
 * Created by jiangtea on 2019/11/20.
 */
public class TypeBean {

    private String letter;
    private int num;

    public TypeBean(String letter, int num) {
        this.letter = letter;
        this.num = num;
    }

    public String getLetter() {
        return letter;
    }

    public int getNum() {
        return num;
    }
}
