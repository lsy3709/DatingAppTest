package com.example.date_test.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import com.example.date_test.R
import com.example.date_test.auth.IntroActivity
import com.example.date_test.message.MyLikeListActivity
import com.example.date_test.setting.MyPageActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val mybtn = findViewById<Button>(R.id.myPageBtn)
        mybtn.setOnClickListener {

            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)

        }

        val myLikeBtn = findViewById<Button>(R.id.myLikeList)
        myLikeBtn.setOnClickListener {

            val intent = Intent(this, MyLikeListActivity::class.java)
            startActivity(intent)

        }

        val logoutBtn = findViewById<Button>(R.id.logoutBtn)
        logoutBtn.setOnClickListener {

            //파이어베이스 인증 로그아웃
            val auth = Firebase.auth
            auth.signOut()

            // 인트로 액티비티로 이동하는 부분.
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)

        }


    }
}