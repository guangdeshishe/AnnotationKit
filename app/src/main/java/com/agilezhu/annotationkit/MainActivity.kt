package com.agilezhu.annotationkit

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agilezhu.annotation.BindView
import com.agilezhu.library.AnnotationKit

class MainActivity : AppCompatActivity() {
    @BindView(R.id.main_title_view)
    lateinit var mTitleView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AnnotationKit.bind(this)

        if (mTitleView != null) {
            Toast.makeText(this, "BindView成功", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "BindView失败", Toast.LENGTH_SHORT).show()
        }
    }
}
