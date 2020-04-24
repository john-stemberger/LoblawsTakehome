package john.stemberger.loblawstakehome.ui

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import john.stemberger.components.ImageLoader

class GlideImageLoader : ImageLoader {
    companion object {
        private val INSTANCE: GlideImageLoader by lazy {
            GlideImageLoader()
        }

        @JvmStatic
        fun getImageLoader(): ImageLoader {
            return INSTANCE
        }
    }

    override fun loadImage(image: ImageView, url: String?) {
        image.setImageDrawable(null)
        if(url.isNullOrEmpty()) {
            image.visibility = View.GONE
        } else {
            image.visibility = View.VISIBLE
            Glide.with(image)
                .load(url)
                .into(image)
        }

    }
}