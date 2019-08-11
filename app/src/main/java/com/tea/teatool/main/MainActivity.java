package com.tea.teatool.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tea.teatool.R;
import com.tea.teatool.arcspeed.ArcSpeedActivity;
import com.tea.teatool.circleload.CircleLoadingActivity;
import com.tea.teatool.cliptext.ClipTextActivity;
import com.tea.teatool.dragmsg.DragMsgActivity;
import com.tea.teatool.flow.FlowActivity;
import com.tea.teatool.keyboard.KeyBoardActivity;
import com.tea.teatool.letterindex.LetterIndexActivity;
import com.tea.teatool.loadingshape.ShapeLoadActivity;
import com.tea.teatool.mazelock.MazeLockActivity;
import com.tea.teatool.slidingmenu.SlidingMenuActivity;
import com.tea.teatool.tabmenu.TabMenuActivity;
import com.tea.teatool.teabutterknife.ButterknifeActivity;
import com.tea.teatool.teaeventbus.TeaEventBusActivity;
import com.tea.teatool.teahttp.TeaHttpActivity;
import com.tea.teatool.teahttp.TeaOkUpFileActivity;
import com.tea.teatool.tearatingbar.TeaRatingBarActivity;
import com.tea.teatool.teatextview.TeaTextViewActivity;
import com.tea.teatool.verticaldrag.VerticalDragActivity;
import com.tea.teatool.webshop.WebShopActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainAdapter.OnItemClickListener {

    private DrawerLayout mainDl;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fabDownload = findViewById(R.id.fab_download);
        NavigationView nvMenu = findViewById(R.id.nv_menu);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        mainDl = findViewById(R.id.main_dl);

        toolbar.setTitle("TeaTool");
        toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mainDl.isDrawerOpen(GravityCompat.START)) {
                    mainDl.openDrawer(GravityCompat.START);
                }
            }
        });

        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.choose1:
                        Toast.makeText(MainActivity.this, "choose1", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        fabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });

        nvMenu.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this, "MenuItemClick", Toast.LENGTH_SHORT).show();
                switch (item.getItemId()) {
                    case R.id.group_item_github:
                        break;
                    case R.id.group_item_more:

                        break;
                    case R.id.group_item_qr_code:

                        break;
                    case R.id.group_item_share_project:

                        break;
                    case R.id.item_model:

                        break;
                    case R.id.item_about:

                        break;
                }

                item.setCheckable(false);
                mainDl.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        ImageView mivMenu = nvMenu.getHeaderView(0).findViewById(R.id.miv_menu);
        mivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainDl.closeDrawer(GravityCompat.START);
                Toast.makeText(MainActivity.this, "head", Toast.LENGTH_SHORT).show();
            }
        });

        generateDatas();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        MainAdapter adapter = new MainAdapter(this, mDatas);
        adapter.setItemClickListener(this);

        recyclerView.setAdapter(adapter);

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshView();
            }
        });
    }

    private ArrayList<MainListBean> mDatas = new ArrayList<>();

    private void generateDatas() {
        mDatas.add(new MainListBean("自定义键盘", KeyBoardActivity.class));
        mDatas.add(new MainListBean("自定义流式布局", FlowActivity.class));
        mDatas.add(new MainListBean("webshop", WebShopActivity.class));
        mDatas.add(new MainListBean("手动实现TextView", TeaTextViewActivity.class));
        mDatas.add(new MainListBean("自定义圆弧加载", ArcSpeedActivity.class));
        mDatas.add(new MainListBean("自定义文字变色", ClipTextActivity.class));
        mDatas.add(new MainListBean("自定义星星评分", TeaRatingBarActivity.class));
        mDatas.add(new MainListBean("自定义字母索引", LetterIndexActivity.class));
        mDatas.add(new MainListBean("自定义九宫格解锁", MazeLockActivity.class));
        mDatas.add(new MainListBean("自定义图形变化加载", ShapeLoadActivity.class));
        mDatas.add(new MainListBean("手写实现Butterknife", ButterknifeActivity.class));
        mDatas.add(new MainListBean("手写实现EventBus", TeaEventBusActivity.class));
        mDatas.add(new MainListBean("自定义列表的下拉抽屉", VerticalDragActivity.class));
        mDatas.add(new MainListBean("自定义侧滑菜单", SlidingMenuActivity.class));
        mDatas.add(new MainListBean("自定义tab菜单筛选", TabMenuActivity.class));
        mDatas.add(new MainListBean("自定义三圆点加载", CircleLoadingActivity.class));
        mDatas.add(new MainListBean("造轮子OkHttp", TeaHttpActivity.class));
        mDatas.add(new MainListBean("OkHttp上传文件", TeaOkUpFileActivity.class));
        mDatas.add(new MainListBean("自定义消息拖拽", DragMsgActivity.class));

    }

    @Override
    public void onItemClick(int position) {
        startActivity(new Intent(this, mDatas.get(position).getActvity()));
    }

    private void refreshView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
