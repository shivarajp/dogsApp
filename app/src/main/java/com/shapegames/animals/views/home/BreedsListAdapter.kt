package com.shapegames.animals.views.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shapegames.animals.data.local.Breeds
import com.shapegames.animals.databinding.BreedRowItemBinding
import com.shapegames.animals.utils.load

class BreedsListAdapter(private val list: MutableList<Breeds>) :
    RecyclerView.Adapter<BreedsViewHolder>() {

    var itemClick: ((Breeds) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedsViewHolder {
        return BreedsViewHolder(
            BreedRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BreedsViewHolder, position: Int) {
        with(holder.viewItem) {
            with(list[position]) {
                breedDogTitle.text = this.breedName
                breedDogImg.load(this.imgUrl)
            }
        }

        /*holder.itemView.setOnClickListener {
            itemClick?.invoke(list[position])
        }

        holder.viewItem.breedDogContainer.setOnClickListener {
            itemClick?.invoke(list[position])
        }*/

        holder.viewItem.view1.setOnClickListener {
            itemClick?.invoke(list[position])
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class BreedsViewHolder(val viewItem: BreedRowItemBinding) : RecyclerView.ViewHolder(viewItem.root) {

}