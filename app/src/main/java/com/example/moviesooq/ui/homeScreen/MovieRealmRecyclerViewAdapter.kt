package com.example.moviesooq.ui.homeScreen


import android.util.Log
import android.view.ViewGroup
 import androidx.recyclerview.widget.RecyclerView
import com.example.datascources.realm_db.ResultRealm
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
/*
 * ExampleRecyclerViewAdapter: extends the Realm-provided
 * RealmRecyclerViewAdapter to provide data
 * for a RecyclerView to display
 * Realm objects on screen to a user.
 */

internal class MovieRealmRecyclerViewAdapter(data: OrderedRealmCollection<ResultRealm?>?,  listener:   ( id: Int) -> Unit ) :
 RealmRecyclerViewAdapter<ResultRealm?,RecyclerView.ViewHolder>(data, true) {
 var TAG = "REALM_RECYCLER_ADAPTER"
 var adapterListener :(id: Int ) -> Unit = listener
 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
  return    ViewHolder.fromList(parent)
 }


 override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
  val obj = getItem(position)
  Log.i(TAG, "Binding view holder: ${obj?.title}")

  (holder as ViewHolder).bind(obj)
  (holder as ViewHolder).itemView.setOnClickListener{

   adapterListener(position)
  }

 }



 override fun getItemId(index: Int): Long {
  return getItem(index)!!.id.toLong()
 }

 init {
  Log.i(TAG, "Created RealmRecyclerViewAdapter for ${getData()!!.size} items.")
 }
}