package com.shapegames.animals.views.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shapegames.animals.R
import com.shapegames.animals.data.local.DogDetails
import com.shapegames.animals.data.local.DogsBreedModel
import com.shapegames.animals.databinding.LikedDogsRowItemBinding
import com.shapegames.animals.utils.load

class LikedDogsListAdapter(
    private val list: MutableList<DogDetails>
) :
    RecyclerView.Adapter<DogsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsViewHolder {
        return DogsViewHolder(
            LikedDogsRowItemBinding.inflate(
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
                } else {
                    likeIv.load(R.drawable.heart_empty)
                }
                imageView.load(this.dogUrl)
            }
        }

        /**
         * We can provide like/unlike feature here as well
         * so that user don't have to go back to unlike a dog
         * Keeping it simple for now
         */
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class DogsViewHolder(val viewItem: LikedDogsRowItemBinding) :
    RecyclerView.ViewHolder(viewItem.root) {

}