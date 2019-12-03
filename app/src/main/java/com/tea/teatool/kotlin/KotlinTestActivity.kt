package com.tea.teatool.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tea.teatool.R
import kotlinx.android.synthetic.main.activity_kotlin_test.*

/**
 * Created by jiangtea on 2019/12/3.
 */

const val stringH = "MyKotlin"

class KotlinTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_test)
        kt_tv.text = stringH
    }

}


