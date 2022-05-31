package com.example.android.miwok

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.miwok.fragments.ColorsFragment


class ColorsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ColorsFragment())
            .commit()
    }
}