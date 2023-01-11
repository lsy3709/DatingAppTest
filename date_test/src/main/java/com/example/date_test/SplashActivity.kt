package com.example.date_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.date_test.auth.IntroActivity
import com.example.date_test.utils.FirebaseAuthUtils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class SplashActivity : AppCompatActivity() {

    private val TAG = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


//        // 토큰 받아오는 샘플.
//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
//                return@OnCompleteListener
//            }
//
//            // Get new FCM registration token
//            val token = task.result
//
//            // Log and toast
//
//            Log.e(TAG, token.toString())
//
//        })
        // 토큰 받아오는 샘플.

        val uid = FirebaseAuthUtils.getUid()

        if (uid == "null") {
            //샘플코드 그대로 복붙.
            Handler().postDelayed({
                val intent = Intent(this, IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }, 3000)

        } else {

            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }, 3000)

        }
    }
}