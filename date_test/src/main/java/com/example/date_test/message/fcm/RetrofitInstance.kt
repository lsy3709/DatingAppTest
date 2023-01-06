package com.example.date_test.message.fcm

import com.example.date_test.message.fcm.Repo.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    //레트로핏 사용 샘플 코드 참고하기.
    companion object {

        private val retrofit by lazy {

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }

        val api = retrofit.create(NotiAPI::class.java)
    }
}