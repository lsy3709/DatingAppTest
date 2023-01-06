package com.example.date_test.message.fcm

import com.example.date_test.message.fcm.Repo.Companion.CONTENT_TYPE
import com.example.date_test.message.fcm.Repo.Companion.SERVER_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotiAPI {
    //Repo 에 있는 서버키 및 주소를 이용해서 서버에 전송하기 위한 역할
    // 샘플 코드 그대로 복사 권장. 오타이유로.
    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(@Body notification: PushNotification): Response<ResponseBody>
}