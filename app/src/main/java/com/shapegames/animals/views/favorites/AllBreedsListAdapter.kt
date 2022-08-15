package com.shapegames.animals.views.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shapegames.animals.R
import com.shapegames.animals.data.local.Breeds
import com.shapegames.animals.data.local.DogsBreedModel
import com.shapegames.animals.databinding.AllBreedsRowItemBinding
import com.shapegames.animals.databinding.LikedDogsRowItemBinding

class AllBreedsListAdapter(
    private val list: MutableList<Breeds>
) :
    RecyclerView.Adapter<AllBreedsViewHolder>() {

    var itemClick: ((Breeds, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllBreedsViewHolder {
        return AllBreedsViewHolder(
            AllBreedsRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AllBreedsViewHolder, position: Int) {
        with(holder.viewItem) {
            with(list[position]) {
                chip.text = "$breedName"
                holder.viewItem.chip.setOnClickListener {
                    itemClick?.invoke(list[position], position)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class AllBreedsViewHolder(val viewItem: AllBreedsRowItemBinding) :
    RecyclerView.ViewHolder(viewItem.root) {

}