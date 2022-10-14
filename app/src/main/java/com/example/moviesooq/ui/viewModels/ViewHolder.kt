package com.example.moviesooq.ui.viewModels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datascources.realm_db.ResultRealm
import com.example.moviesooq.databinding.ItemBinding

class ViewHolder(private val bindingMainHolder: ItemBinding) :
    RecyclerView.ViewHolder(bindingMainHolder.root) {






    companion object {
        fun fromList(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }






    fun bind(result: ResultRealm) {
        bindingMainHolder.result = result
        bindingMainHolder.executePendingBindings()
    }


}
