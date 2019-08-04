package com.tea.teatool.teahttp;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by jiangtea on 2019/8/4.
 */
public interface Bindry {

    String fileName();

    long fileLength();

    String mimType();

    void onWrite(OutputStream outputStream) throws IOException;

}
