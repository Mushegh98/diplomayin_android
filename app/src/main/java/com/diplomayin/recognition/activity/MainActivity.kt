package com.diplomayin.recognition.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diplomayin.recognition.R
import com.diplomayin.recognition.base.utils.extension.replaceFragment
import com.diplomayin.recognition.fragment.login.LoginScreenFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel : MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(viewModel.getToken().isNullOrEmpty()){
            supportFragmentManager.replaceFragment(R.id.frame,
                LoginScreenFragment.newInstance())
        }else{
            Intent(this,ContainerActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }

    }
}