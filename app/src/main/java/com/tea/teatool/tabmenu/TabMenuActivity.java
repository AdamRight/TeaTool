package com.tea.teatool.tabmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tea.teatool.R;

public class TabMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_menu);

        TabMenuView tabMenuView = findViewById(R.id.tab_menu_view);
        tabMenuView.setAdapter(new TabMenuAdapter(this));
    }
}
