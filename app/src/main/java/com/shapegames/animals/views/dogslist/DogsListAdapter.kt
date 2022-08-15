package com.shapegames.animals.views.dogslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shapegames.animals.R
import com.shapegames.animals.data.local.Breeds
import com.shapegames.animals.data.local.DogDetails
import com.shapegames.animals.data.local.DogsBreedModel
import com.shapegames.animals.databinding.DogsRowItemBinding
import com.shapegames.animals.utils.load

class DogsListAdapter(
    private val list: MutableList<DogsBreedModel>
) :
    RecyclerView.Adapter<DogsViewHolder>() {

    var itemClick: ((DogsBreedModel, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsViewHolder {
        return DogsViewHolder(
            DogsRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DogsViewHolder, position: Int) {
        with(holder.viewItem) {
            with(list[position]) {
                title.text = "$breedName"
                if (isLiked) {
                    likeIv.load(R.drawable.heart_filled)
                    /*Glide.with(likeIv.context).load(R.drawable.heart_filled)
                        .apply(RequestOptions().centerCrop()).into(likeIv)*/
                } else {
                    likeIv.load(R.drawable.heart_empty)
                    /*Glide.with(likeIv.context).load(R.drawable.heart_empty)
                        .apply(RequestOptions().centerCrop()).into(likeIv)*/
                }
                imageView.load(this.dogUrl)

                /*Glide.with(imageView.context).load(this.dogUrl).apply(RequestOptions().centerCrop())
                    .into(imageView)*/
            }
        }

        holder.viewItem.imageView.setOnClickListener {
            itemClick?.invoke(list[position], position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class DogsViewHolder(val viewItem: DogsRowItemBinding) : RecyclerView.ViewHolder(viewItem.root) {

    fun bind(position: Int) {

    }
}