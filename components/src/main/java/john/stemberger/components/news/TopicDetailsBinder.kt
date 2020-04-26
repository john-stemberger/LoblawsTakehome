package john.stemberger.components.news

import john.stemberger.components.ComponentBinder
import john.stemberger.components.ComponentViewHolder
import john.stemberger.components.ImageLoader

class TopicDetailsBinder(
    val thumbnail: String? = null,
    val body: String,
    val imageLoader: ImageLoader
) : ComponentBinder() {
    override fun bind(viewHolder: ComponentViewHolder) {
        if (viewHolder is TopicDetailsViewHolder) {
            viewHolder.body.text = body
            imageLoader.loadImage(viewHolder.thumbnail, thumbnail)
        }
    }
}