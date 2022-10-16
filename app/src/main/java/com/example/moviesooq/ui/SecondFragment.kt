package com.example.moviesooq.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.datascources.realm_db.MoviesRealm
import com.example.datascources.realm_db.ResultRealm
import com.example.moviesooq.databinding.FragmentSecondBinding
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmList

@AndroidEntryPoint
class SecondFragment : Fragment() {
    private lateinit var rlmRsltList: RealmList<ResultRealm>
private lateinit var binding:FragmentSecondBinding
    val args: SecondFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

            binding=FragmentSecondBinding.inflate(inflater)

        setUpScreen()

        return binding.root
    }


    private fun setUpScreen() {
        val db = Realm.getDefaultInstance()
        rlmRsltList = db.where(MoviesRealm::class.java).findFirst()?.results!!
        if (rlmRsltList.isNotEmpty()) {
            binding.result = rlmRsltList[args.id]
        }
        Glide.with(requireActivity())
            .load("https://image.tmdb.org/t/p/w400/"+rlmRsltList[args.id]?.poster_path)

            .into(binding.imageView)
    }


}