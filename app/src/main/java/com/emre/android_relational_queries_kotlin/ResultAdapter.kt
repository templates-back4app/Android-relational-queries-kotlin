package com.emre.android_relational_queries_kotlin

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.parse.ParseObject
import java.util.ArrayList

class ResultAdapter(context: Context, list: List<ParseObject>) :
    RecyclerView.Adapter<ResultHolder>() {

    var context: Context? = null
    var list: List<ParseObject>? = null

    init {
        this.list = list
        this.context = context
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearList() {
        list = ArrayList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.result_cell, parent, false)
        return ResultHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ResultHolder, position: Int) {
        val `object` = list!![position]
        when {
            `object`.getString("title") != null -> holder.name.text =
                `object`.getString("title")
            `object`.getString("name") != null -> holder.name.text =
                `object`.getString("name")
            else -> holder.name.text = "null"
        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }
}

class ResultHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var name: TextView = itemView.findViewById(R.id.name)

}
