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
import com.shapegames.animals.data.local.DogsBreedModel
import com.shapegames.animals.data.remote.Status
import com.shapegames.animals.databinding.DogDetailsFragmentBinding
import com.shapegames.animals.utils.Util.GRID_COUNT
import com.shapegames.animals.utils.Util.LIKED
import com.shapegames.animals.utils.Util.MINIMUM_ID
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

    private var parentBreedId: Long? = null
    private var subBreedId: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DogDetailsFragmentBinding.inflate(inflater, container, false)

        parentBreedId = args.breedId
        subBreedId = args.subBreedId

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeDogs()
        fetchDogs()
    }

    private fun observeDogs() {
        if (subBreedId!! > MINIMUM_ID) {
            //Its a sub breed
            viewModel.observeLocalDogsAndBreedByBreedId(subBreedId!!)
                .observe(viewLifecycleOwner, Observer { list ->
                    dogsList.addAll(list)
                    adapter.notifyDataSetChanged()
                })
        } else {
            //Its a parent breed
            viewModel.observeLocalDogsAndBreedByBreedId(parentBreedId!!)
                .observe(viewLifecycleOwner, Observer {
                    dogsList.addAll(it)
                    adapter.notifyDataSetChanged()
                })
        }

        adapter = DogsListAdapter(dogsList)
        binding.dogsRv.adapter = adapter

        adapter.itemClick = { dog: DogsBreedModel, position: Int ->
            //User liked/unliked the photo
            if (!dog.isLiked) {
                dog.isLiked = LIKED
            } else {
                dog.isLiked = UNLIKED
            }
            viewModel.updateLikeStatus(dog).observe(viewLifecycleOwner, Observer {
                lifecycleScope.launch {
                    dogsList[position] = dog
                    adapter.notifyItemChanged(position)
                }
            })
        }
    }


    private fun fetchDogs() {
        if (subBreedId!! > MINIMUM_ID || parentBreedId!! > MINIMUM_ID) {
            viewModel.fetchDogsBySubBreedFromApi(
                parentBreedId = parentBreedId!!,
                subBreedId = subBreedId!!
            ).observe(viewLifecycleOwner, Observer {

                when (it.status) {
                    Status.ERROR -> {
                        binding.progressBar.hide()
                        requireActivity().toast("Something went wrong")
                    }
                    Status.SUCCESS -> {
                        binding.progressBar.hide()
                    }
                    Status.LOADING -> {
                        binding.progressBar.show()
                    }
                }
            })
        }
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
        adapter = DogsListAdapter(dogsList)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}