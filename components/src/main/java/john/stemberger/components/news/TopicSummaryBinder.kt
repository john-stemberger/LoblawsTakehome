package john.stemberger.components.news

import john.stemberger.components.ComponentBinder
import john.stemberger.components.ComponentViewHolder
import john.stemberger.components.ImageLoader

class TopicSummaryBinder(
    val title: String,
    val thumbnail: String? = null,
    val imageLoader: ImageLoader
) : ComponentBinder() {
    override fun bind(viewHolder: ComponentViewHolder) {
        if (viewHolder is TopicSummaryViewHolder) {
            viewHolder.title.text = title
            imageLoader.loadImage(viewHolder.thumbnail, thumbnail)
        }
    }
}