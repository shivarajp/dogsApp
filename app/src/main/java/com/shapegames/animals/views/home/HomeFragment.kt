package com.shapegames.animals.views.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.shapegames.animals.R
import com.shapegames.animals.data.local.Breeds
import com.shapegames.animals.data.remote.Status
import com.shapegames.animals.databinding.HomeFragmentBinding
import com.shapegames.animals.utils.Util.BREED_GRID_COUNT
import com.shapegames.animals.utils.Util.MAX_PERCENTAGE
import com.shapegames.animals.utils.hide
import com.shapegames.animals.utils.show
import com.shapegames.animals.utils.toast
import com.shapegames.animals.views.dogslist.DogsDetailsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var adapter: BreedsListAdapter
    private var _binding: HomeFragmentBinding? = null

    private val binding get() = _binding!!
    private val breedsList = mutableListOf<Breeds>()
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveBreeds()
        setUpViews()

    }

    private fun showBreeds() {
        viewModel.getAllBreedsFromLocal().observe(viewLifecycleOwner, Observer {
            breedsList.addAll(it)
            adapter.notifyDataSetChanged()
        })

        adapter = BreedsListAdapter(breedsList)
        binding.breedsRv.adapter = adapter

        adapter.itemClick = {
            //go to details fragment with breed
            // and sub_breed id of clicked item
            val action = HomeFragmentDirections
                .actionFirstFragmentToSecondFragment(it.parentId, it.breedId)
            findNavController().navigate(action)
        }

        binding.likedDogsIv.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionFirstFragmentToLikedDogsFragment()
            )
        }
    }

    private fun setUpViews() {
        binding.breedsRv.layoutManager = GridLayoutManager(requireActivity(), BREED_GRID_COUNT)
    }

    private fun saveBreeds() {
        viewModel.saveLocalBreedsListInDb().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.ERROR -> {
                    binding.progressBar2.hide()
                    binding.loadingTv.hide()
                    requireActivity().toast("Something went wrong")
                }
                Status.SUCCESS -> {
                    if (!it.data.equals("success")) {
                        binding.progressBar2.max = MAX_PERCENTAGE
                        it.data?.toInt()?.let {
                            binding.progressBar2.progress = it
                        }

                        binding.loadingTv.text = "One time setup ${it.data}"
                    } else {
                        binding.progressBar2.hide()
                        binding.loadingTv.hide()
                        showBreeds()
                    }

                }
                Status.LOADING -> {
                    binding.progressBar2.show()
                    binding.loadingTv.show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}