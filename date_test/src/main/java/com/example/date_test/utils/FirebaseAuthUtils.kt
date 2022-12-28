package com.example.date_test.utils

import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthUtils {

    //static 처럼 사용
    companion object {

        private lateinit var auth : FirebaseAuth

        fun getUid() : String {

            auth = FirebaseAuth.getInstance()

            return auth.currentUser?.uid.toString()

        }

    }

}