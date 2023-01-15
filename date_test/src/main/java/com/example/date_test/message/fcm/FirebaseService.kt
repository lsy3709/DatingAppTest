package com.example.date_test.message.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.date_test.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


// 유저 토큰 값 받아오고, -> Firebase 서버로 메시지 보내라 하고
// Firebase 서버에서 앱으로 메시지 보내주고
// 앱에서 메시지 받는 방법 생각해보기.
// 앱에서 알람 띄우기.
// 반응이 많이 느림. 오는 것도 있고, 나중에 한참 있다고 오거나
// 안오는 경우도 있음. 되기는 함.

// 파이어 베이스 알람 푸쉬 시 주의 사항.  안되는 경우 대처 요령.
//계속 파이어베스 푸쉬 알림 기능 동작 안함. 계속 테스트 중.
//1)implementation 'com.google.firebase:firebase-messaging-directboot:20.2.0' 추가.
// //직접 부팅 모드에서 FCM 메시지 수신 -< 공식 홈페이지에 있는 부분
//2)Clean Project 3)Invalidate Cashes
//4)PushNotification.kt의 token을 to로변경,
//5)앱을 깔았다 지우고
//6)유저를 새로 만들어서 회원가입 알림 기능 동작, 하지만, 한명에게만 푸쉬가 뜸.
// 팁) 5) 6) 만 진행 해도 잘 되는 경우도 있음.

// 기존 파이어베이스 계정 및 디비 삭제 안해도 기기만 새로 설치 해도 잘됨.
// 에뮬레이터 기기를 다시 지우고 새로 설치 후 회원 가입 해보고 테스트 먼저 해보고 위에 과정 다시 해보기.
// 잘 되는 경우가 있음.0

// 기존 로그인 한 아이디로 로그인시 (스튜디오 끄고 다시 시작시)
// fcm 푸쉬 잘 안됨.
// 새로 회원 가입한 아이디로 그 기기에서는 잘 작동함.
// 기존 회원 아이디로 로그인시는 또 푸쉬 알람 안감. 대신 메세지는 전송은 됨.

// 문제1
// A 기기에 아이디 1, B 기기에 아이디 2 가입 후 서로 메시지 전송시 알람 잘감(새로 가입시)
// 다시, A 기기에 아이디 3, B 기기에 아이디 4 가입 후 서로 메시지 전송시 알람 잘감 (새로 가입시)
// 이제 A 기기에 아이디 3, B 기기에 아이디 2 에게 메세지 전송시, B기기 아이디 4 로그인이 되어있다면 메시지 전송이 됨.
// 토큰이 해당 에뮬레이터 기기 마다 매정이 되는 것 같음.


class FirebaseService : FirebaseMessagingService() {

    private  val TAG = "FirebaseService"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

//        Log.e(TAG, message.notification?.title.toString())
//        Log.e(TAG, message.notification?.body.toString())
//
        //파이어 베이스 콘솔 상에서 클라우드 메시지 보내면 제목 내용 뜸.
//        val title = message.notification?.title.toString()
//        val body = message.notification?.body.toString()

        val title = message.data["title"].toString()
        val body = message.data["content"].toString()

        Log.e(TAG, title)
        Log.e(TAG, body)

        createNotificationChannel()
        sendNotification(title, body)



    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "name"
            val descriptionText = "description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Test_Channel", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(title : String, body: String){
        var builder = NotificationCompat.Builder(this, "Test_Channel")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)) {
            notify(123, builder.build())
        }
    }

}