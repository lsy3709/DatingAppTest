package com.bokchi.sogating_final.slider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.date_test.R

class CardStackAdapter(val context : Context, val items : List<String>) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackAdapter.ViewHolder {

        //기본 문법
        val inflater = LayoutInflater.from(parent.context)
        // 아이템 뷰를 넣는 부분 item_card 넣기.
        val view : View = inflater.inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)

    }

    //데이터 넣는 부분
    override fun onBindViewHolder(holder: CardStackAdapter.ViewHolder, position: Int) {
        holder.binding(items[position])
    }

    //데이터의 갯수 만큼 화면을 넘길수 있음.
    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun binding(data : String) {

        }

    }


}