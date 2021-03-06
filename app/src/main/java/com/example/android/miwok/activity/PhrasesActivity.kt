package com.example.android.miwok.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.miwok.R
import com.example.android.miwok.fragments.PhrasesFragment


class PhrasesActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PhrasesFragment())
            .commit()
    }
}