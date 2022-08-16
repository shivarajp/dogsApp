package com.shapegames.animals.views.dogslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.shapegames.animals.data.local.DogDetails
import com.shapegames.animals.data.local.DogsBreedModel
import com.shapegames.animals.data.models.DogsByBreedResponseModel
import com.shapegames.animals.data.remote.Status
import com.shapegames.animals.databinding.DogDetailsFragmentBinding
import com.shapegames.animals.utils.Util.GRID_COUNT
import com.shapegames.animals.utils.Util.LIKED
import com.shapegames.animals.utils.Util.UNLIKED
import com.shapegames.animals.utils.hide
import com.shapegames.animals.utils.show
import com.shapegames.animals.utils.toast
import com.shapegames.animals.views.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DogsDetailsFragment : Fragment() {

    private var _binding: DogDetailsFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private val args: DogsDetailsFragmentArgs by navArgs()
    private val dogsList = mutableListOf<DogsBreedModel>()
    private lateinit var adapter: DogsListAdapter

    private lateinit var parentBreedName: String
    private lateinit var subBreedName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DogDetailsFragmentBinding.inflate(inflater, container, false)

        parentBreedName = args.breedId
        subBreedName = args.subBreedId

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        fetchDogs()
    }


    private fun handleSuccess(dogsByBreedResponseModel: DogsByBreedResponseModel) {

        adapter = DogsListAdapter(dogsList, parentBreedName, subBreedName)
        binding.dogsRv.adapter = adapter

        viewModel.getAllDogsUrls(parentBreedName, subBreedName)
            .observe(viewLifecycleOwner, Observer { it ->
                if (it.isNotEmpty()) {

                    var liked = dogsByBreedResponseModel.message.intersect(it.toSet()).toList()
                    val notLiked = dogsByBreedResponseModel.message.minus(liked.toSet())

                    liked.forEach { url ->
                        dogsList.add(DogsBreedModel(dogUrl = url, isLiked = true))
                    }

                    notLiked.forEach { url ->
                        dogsList.add(DogsBreedModel(dogUrl = url, isLiked = false))
                    }
                } else {
                    dogsByBreedResponseModel.message.forEach {
                        dogsList.add(DogsBreedModel(dogUrl = it, isLiked = false))
                    }
                }
                adapter.notifyDataSetChanged()

            })

        binding.progressBar.hide()
        //dogsList.addAll(dogsByBreedResponseModel.message)
        //adapter.notifyDataSetChanged()
        adapter.itemClick = { dog: DogsBreedModel, parentBreedName: String,
                              subBreedName: String, position: Int ->
            //User liked/unliked the photo
            if (!dog.isLiked) {
                dog.isLiked = LIKED
                val dogDetail = DogDetails(
                    dogUrl = dog.dogUrl,
                    breedName = parentBreedName,
                    subBreed = subBreedName,
                    isLiked = dog.isLiked
                )

                viewModel.insertLikedDog(dogDetail)
                    .observe(viewLifecycleOwner, Observer {
                        lifecycleScope.launch {
                            dogsList[position] = dog
                            adapter.notifyItemChanged(position)
                        }
                    })
            } else {
                dog.isLiked = UNLIKED
                viewModel.deleteDog(parentBreedName, subBreedName, dog.dogUrl)
                dogsList[position] = dog
                adapter.notifyItemChanged(position)
            }
        }
    }


    private fun fetchDogs() {
        if (subBreedName.isNotEmpty() || parentBreedName.isNotEmpty()) {
            viewModel.currentWeather.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Status.ERROR -> {
                        handleError()
                    }
                    Status.SUCCESS -> {
                        it?.data?.let {
                            handleSuccess(it)
                        }
                    }
                    Status.LOADING -> {
                        binding.progressBar.show()
                    }
                }
            })

            viewModel.fetchDogsBySubBreedFromApi(
                parentBreedId = parentBreedName,
                subBreedId = subBreedName
            )
        }
    }

    fun handleError() {
        binding.progressBar.hide()
        requireActivity().toast("Something went wrong")
    }


    private fun init() {
        binding.backIv.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.likedDogsIv.setOnClickListener {
            findNavController().navigate(
                DogsDetailsFragmentDirections.actionSecondFragmentToLikedDogsFragment()
            )
        }

        binding.dogsRv.layoutManager = GridLayoutManager(requireActivity(), GRID_COUNT)
        adapter = DogsListAdapter(dogsList, parentBreedName, subBreedName)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}