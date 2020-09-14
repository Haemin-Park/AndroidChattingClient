package com.example.testchat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testchat.Constant
import com.example.testchat.R
import com.example.testchat.model.Chat

class ChatAdapter(val context: Context)
    : RecyclerView.Adapter<ChatAdapter.Holder>()
{
    val MY_VIEW = 1;
    val RECEIVE_VIEW = 2;

    val constant = Constant()

    var chatDatas = ArrayList<Chat>()

    fun addItem(item: Chat){
        chatDatas.add(item)
        notifyDataSetChanged()
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){

        val mid = itemView.findViewById<TextView>(R.id.mid)
        val chatItem = itemView.findViewById<TextView>(R.id.messageTextView)

        fun bind(chatData: Chat, context: Context){
            mid.text = chatData.sender
            chatItem.text = chatData.message
        }
    }

    override fun getItemViewType(position: Int) : Int{
        val currentItem: Chat = chatDatas[position]
        val sender = currentItem.sender

        when(sender){
            constant.SENDER -> return MY_VIEW
            else -> return RECEIVE_VIEW
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