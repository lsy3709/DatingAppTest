package com.example.date_test

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import com.example.date_test.auth.IntroActivity
import com.example.date_test.auth.UserDataModel
import com.example.date_test.setting.SettingActivity
import com.example.date_test.slider.CardStackAdapter
import com.example.date_test.utils.FirebaseAuthUtils
import com.example.date_test.utils.FirebaseRef
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction

class MainActivity : AppCompatActivity() {

    lateinit var cardStackAdapter: CardStackAdapter

    //뷰를 어떻게 보일거냐? 카드 스택뷰에 정의된 부분에서 가져올 예정.
    lateinit var manager: CardStackLayoutManager

    // 콘솔 출력시 식별하기 위한 태그.
    private val TAG = "MainActivity"

    //사용자 정보를 담은 객체
    private val usersDataList = mutableListOf<UserDataModel>()

    // 사용자 조회 상태 숫자.
    private var userCount = 0

    // 현재 유저 성별
    private lateinit var currentUserGender : String

    // 해당 유저 UID 값 조회.
    private val uid = FirebaseAuthUtils.getUid()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //내가 누구를 좋아요 하면, 상대방 좋아요 리스트 목록에 내가 있는지 확인하는 알고리즘.

        val setting = findViewById<ImageView>(R.id.settingIcon)
        setting.setOnClickListener {

            //로그아웃
//            val auth = Firebase.auth
//            auth.signOut()

            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)

        }

        val cardStackView = findViewById<CardStackView>(R.id.cardStackView)

        manager = CardStackLayoutManager(baseContext, object : CardStackListener {

            override fun onCardDragging(direction: Direction?, ratio: Float) {

            }

            // 메인 화면에 카드 아이템을을 와이프 쓸어 넘길때 조정하는 알고리즘.
            override fun onCardSwiped(direction: Direction?) {
                if(direction == Direction.Right) {
//                    Toast.makeText(this@MainActivity, "right", Toast.LENGTH_SHORT).show()
//                    Log.d(TAG, usersDataList[userCount].uid.toString())

                    userLikeOtherUser(uid, usersDataList[userCount].uid.toString())
                }

                if(direction == Direction.Left) {
//                    Toast.makeText(this@MainActivity, "left", Toast.LENGTH_SHORT).show()
                }

                userCount = userCount + 1

                if(userCount == usersDataList.count()) {
                    getUserDataList(currentUserGender)
                    Toast.makeText(this@MainActivity, "유저 새롭게 받아옵니다", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCardRewound() {

            }

            override fun onCardCanceled() {

            }

            override fun onCardAppeared(view: View?, position: Int) {

            }

            override fun onCardDisappeared(view: View?, position: Int) {

            }

        })


        cardStackAdapter = CardStackAdapter(baseContext, usersDataList)
        cardStackView.layoutManager = manager
        cardStackView.adapter = cardStackAdapter

        //getUserDataList()
        getMyUserData()


        // 카드 스택 레이아웃에 매니저 연결
//        cardStackView.layoutManager = manager
//        cardStackView.adapter = cardStackAdapter
    }

    // 나의 정보 가져오기.
    private fun getMyUserData(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                Log.d(TAG, dataSnapshot.toString())
                val data = dataSnapshot.getValue(UserDataModel::class.java)

                Log.d(TAG, data?.gender.toString())

                currentUserGender = data?.gender.toString()

                getUserDataList(currentUserGender)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userInfoRef.child(uid).addValueEventListener(postListener)

    }

    // 모든 유저 가져오기.
    private fun getUserDataList(currentUserGender : String){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children) {

                    val user = dataModel.getValue(UserDataModel::class.java)

                    if(user!!.gender.toString().equals(currentUserGender)) {

                    } else {

                        usersDataList.add(user!!)

                    }


                }

                cardStackAdapter.notifyDataSetChanged()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userInfoRef.addValueEventListener(postListener)

    }
    //유저 종아요 표시 ,
    // 나의 uid, 내가 좋아요한 사람의 uid 비교 하기.

    private fun userLikeOtherUser(myUid : String, otherUid : String){

        FirebaseRef.userLikeRef.child(myUid).child(otherUid).setValue("true")

        getOtherUserLikeList(otherUid)

    }

    // 내가 좋아요한 사람이 누구를 좋아요 했는지 알 수 있음
    private fun getOtherUserLikeList(otherUid : String){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 여기 리스트안에서 나의 UID가 있는지 확인만 해주면 됨.
                // 내가 좋아요한 사람(민지)의 좋아요 리스트를 불러와서
                // 여기서 내 uid가 있는지 체크만 해주면 됨.
                for (dataModel in dataSnapshot.children) {

                    val likeUserKey = dataModel.key.toString()
                    if(likeUserKey.equals(uid)){
                        Toast.makeText(this@MainActivity, "매칭 완료", Toast.LENGTH_SHORT).show()
                        createNotificationChannel()
                        sendNotification()
                    }

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userLikeRef.child(otherUid).addValueEventListener(postListener)


    }

    //Notification

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

    private fun sendNotification(){
        var builder = NotificationCompat.Builder(this, "Test_Channel")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("매칭완료")
            .setContentText("매칭이 완료되었습니다 저사람도 나를 좋아해요")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)) {
            notify(123, builder.build())
        }
    }





}