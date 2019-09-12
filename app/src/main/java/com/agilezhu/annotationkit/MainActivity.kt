package com.agilezhu.annotationkit

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.agilezhu.annotation.BindView

class MainActivity : AppCompatActivity() {
    @BindView(viewId = R.id.main_title_view)
    lateinit var mTitleView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
