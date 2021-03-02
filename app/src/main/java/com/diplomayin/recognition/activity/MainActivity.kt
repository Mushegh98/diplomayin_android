package com.diplomayin.recognition.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diplomayin.recognition.R
import com.diplomayin.recognition.fragment.login.LoginScreenFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.frame,
            LoginScreenFragment.newInstance(),
            LoginScreenFragment::class.java.simpleName).addToBackStack("").commit()
    }
}