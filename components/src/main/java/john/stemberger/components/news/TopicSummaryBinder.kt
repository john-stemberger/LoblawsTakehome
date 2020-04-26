package john.stemberger.components.news

import android.view.View
import john.stemberger.components.ComponentBinder
import john.stemberger.components.ComponentViewHolder
import john.stemberger.components.ImageLoader
import java.lang.ref.WeakReference

data class TopicSummaryBinder(
    val title: String,
    val thumbnail: String? = null,
    val imageLoader: ImageLoader
) : ComponentBinder(),
    View.OnClickListener {

    interface TopicSummaryListener {
        fun onClick(binder: TopicSummaryBinder)
    }

    var weakListener: WeakReference<TopicSummaryListener>? = null

    fun setListener(ear: TopicSummaryListener) {
        weakListener = WeakReference(ear)
    }

    override fun bind(viewHolder: ComponentViewHolder) {
        viewHolder.itemView.setOnClickListener(null)
        if (viewHolder is TopicSummaryViewHolder) {
            viewHolder.itemView.setOnClickListener(this)
            viewHolder.title.text = title
            imageLoader.loadImage(viewHolder.thumbnail, thumbnail)
        }
    }

    // region View.OnClickListener
    override fun onClick(view: View) {
        weakListener?.get()?.onClick(this)
    }
    // endregion
}