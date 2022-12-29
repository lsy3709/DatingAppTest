package com.example.date_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView

import com.example.date_test.auth.IntroActivity
import com.example.date_test.auth.UserDataModel
import com.example.date_test.setting.SettingActivity
import com.example.date_test.slider.CardStackAdapter
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

    //사용자 정보를 담은 객체
    private val usersDataList = mutableListOf<UserDataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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

            override fun onCardSwiped(direction: Direction?) {

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

        getUserDataList()


        // 카드 스택 레이아웃에 매니저 연결
//        cardStackView.layoutManager = manager
//        cardStackView.adapter = cardStackAdapter
    }

    // 유저 가져오기.
    private fun getUserDataList() {

        val postListener = object : ValueEventListener {
            //dataSnapshot 파이어베이스에서 담은 정보.
            override fun onDataChange(dataSnapshot: DataSnapshot) {
            //dataSnapshot.children 해당 컬렉션 하위에 있는 데이터를
                // 반복문으로 dataModel에 담아서 하나씩 꺼내는 작업.
                for (dataModel in dataSnapshot.children) {

                    val user = dataModel.getValue(UserDataModel::class.java)
                    usersDataList.add(user!!)

                }

                cardStackAdapter.notifyDataSetChanged()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("lsy", "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userInfoRef.addValueEventListener(postListener)

    }
}