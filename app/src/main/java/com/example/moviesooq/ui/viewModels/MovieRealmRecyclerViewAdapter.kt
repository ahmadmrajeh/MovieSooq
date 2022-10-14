package com.example.moviesooq.ui.viewModels


import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
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
internal class MovieRealmRecyclerViewAdapter(data: OrderedRealmCollection<ResultRealm?>?) :
 RealmRecyclerViewAdapter<ResultRealm?,RecyclerView.ViewHolder>(data, true) {
 var TAG = "REALM_RECYCLER_ADAPTER"

 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
  return    ViewHolder.fromList(parent)
 }

 override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
  val obj = getItem(position)
  Log.i(TAG, "Binding view holder: ${obj!!.title}")

  (holder as ViewHolder).bind(obj)

 }

 override fun getItemId(index: Int): Long {
  return getItem(index)!!.id.toLong()
 }
 internal inner class ExampleViewHolder(var title: TextView)
  : RecyclerView.ViewHolder(title) {
  var data: ResultRealm? = null
 }
 init {
  Log.i(TAG,
   "Created RealmRecyclerViewAdapter for ${getData()!!.size} items.")
 }
}