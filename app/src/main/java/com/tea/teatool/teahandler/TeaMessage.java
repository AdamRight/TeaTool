package com.tea.teatool.teahandler;

/**
 * Created by jiangtea on 2019/6/26.
 */

public class TeaMessage {
    public Object obj;
    public TeaHandler target;
    public long when;
    public TeaMessage next;
}
