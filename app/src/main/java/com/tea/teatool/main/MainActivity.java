package com.tea.teatool.main;

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
        mDatas.add(new MainListBean("自定义View", 1));
        mDatas.add(new MainListBean("自定义控件实战", 2));
        mDatas.add(new MainListBean("系统架构", 3));
        mDatas.add(new MainListBean("web", 4));
        mDatas.add(new MainListBean("其他", 5));
        mDatas.add(new MainListBean("架构篇", 6));
    }

    @Override
    public void onItemClick(int position) {
        FunctionDetailActivity.startDetailActivity(this, mDatas.get(position).getFunctionTypes());
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
