package com.example.moviesooq.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datascources.realm_db.MoviesRealm
import com.example.datascources.realm_db.ResultRealm
import com.example.datascources.util.NetworkResult
import com.example.moviesooq.databinding.FragmentHomeBinding
import com.example.moviesooq.ui.viewModels.HomeViewModel
import com.example.moviesooq.ui.viewModels.MovieRealmRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var rlmRsltList: RealmList<ResultRealm>
    private lateinit var mAdapter: MovieRealmRecyclerViewAdapter
    private lateinit var binding:FragmentHomeBinding
   // private val mAdapter by lazy { MovieRealmRecyclerViewAdapter() }
    private lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentHomeBinding.inflate(inflater)

        viewModel.readMoviesList()





                val db = Realm.getDefaultInstance()
  rlmRsltList = db.where(MoviesRealm::class.java).findFirst()?.results!!





        mAdapter = MovieRealmRecyclerViewAdapter(rlmRsltList){
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSecondFragment())
        }
        setUpRecyclerView()
        Log.e("inserts","runs")
        return binding.root
    }
    private fun setUpRecyclerView() {

        binding.rv .adapter = mAdapter
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
     }

}