package com.example.testchat

data class Constant(
                    val MESSAGE_TYPE_ENTER: String = "ENTER",
                    val MESSAGE_TYPE_TALK: String = "TALK",
                    val SENDER: String = "test2",
                    val URL: String = "ws://ec2-52-79-243-46.ap-northeast-2.compute.amazonaws.com:8080/ws/chat/websocket",
                    val chatRoomId: String = "1")
