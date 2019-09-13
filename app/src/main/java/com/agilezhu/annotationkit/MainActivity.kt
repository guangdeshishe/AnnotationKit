package com.agilezhu.annotationkit

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agilezhu.annotation.BindView

class MainActivity : AppCompatActivity() {
    @BindView(R.id.main_title_view)
    var mTitleView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (mTitleView != null) {
            Toast.makeText(this, "BindView成功", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "BindView失败", Toast.LENGTH_SHORT).show()
        }
    }
}
