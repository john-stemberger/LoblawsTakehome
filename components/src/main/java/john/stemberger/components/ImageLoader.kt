package john.stemberger.components

import android.widget.ImageView

interface ImageLoader {
    /**
     * load the provided image url into the image view.
     * if no image view is provided then hide the image view completely
     */
    fun loadImage(image: ImageView, url: String?)
}