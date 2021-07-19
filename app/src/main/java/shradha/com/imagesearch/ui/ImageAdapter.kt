package shradha.com.imagesearch.ui

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import shradha.com.imagesearch.R
import shradha.com.imagesearch.data.model.Photo
import shradha.com.imagesearch.ui.ImageListAdapter.ImageViewHolder

object DIFFUTILS : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}

class ImageListAdapter :
    androidx.recyclerview.widget.ListAdapter<Photo, ImageViewHolder>(DIFFUTILS) {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.image)
        private val photographerId = itemView.findViewById<TextView>(R.id.tvPhotoGrapherId)

        fun bind(photo: Photo) {
            photographerId.text = photo.photographer
            Glide
                .with(image.context)
                .load(photo.src.medium)
                .centerCrop()
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.list_items, parent, false
                )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
