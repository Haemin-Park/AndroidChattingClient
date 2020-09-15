package com.example.testchat.model.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitCreator {

    companion object{
        val BASE_URL = "http://ec2-52-79-243-46.ap-northeast-2.compute.amazonaws.com:8080"

        private fun retrofit(BASE_URL:String): Retrofit{
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }

        fun <T> create(service: Class<T>): T{
            return retrofit(BASE_URL).create(service)
        }
    }
}