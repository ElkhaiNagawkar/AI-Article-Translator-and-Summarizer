package com.enagawkar.info5126_finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.enagawkar.info5126_finalproject.model.ArticleData

class RecyclerAdapter(private var data: List<ArticleData>, private val listener: RecyclerViewClickEvent) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) , View.OnClickListener {
        var headerText: TextView

        init {
            // Define click listener for the ViewHolder's View.
            headerText = view.findViewById<TextView>(R.id.textView4)
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = bindingAdapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onRowClick(position)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recyclerview_item_layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        position: Int
    ) {
        viewHolder.headerText.setText((data.get(position).title).toString())
    }

    override fun getItemCount(): Int {
        println("data: " + data)
       return data.size
    }



    interface RecyclerViewClickEvent{
        fun onRowClick(position: Int)
    }
}