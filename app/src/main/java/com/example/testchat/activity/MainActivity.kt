package com.example.testchat.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testchat.R
import com.example.testchat.adapter.ChatRoomAdapter
import com.example.testchat.model.response.ChatRoom
import com.example.testchat.model.service.ChatRoomApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val cAdapter: ChatRoomAdapter = ChatRoomAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_chatroom.adapter = cAdapter
        recycler_chatroom.layoutManager = LinearLayoutManager(this)
        recycler_chatroom.setHasFixedSize(true)

        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(ChatRoomApi.getMovie()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({ response: List<ChatRoom> ->
                    for (item in response) {
                        Log.d("ChatRoom", item.name)
                        cAdapter.addItem(item)
                    }

                }, { error: Throwable ->
                    Log.d("ChatRoom: ", error.localizedMessage)
                }))

    }
}