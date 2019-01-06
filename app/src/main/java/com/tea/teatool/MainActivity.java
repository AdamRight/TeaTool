package com.tea.teatool;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tea.teatool.flow.FlowActivity;
import com.tea.teatool.keyboard.KeyBoardActivity;
import com.teatool.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainAdapter.OnItemClickListener {

    private DrawerLayout mainDl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        FloatingActionButton fabDownload = findViewById(R.id.fab_download);
        NavigationView nvMenu = findViewById(R.id.nv_menu);
        mainDl = findViewById(R.id.main_dl);

        toolbar.setTitle("首页");
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
                        Toast.makeText(MainActivity.this,"choose1", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        fabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"click", Toast.LENGTH_SHORT).show();
            }
        });

        nvMenu.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this,"MenuItemClick", Toast.LENGTH_SHORT).show();
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

        ImageView mivMenu =  nvMenu.getHeaderView(0).findViewById(R.id.miv_menu);
        mivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainDl.closeDrawer(GravityCompat.START);
                Toast.makeText(MainActivity.this,"head", Toast.LENGTH_SHORT).show();
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
    }

    private ArrayList<String> mDatas =new ArrayList<>();
    private void generateDatas(){
        mDatas.add("自定义键盘");
        mDatas.add("自定义流式布局");
    }

    @Override
    public void onItemClick(int position) {
        switch (position){
            case 0:
                startActivity(new Intent(this, KeyBoardActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, FlowActivity.class));
                break;
        }
    }
}
