package com.example.date_test.message.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


// 유저 토큰 값 받아오고, -> Firebase 서버로 메시지 보내라 하고
// Firebase 서버에서 앱으로 메시지 보내주고
// 앱에서 메시지 받는 방법 생각해보기.
// 앱에서 알람 띄우기.
class FirebaseService : FirebaseMessagingService() {

    private  val TAG = "FirebaseService"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.e(TAG, message.notification?.title.toString())
        Log.e(TAG, message.notification?.body.toString())
    }

}