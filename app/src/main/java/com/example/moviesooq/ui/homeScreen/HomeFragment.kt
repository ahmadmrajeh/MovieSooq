package com.example.moviesooq.ui.homeScreen

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.datascources.realm_db.MoviesRealm
import com.example.datascources.realm_db.ResultRealm
import com.example.moviesooq.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    internal  var Tag= "EMAILUI_AUTH"
    private lateinit var auth: FirebaseAuth
    private var rlmRsltList: RealmList<ResultRealm>?= RealmList()
    private lateinit var mAdapter: MovieRealmRecyclerViewAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

    }


    fun observeData() {
        viewModel.result.observe(viewLifecycleOwner) {

                rlmRsltList = it


            setUpRecyclerView()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)


            viewModel.readMoviesList()
            viewModel.readOfflineCache()
binding.button5.setOnClickListener{

}

        observeData()


        return binding.root
    }


    private fun setUpRecyclerView() {


        if (rlmRsltList?.isNotEmpty() == true) {
            mAdapter = MovieRealmRecyclerViewAdapter(rlmRsltList) { id ->
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSecondFragment(
                        id
                    )
                )
            }

            lifecycleScope.launch(Dispatchers.Main) {
                binding.rv.adapter = mAdapter
                binding.rv.layoutManager = LinearLayoutManager(requireContext())
            }


        }


    }

    override fun onStart() {
        super.onStart()





    }




}
