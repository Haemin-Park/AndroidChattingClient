package com.example.testchat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testchat.adapter.ChatAdapter
import com.example.testchat.model.Chat
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger


class MainActivity : AppCompatActivity() {

    val cAdapter: ChatAdapter = ChatAdapter(this)

    val logger = Logger.getLogger("Main")

    lateinit var stompConnection: Disposable
    lateinit var topic: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.adapter = cAdapter
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)

        //1. init4

        // url: ws://[도메인]/엔드포인트
        val url = "ws://ec2-52-78-122-121.ap-northeast-2.compute.amazonaws.com:8080/ws/chat/websocket"
        val intervalMillis = 5000L
        val client = OkHttpClient.Builder()
//              .addInterceptor { it.proceed(it.request().newBuilder().header("Authorization", "bearer 68d20faa-54c4-11e8-8195-98ded0151692").build()) }
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()

        val stomp = StompClient(client, intervalMillis).apply { this@apply.url = url }

        val constant = Constant()
        val jsonObject = JSONObject()

        // 2. connect
        stompConnection = stomp.connect().subscribe {
            when (it.type) {
                Event.Type.OPENED -> {

                    // subscribe 채널구독
                    topic = stomp.join("/sub/chat/room/e625927d-59cc-432a-8c83-76052bcb1682")
                            .subscribe { logger.log(Level.INFO, it) }

                    send.setOnClickListener {
                        cAdapter.addItem(Chat(constant.MESSAGE_TYPE_MY, message.text.toString()))
                        try {
                            jsonObject.put("messageType", "TALK")
                            jsonObject.put("chatRoomId", "e625927d-59cc-432a-8c83-76052bcb1682")
                            jsonObject.put("sender", "test1")
                            jsonObject.put("message", message.text.toString())
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        // send
                        Log.d("error", jsonObject.toString())
                        stomp.send("/pub/chat/message", jsonObject.toString()).subscribe()
                        message.text = null
                    }

                    // unsubscribe
                    //topic.dispose()

                }
                Event.Type.CLOSED -> {

                }
                Event.Type.ERROR -> {

                }
            }
        }


    }
}