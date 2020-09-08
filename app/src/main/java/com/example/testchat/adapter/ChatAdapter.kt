package com.example.testchat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testchat.Constant
import com.example.testchat.model.Chat
import com.example.testchat.R

class ChatAdapter(val context: Context)
    : RecyclerView.Adapter<ChatAdapter.Holder>()
{
    val MY_VIEW = 1;
    val RECEIVE_VIEW = 2;

    val constant = com.example.testchat.Constant()

    var chatDatas = ArrayList<Chat>()

    fun addItem(item: Chat){
        chatDatas.add(item)
        notifyDataSetChanged()
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){

        val chat_item = itemView.findViewById<TextView>(R.id.messageTextView)

        fun bind(chatData: Chat, context: Context){
            chat_item.text = chatData.message
        }
    }

    override fun getItemViewType(position: Int) : Int{
        val currentItem: Chat = chatDatas[position]
        val messageType = currentItem.type

        when(messageType){
            constant.MESSAGE_TYPE_MY -> return MY_VIEW
            constant.MESSAGE_TYPE_RECEIVE -> return RECEIVE_VIEW
        }

        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val view = when(viewType){
            MY_VIEW -> LayoutInflater.from(context).inflate(R.layout.my_msgitem, parent, false)
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