package com.example.date_test.setting
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Process.myUid
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.date_test.MainActivity

import com.bumptech.glide.Glide
import com.example.date_test.R
import com.example.date_test.auth.UserDataModel
import com.example.date_test.utils.FirebaseAuthUtils
import com.example.date_test.utils.FirebaseRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MyPageActivity : AppCompatActivity() {

    private val TAG = "MyPageActivity"

    private val uid = FirebaseAuthUtils.getUid()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        getMyData()

    }


    private fun getMyData(){

        val myImage = findViewById<ImageView>(R.id.myImage)

        val myUid = findViewById<TextView>(R.id.myUid)
        val myNickname = findViewById<TextView>(R.id.myNickname)
        val myAge = findViewById<TextView>(R.id.myAge)
        val myCity = findViewById<TextView>(R.id.myCity)
        val myGender = findViewById<TextView>(R.id.myGender)


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //dataSnapshot 파이어베이스에서 담은 정보. 로그 출력.
                Log.d(TAG, dataSnapshot.toString())
                // dataSnapshot 에서 가지고 온 정보들 해당 데이터 클래스(DTO)형식으로 해서 data 에 재할당.
                val data = dataSnapshot.getValue(UserDataModel::class.java)

                // data 객체에 담긴 정보를 직접 접근에 다시 재할당.
                myUid.text = data!!.uid
                myNickname.text = data!!.nickname
                myAge.text = data!!.age
                myCity.text = data!!.city
                myGender.text = data!!.gender

                // 파이어베이스 스토리지에 저장된 이미지 파일명: uid.png 해당 이미지를 가지고 옴.
                val storageRef = Firebase.storage.reference.child(data.uid + ".png")
                // storageRef 객체에 담긴 이미지를 다운로드 받아서
                // 람다식으로 해서 글라이드 이미지를 처리하는 곳으로 담음.
                storageRef.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->

                    if(task.isSuccessful) {
                        Glide.with(baseContext)
                            .load(task.result)
                            .into(myImage)

                    }

                })


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        //파이어 베이스 이벤트 리스너에 해당 postListener 등록.
        // 마이 페이지에 불러올 파이어베이스 정보들.
        FirebaseRef.userInfoRef.child(uid).addValueEventListener(postListener)

    }
}