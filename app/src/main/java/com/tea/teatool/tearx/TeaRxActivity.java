package com.tea.teatool.tearx;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.tea.teatool.R;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class TeaRxActivity extends AppCompatActivity {

    private ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_rx);
        imageview = findViewById(R.id.image_view);


        new Thread(new Runnable() {
            @Override
            public void run() {
                Observable.just("http://img.taopic.com/uploads/allimg/130331/240460-13033106243430.jpg")
                        .map(new Function<String, Bitmap>() {
                            @Override
                            public Bitmap apply(String urlString) throws Exception {
                                URL url = new URL(urlString);
                                URLConnection urlConnection = url.openConnection();
                                InputStream inputStream = urlConnection.getInputStream();
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                return bitmap;
                            }
                        })
                        .map(new Function<Bitmap, Bitmap>() {
                            @Override
                            public Bitmap apply(Bitmap bitmap){
                                Bitmap bitmapWM = createWaterMark(bitmap, "WaterMark");
                                return bitmapWM;
                            }
                        })
                        //.subscribeOn(Schedulers.io())
                        //.observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Bitmap>() {
                            @Override
                            public void accept(final Bitmap bitmap){

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        imageview.setImageBitmap(bitmap);
                                    }
                                });
                            }
                        });
            }
        }).start();




        Observable.just("fdsf").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe() {
                Toast.makeText(TeaRxActivity.this,"onSubscribe",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNext(String s) {
                Toast.makeText(TeaRxActivity.this,"onNext：：" + s,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Throwable e) {
                Toast.makeText(TeaRxActivity.this,"onError",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onComplete() {
                Toast.makeText(TeaRxActivity.this,"onComplete",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Bitmap createWaterMark(Bitmap bitmap, String mark) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint p = new Paint();
        // 水印颜色
        p.setColor(Color.parseColor("#C5FF0000"));
        // 水印字体大小
        p.setTextSize(150);
        //抗锯齿
        p.setAntiAlias(true);
        //绘制图像
        canvas.drawBitmap(bitmap, 0, 0, p);
        //绘制文字
        canvas.drawText(mark, 0, h / 2, p);
        canvas.save();
        canvas.restore();
        return bmp;
    }
}
