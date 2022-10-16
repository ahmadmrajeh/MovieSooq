package com.example.moviesooq.ui.homeScreen

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.datascources.realm_db.ResultRealm
import com.example.moviesooq.databinding.ItemBinding

class ViewHolder(private val bindingMainHolder: ItemBinding) :
    RecyclerView.ViewHolder(bindingMainHolder.root) {






    companion object {
        fun fromList(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context )
            val binding = ItemBinding.inflate(layoutInflater, parent, false)


            return ViewHolder(binding)
        }
    }






    fun bind(result: ResultRealm) {
        bindingMainHolder.result = result
        bindingMainHolder.executePendingBindings()
        Glide.with(itemView)
            .load("https://image.tmdb.org/t/p/w400/"+result.poster_path)

            .into(bindingMainHolder.ImageView)

Log.e("path",result.poster_path)
    }


}
