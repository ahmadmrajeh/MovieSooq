package com.example.moviesooq.ui.homeScreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datascources.realm_db.MoviesRealm
import com.example.datascources.realm_db.ResultRealm
import com.example.moviesooq.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmList

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var rlmRsltList: RealmList<ResultRealm>
    private lateinit var mAdapter: MovieRealmRecyclerViewAdapter
    private lateinit var binding:FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(requireActivity())[HomeViewModel::class.java]

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentHomeBinding.inflate(inflater)
        viewModel.readMoviesList()
        setUpRecyclerView()






         return binding.root
    }
    private fun setUpRecyclerView() {
        val db = Realm.getDefaultInstance()
        rlmRsltList = db.where(MoviesRealm::class.java).findFirst()?.results!!
        if ( rlmRsltList.isNotEmpty())
        {
            mAdapter = MovieRealmRecyclerViewAdapter(rlmRsltList){id ->
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSecondFragment(id))
            }

            binding.rv .adapter = mAdapter
            binding.rv.layoutManager = LinearLayoutManager(requireContext())

        }
    }

}
