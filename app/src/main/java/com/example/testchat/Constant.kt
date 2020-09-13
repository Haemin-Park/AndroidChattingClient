package com.example.testchat

data class Constant(val SEND_DATA_MESSAGE: String = "message",
                    val MESSAGE_TYPE_MY: String = "my",
                    val MESSAGE_TYPE_RECEIVE: String = "receive",
                    val SENDER: String = "test2",
                    val URL: String = "ws://ec2-52-79-243-46.ap-northeast-2.compute.amazonaws.com:8080/ws/chat/websocket",
                    val chatRoomId: String = "1")
