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


        var strArrays = arrayOf("1", "2", "", "", "3", "4")
        strArrays.filter { it.isNotEmpty() }.forEach { println(it) }

        Outter@ for ((index, value) in strArrays.withIndex()) {
            println("$index -> $value")
            Inner@ for (indexedValue in strArrays.withIndex()) {
                if (true) {
                    break@Outter
                }
            }
        }

        val view = View()
        view.onclick = object : OnClickListener {
            override fun onClick() {
            }
        }



    }

}

class Woman {
    var nameA: String = "翠花"
        get() {
            print("get方法")
            return field
        }
        set(value) {
            print("set方法")
            field = value
        }
}

interface Child {
    fun play()
}

interface Parent {
    fun eat()
}

class People : Child, Parent {
    override fun play() {
    }

    override fun eat() {
    }

    var d = FF.ffv
}

class People1(child: Child, parent: Parent) : Child by child, Parent by parent

object FF {
    val ffv: String = "ff"
}

interface OnClickListener {
    fun onClick()
}

class View {
    var onclick: OnClickListener? = null
}
