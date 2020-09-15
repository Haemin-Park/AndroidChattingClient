package com.example.testchat.model.service

import com.example.testchat.model.network.RetrofitCreator
import com.example.testchat.model.response.ChatRoom
import io.reactivex.Single
import retrofit2.http.GET

interface ChatRoomApi {

    interface ChatRoomImpl{
        @GET("/chat")
        fun getChatRoom(): Single<List<ChatRoom>>
    }

    companion object{
        fun getMovie(): Single<List<ChatRoom>>{
            return RetrofitCreator.create(ChatRoomImpl::class.java).getChatRoom()
        }
    }
}