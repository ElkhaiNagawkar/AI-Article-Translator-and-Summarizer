package com.enagawkar.info5126_finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.enagawkar.info5126_finalproject.model.ArticleData

class RecyclerAdapter(private val data: List<ArticleData>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var headerText: TextView
        var bodyText: TextView

        init {
            // Define click listener for the ViewHolder's View.
            headerText = view.findViewById<TextView>(R.id.textView4)
            bodyText = view.findViewById<TextView>(R.id.textView5)

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
        viewHolder.bodyText.setText((data.get(position).summary).toString())
    }

    override fun getItemCount(): Int {
       return data.size
    }
}