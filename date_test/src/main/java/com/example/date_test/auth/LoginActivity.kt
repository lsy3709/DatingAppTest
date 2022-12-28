package com.example.date_test.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.date_test.MainActivity
import com.example.date_test.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    //파이어베이스 인증 객체 선언
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 객체 할당.
        auth = Firebase.auth

        // 로그인 버튼 이벤트 설정.
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        loginBtn.setOnClickListener {

        //입력된 이메일 패스워드 가져오기
            val email = findViewById<TextInputEditText>(R.id.emailArea)
            val pwd = findViewById<TextInputEditText>(R.id.pwdArea)

            //이메일 패스워드 파이어베이스 인증과 비교해 성공 실패 확인.
            //파이어베이스에 제공하는 기존 사용자 로그인 인증 절차 샘플 코드 복붙 후
            // 이메일, 패스워드만 입력함.
            auth.signInWithEmailAndPassword(email.text.toString(), pwd.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    } else {

                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_LONG).show()

                    }
                }


        }

    }
}