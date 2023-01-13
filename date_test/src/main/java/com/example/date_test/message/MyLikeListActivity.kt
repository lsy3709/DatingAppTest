package com.example.date_test.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.date_test.R
import com.example.date_test.auth.UserDataModel
import com.example.date_test.message.fcm.NotiModel
import com.example.date_test.message.fcm.PushNotification
import com.example.date_test.message.fcm.RetrofitInstance
import com.example.date_test.utils.FirebaseAuthUtils
import com.example.date_test.utils.FirebaseRef
import com.example.date_test.utils.MyInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// 내가 종아요한 사람들이 나를 좋아요 한 리스트 목록.
class MyLikeListActivity : AppCompatActivity() {

    private val TAG = "MyLikeListActivity"
    // 내 uid 가져오기
    private val uid = FirebaseAuthUtils.getUid()

    private val likeUserListUid = mutableListOf<String>()
    private val likeUserList = mutableListOf<UserDataModel>()

    lateinit var listviewAdapter : ListViewAdapter
    lateinit var getterUid : String
    lateinit var getterToken : String


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_like_list)

        val userListView = findViewById<ListView>(R.id.userListView)

        listviewAdapter = ListViewAdapter(this,likeUserList)
        userListView.adapter = listviewAdapter

        //전체 유저 데이터 받아오기
       // getUserDataList()
        // 내가 좋아요한 사람들
        getMyLikeList()
        // 나를 좋아요한 사람의 리스트 받기.

        //전체 유저 중에서 내가 좋아요한 사람들 목록 가져와 나와 매칭 여부
//        userListView.setOnItemClickListener{ parent, view, position, id ->
//            //Log.d(TAG,likeUserList[position].uid.toString())
//            checkMatching(likeUserList[position].uid.toString())
//
//            //레트로핏 통신 하기위한 설정
//            // 테스트시 안되는 경우 -> File -> Invalidate cache 누르고
//            // 디바이스 다시 리부트
//            // 앱 지우고 다시 실행 테스트 해보기.
//            val notiModel = NotiModel("a", "b")
//
//            //테스트시 2번째 매개변수에 받는 상대방의 토큰 값 하드코딩으로 넣어 보기.
//            val pushModel = PushNotification(notiModel, likeUserList[position].token.toString())
//
//            testPush(pushModel)
//        }

        userListView.setOnItemLongClickListener { parent, view, position, id ->

            checkMatching(likeUserList[position].uid.toString())
            getterUid = likeUserList[position].uid.toString()
            getterToken = likeUserList[position].token.toString()

            return@setOnItemLongClickListener(true)
        }
        // 좋아요 한 유저를 클릭시 메시지 보내기 창이 뜨고 , 받는 사람도 창이 필요.
        // 메시지 전송 후 상대방 푸쉬 알람 띄우기
        // 서로 좋아요 한 사람만 메세지 보내기 가능,


    }

    private fun checkMatching(otherUid : String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 매칭여부 디버깅 로그찍기. 에러표시로.
                Log.e(TAG,dataSnapshot.toString())

                // 디버깅 로그찍기.
                Log.d(TAG, otherUid)

                if(dataSnapshot.children.count() == 0){
                    Toast.makeText(this@MyLikeListActivity,"상대방이 좋아요한 사람이 없어요",Toast.LENGTH_SHORT).show()
                } else {
                    // 여기 리스트안에서 나의 UID가 있는지 확인만 해주면 됨.
                    // 내가 좋아요한 사람(민지)의 좋아요 리스트를 불러와서
                    // 여기서 내 uid가 있는지 체크만 해주면 됨.
                    for (dataModel in dataSnapshot.children) {

                        // 내가 좋아요 한 사람의 좋아요한 목록의 키(uid) 값
                        val likeUserKey = dataModel.key.toString()
                        if(likeUserKey.equals(uid)){
                            Toast.makeText(this@MyLikeListActivity,"매칭이 됨",Toast.LENGTH_SHORT).show()
                            showDialog()
                        } else {
                            Toast.makeText(this@MyLikeListActivity,"매칭이 안됨",Toast.LENGTH_SHORT).show()
                        }
                    }
                }



            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        // 내가 좋아요 한 사람의 좋아요 리스트를 가져오기.
        FirebaseRef.userLikeRef.child(otherUid).addValueEventListener(postListener)
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

                        // 전체 유저중에 내가 좋아한 사람들의 정보만 추가함.
                    if(likeUserListUid.contains(user?.uid)){
                        // 내가 좋아요한 사람들이 담긴 정보만 확인 가능.
                        likeUserList.add(user!!)
                    }
                    listviewAdapter.notifyDataSetChanged()
                    Log.d(TAG, likeUserList.toString())
                }

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                }
            }

            FirebaseRef.userInfoRef.addValueEventListener(postListener)

        }

    //PUSH 보내기
    //message -> fcm -> PushNotification 에 있음
    private fun testPush(notification : PushNotification) = CoroutineScope(Dispatchers.IO).launch {
//        Log.d(TAG, "테스트 푸쉬 호출후")
        //NotiAPI postNotification 있음.
        RetrofitInstance.api.postNotification(notification)
//        Log.d(TAG, "레트로피 호출 후")

    }

    // Dialog
    private fun showDialog(){

        //다이얼로그 뷰 부분 가져오기
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        //해당 뷰 연결 하기.
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("메세지 보내기")

//        이렇게 하면 다이얼로그 잘 뜸
//        mBuilder.show()

        // 뷰 보여주는 함수 재할당.
        val mAlertDialog = mBuilder.show()

        // 추가 작업

        // 버튼 부분 아이디로 선택해서 가져오기
        val btn = mAlertDialog.findViewById<Button>(R.id.sendBtnArea)
        // 텍스트 영역 마찬가지
        val textArea = mAlertDialog.findViewById<EditText>(R.id.sendTextArea)
        // 버튼 클릭시 이벤트 핸들러 부분
        btn?.setOnClickListener {

            //전달 할 메세지 내용
            val msgText = textArea!!.text.toString()

            // 메시지 모델을 만들어서, 닉네임과, 전달한 메세지 설정.
            val mgsModel = MsgModel(MyInfo.myNickname, msgText)

            // 상대방의 uid 필요하고, 전달 할 메세지를 설정.
            // 해당 메시지를 중복 되지 않고 보내기 위해서 : push() 설정.
            FirebaseRef.userMsgRef.child(getterUid).push().setValue(mgsModel)

            // 제목과 내용 모델
            val notiModel = NotiModel(MyInfo.myNickname, msgText)

            //제목과 내용 모델 , 토큰 (상대방)
            val pushModel = PushNotification(notiModel, getterToken)

            //레트로핏으로 전달하기.
            testPush(pushModel)

            //다이얼로그 창 숨기기
            mAlertDialog.dismiss()
        }

        // message
        // 받는 사람 uid
        // Message
        // 누가 보냈는지

    }

}