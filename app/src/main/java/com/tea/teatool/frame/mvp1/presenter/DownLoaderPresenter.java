package com.tea.teatool.frame.mvp1.presenter;


import com.tea.teatool.frame.mvp1.DownloaderContract;
import com.tea.teatool.frame.mvp1.MvpActivity1;
import com.tea.teatool.frame.mvp1.engine.DownLoaderEngine;
import com.tea.teatool.frame.mvp1.model.ImageBean;

// P层几乎不做事情？谷歌的sample中，P层是包揽了所有的活
public class DownLoaderPresenter implements DownloaderContract.PV {

    private MvpActivity1 view;
    private DownLoaderEngine model; // 下载的模型

    public DownLoaderPresenter(MvpActivity1 view) {
        this.view = view;
        model = new DownLoaderEngine(this);
    }

    @Override
    public void requestDownloader(ImageBean imageBean) {
        // 接收到View层的指令，去完成某个需求（可以自己完成，也可以让别人去完成）
        try {
            model.requestDownloader(imageBean);
        } catch (Exception e) {
            e.printStackTrace();
            // 省略了异常的处理
        }
    }

    @Override
    public void responseDownloaderResult(final boolean isSuccess, final ImageBean imageBean) {
        // 将完成的结果告知View层（刷新UI）
        view.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.responseDownloaderResult(isSuccess, imageBean);
            }
        });
    }
}
