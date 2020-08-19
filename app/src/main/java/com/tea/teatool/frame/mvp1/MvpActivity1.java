package com.tea.teatool.frame.mvp1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tea.teatool.R;
import com.tea.teatool.frame.mvp1.model.ImageBean;
import com.tea.teatool.frame.mvp1.presenter.DownLoaderPresenter;
import com.tea.teatool.frame.mvp1.utils.Constant;

/**
 *
 * View收到用户的操作 -----> View把用户的操作，交给Presenter
 *
 * Presenter控制Model进行业务逻辑处理,Presenter处理完毕后，数据封装到Model
 *
 * Presenter收到通知后，再更新View
 *
 * View层与Model层完全分离 -----> 所有的逻辑交互都在Presenter
 *
 * MVP中Activity是view层
 *
 * 	M: Model 数据层，访问网络数据都写在这里面
 * 	V: View 界面层，与 View 相关的一些操作都写在这里面，Activity , Fragment , ViewGroup
 * 	P: Presenter 解耦关联层（Model - View），可能还有一些额外的逻辑：数据的一些处理逻辑
 *
 *  P层的作用就是处理M和V之间的交互。
 *
 */
public class MvpActivity1 extends AppCompatActivity implements DownloaderContract.PV {

    private ImageView imageView;
    private DownLoaderPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp1);
        imageView = findViewById(R.id.iv_image);
        presenter = new DownLoaderPresenter(this);
    }

    // 点击事件
    public void down(View view) {
        ImageBean imageBean = new ImageBean();
        imageBean.setRequestPath(Constant.IMAGE_PATH);
        requestDownloader(imageBean);
    }

    @Override
    public void requestDownloader(ImageBean imageBean) {
        if (presenter != null) presenter.requestDownloader(imageBean);
    }

    @Override
    public void responseDownloaderResult(boolean isSuccess, ImageBean imageBean) {
        Toast.makeText(this, isSuccess ? "下载成功" : "下载失败", Toast.LENGTH_SHORT).show();
        if (isSuccess && imageBean.getBitmap() != null) {
            imageView.setImageBitmap(imageBean.getBitmap());
        }
    }


}
