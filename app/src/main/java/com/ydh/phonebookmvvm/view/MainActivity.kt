package com.ydh.phonebookmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ydh.phonebookmvvm.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp()
    }
}