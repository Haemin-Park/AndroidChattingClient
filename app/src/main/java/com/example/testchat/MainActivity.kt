package com.example.testchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testchat.adapter.ChatAdapter
import com.example.testchat.model.Chat
import kotlinx.android.synthetic.main.activity_main.*
import java.net.Socket

class MainActivity : AppCompatActivity() {

    lateinit var mSocket: Socket
    val chatDatas = ArrayList<Chat>()
    val cAdapter: ChatAdapter = ChatAdapter(this, chatDatas)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.adapter = cAdapter
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)

        send.setOnClickListener {
            chatDatas.add(Chat("myMsg",message.text.toString()))
            cAdapter.notifyDataSetChanged()
        }
    }
}