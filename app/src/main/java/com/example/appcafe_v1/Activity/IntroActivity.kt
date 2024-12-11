package com.example.appcafe_v1.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Window
import com.example.appcafe_v1.LoginActivity
import com.example.appcafe_v1.RegisterActivity
import com.example.appcafe_v1.databinding.ActivityIntroBinding

class IntroActivity : BaseActivity() {
    lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener{
            startActivity(Intent(this@IntroActivity, LoginActivity::class.java))
        }
        binding.signup.setOnClickListener{
            startActivity(Intent(this@IntroActivity, RegisterActivity::class.java))
        }

    }
}