package com.example.testchat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
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
import java.util.logging.Logger


class MainActivity : AppCompatActivity() {

    val cAdapter: ChatAdapter = ChatAdapter(this)
    val logger = Logger.getLogger("Main")

    var jsonObject = JSONObject()

    lateinit var stompConnection: Disposable
    lateinit var topic: Disposable

    val parser: Parser = Parser()
    lateinit var json: JsonObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.adapter = cAdapter
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)

        val constant = Constant()

        //1. init
        // url: ws://[도메인]/[엔드포인트]/websocket
        val url = constant.URL
        val intervalMillis = 5000L
        val client = OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()

        val stomp = StompClient(client, intervalMillis).apply { this@apply.url = url }

        // 2. connect
        stompConnection = stomp.connect().subscribe {
            when (it.type) {
                Event.Type.OPENED -> {
                    // subscribe 채널구독
                    // 메세지 받아오기

                    topic = stomp.join("/sub/chat/room/" + constant.chatRoomId).subscribe{
                        stompMessage ->
                        val result = Klaxon()
                                .parse<Chat>(stompMessage)
                        Log.d("JOIN", stompMessage.toString())
                        runOnUiThread {
                            if (result != null) {
                                when (result.sender) {
                                    constant.SENDER -> cAdapter.addItem(result)
                                    else -> cAdapter.addItem(result)
                                }
                            }
                        }
                    }

                    send.setOnClickListener {

                        try {
                            jsonObject.put("messageType", "TALK")
                            jsonObject.put("chatRoomId", constant.chatRoomId)
                            jsonObject.put("sender", constant.SENDER)
                            jsonObject.put("message", message.text.toString())
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        // send
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