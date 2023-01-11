package com.example.date_test.utils

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseRef {

    companion object {

        // 파이어베이스 데이터베이스 연결.
        // 리얼타임 데이터베이스 사용하기 위한 객체.
        val database = Firebase.database

        // userInfo 경로에서 해당 값을 받아오기. 마치 컬렉션에 하위 다큐먼트 문서를 가지고옴.
        // 각각의 사용자를 다큐먼트 처럼 가지고 옴.
        val userInfoRef = database.getReference("userInfo")

        // 사용자가 좋아요한 목록
        val userLikeRef = database.getReference("userLike")

        //사용자 메세지
        val userMsgRef = database.getReference("userMsg")

    }
}