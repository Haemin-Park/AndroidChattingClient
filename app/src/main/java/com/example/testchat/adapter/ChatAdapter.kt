package com.example.testchat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.testchat.model.Chat
import com.example.testchat.R

class ChatAdapter(val context: Context, val chatDatas: ArrayList<Chat>)
    : RecyclerView.Adapter<ChatAdapter.Holder>()
{
    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){

        val chat_item = itemView.findViewById<TextView>(R.id.messageTextView)

        fun bind(chatData: Chat, context: Context){
            chat_item.text = chatData.message
        }
    }

    fun getItemType(position: Int) : Int{
        val currentItem: Chat = chatDatas[position]
        val messageType = currentItem.type

        when(messageType){
            "myMsg" -> return 0
            "receiveMsg" -> return 1
        }

        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val view: View

        view = when(viewType){
            0 -> LayoutInflater.from(context).inflate(R.layout.my_msgitem, parent, false)
            else -> LayoutInflater.from(context).inflate(R.layout.receive_msgitem, parent, false)
        }

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return chatDatas.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(chatDatas[position], context)
    }
}