package com.example.date_test.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.date_test.R

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        //로그인 버튼 클릭시 -> 로그인 화면으로
        // 나중에 뷰 바인딩 기법으로 변경 해도 됨. 일단은 기본으로 함.
        val loginBtn : Button = findViewById(R.id.loginBtn)
        loginBtn.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        //회원가입 버튼 클릭시 -> 회원가입 화면으로
        val joinBtn : Button = findViewById(R.id.joinBtn)
        joinBtn.setOnClickListener {

            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }


    }
}