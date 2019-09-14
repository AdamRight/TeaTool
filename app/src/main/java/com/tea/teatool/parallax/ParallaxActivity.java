package com.tea.teatool.parallax;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tea.teatool.R;

public class ParallaxActivity extends AppCompatActivity {

    private ParallaxViewPage parallaxViewPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parallax);
        parallaxViewPage = findViewById(R.id.parallax_page);
        parallaxViewPage.setLayout(getSupportFragmentManager(),new int[]{R.layout.fragment_page_first,R.layout.fragment_page_second,
                R.layout.fragment_page_third,R.layout.fragment_page_first});
    }
}
