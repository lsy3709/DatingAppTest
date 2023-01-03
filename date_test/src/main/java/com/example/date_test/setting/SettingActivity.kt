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

        // 1) 앱에서 코드로 notification 띄우기. -> gradle 설치 ->
        //  현재 앱에서 코드로 동작 중
        // 2)파이어베이스 콘솔에서 전체 유저에게 메세지 보내기 참여 -> 파이베이스 알림 메시지 선택. 만들기.
        // -> 테스트 시, 잘 동작 하지 않는 부분이 있음. 다시 확인 해보기.
        //-> 도착은 늦을수 있음, 여러번 테스트 해보면 됨,

        // 주의) 앱을 켜고 , 앱을 백그라운드로 해서 도 확인 해보기. 확인이 잘됨.
        // 만약, 추가로 토큰 및 서비스 설정이 필요하면 참고해서 추가해놓기.

        // 3) 특정 사용자에게 보내기 -> 파이어 베이스 콘솔에서

        // 파이어베이스 콘솔에서 FCM 토큰 등록 부분에 샘플 코드 복사하기.
        // SplashActivity에 복사하기.
        // 실행 후 , 로그캣에 토큰 값 복사하기. 에러 표시로해서 빨간색으로 보이고, 해당 태그로 검색시 빨리 찾음.
        // 그리고 해당 테스트 모드에 해당 토큰 값 복사하기.
        // 각각 의 사용자의 토큰이 다르므로 해당 토큰 값 유저에게 메시지를 보내는 테스트 임.
        // 역시나 백그라운드에서 확인 해보기.

        // 4) 앱에서 직접 다른 사람에게 푸시메세지 보내기.

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