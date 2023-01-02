package com.example.date_test.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.example.date_test.R
import com.example.date_test.auth.UserDataModel
import com.example.date_test.utils.FirebaseAuthUtils
import com.example.date_test.utils.FirebaseRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

// 내가 종아요한 사람들이 나를 좋아요 한 리스트 목록.
class MyLikeListActivity : AppCompatActivity() {

    private val TAG = "MyLikeListActivity"

    private val likeUserListUid = mutableListOf<String>()
    private val likeUserList = mutableListOf<UserDataModel>()

    lateinit var listviewAdapter : ListViewAdapter

    // 내 uid 가져오기
    private val uid = FirebaseAuthUtils.getUid()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_like_list)

        val userListView = findViewById<ListView>(R.id.userListView)

        listviewAdapter = ListViewAdapter(this,likeUserList)
        userListView.adapter = listviewAdapter

        //전체 유저 데이터 받아오기
        getUserDataList()
        // 내가 좋아요한 사람들
        getMyLikeList()
        // 나를 좋아요한 사람의 리스트 받기.


    }


    // 내가 좋아요한 사람들 리스트
    private fun getMyLikeList() {

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 여기 리스트안에서 나의 UID가 있는지 확인만 해주면 됨.
                // 내가 좋아요한 사람(민지)의 좋아요 리스트를 불러와서
                // 여기서 내 uid가 있는지 체크만 해주면 됨.
                for (dataModel in dataSnapshot.children) {
                    Log.d(TAG, dataModel.key.toString())
                    //내가 좋아요한 사람들의 리스트가 들어 있음.
                    likeUserListUid.add(dataModel.key.toString())


                }
                getUserDataList()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        // 내가 좋아요 한 사람의 좋아요 리스트를 가져오기.
        FirebaseRef.userLikeRef.child(uid).addValueEventListener(postListener)


    }

    private fun getUserDataList() {

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (dataModel in dataSnapshot.children) {

                    val user = dataModel.getValue(UserDataModel::class.java)

                    if(likeUserListUid.contains(user?.uid)){
                        // 내가 좋아요한 사람들이 담긴 정보만 확인 가능.
                        likeUserList.add(user!!)
                    }
                    listviewAdapter.notifyDataSetChanged()
                    Log.d(TAG, "point : "+ user.toString())
                }

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                }
            }

            FirebaseRef.userInfoRef.addValueEventListener(postListener)

        }

}