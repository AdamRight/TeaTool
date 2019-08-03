package com.tea.teatool.teahttp;

import java.io.IOException;

/**
 * Created by jiangtea on 2019/8/3.
 */
public interface Callback {

    void onFailure(Call call, IOException e);

    void onResponse(Call call ,TeaResponse response) throws IOException;
}
