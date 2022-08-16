package com.shapegames.animals.views.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.shapegames.animals.data.local.Breeds
import com.shapegames.animals.data.local.DogDetails
import com.shapegames.animals.databinding.FragmentLikedDogsBinding
import com.shapegames.animals.utils.hide
import com.shapegames.animals.utils.show
import com.shapegames.animals.views.home.HomeViewModel

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LikedDogsFragment : Fragment() {

    private lateinit var adapter: LikedDogsListAdapter
    private lateinit var breedAdapter: AllBreedsListAdapter
    private var _binding: FragmentLikedDogsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private val dogsList = mutableListOf<DogDetails>()
    private val breedList = mutableListOf<Breeds>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLikedDogsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        showLikedDogs()
    }

    private fun init() {
        binding.backIv.setOnClickListener {
            requireActivity().onBackPressed()
        }
        adapter = LikedDogsListAdapter(dogsList)
        breedAdapter = AllBreedsListAdapter(breedList)
    }

    private fun showLikedDogs() {
        binding.likedDogsRv.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.likedDogsRv.adapter = adapter

        binding.breedsHorizontalRv.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.breedsHorizontalRv.adapter = breedAdapter

        viewModel.getAllLikedDogs().observe(viewLifecycleOwner, Observer {
            dogsList.addAll(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.getAllBreedsFromLocal().observe(viewLifecycleOwner, Observer {
            breedList.clear()
            breedList.addAll(it)
            breedAdapter.notifyDataSetChanged()
        })

        breedAdapter.itemClick = { breed, poition ->
            binding.breedTitle.text = breed.breedName

            viewModel.getLikedDogsByBreedNameAndSubBreed(breed.breedName, breed.subBreedName)
                .observe(viewLifecycleOwner, Observer {
                    dogsList.clear()
                    dogsList.addAll(it)
                    adapter.notifyDataSetChanged()

                    if (it.isEmpty()) {
                        binding.noDogsTv.show()
                    } else {
                        binding.noDogsTv.hide()
                    }
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}