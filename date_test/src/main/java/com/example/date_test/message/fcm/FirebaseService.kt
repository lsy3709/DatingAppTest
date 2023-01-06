package com.example.date_test.message.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


// 유저 토큰 값 받아오고, -> Firebase 서버로 메시지 보내라 하고
// Firebase 서버에서 앱으로 메시지 보내주고
// 앱에서 메시지 받는 방법 생각해보기.
// 앱에서 알람 띄우기.
// 반응이 많이 느림. 오는 것도 있고, 나중에 한참 있다고 오거나
// 안오는 경우도 있음. 되기는 함.
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