package john.stemberger.components.news

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import john.stemberger.components.ComponentViewHolder
import john.stemberger.components.R

class TopicSummaryViewHolder(itemView: View) : ComponentViewHolder(itemView) {
    val thumbnail: ImageView = ViewCompat.requireViewById(itemView, R.id.topic_summary_thumbnail)
    val title: TextView = ViewCompat.requireViewById(itemView, R.id.topic_summary_title)

    override fun unbind() {
        thumbnail.setImageDrawable(null)
        super.unbind()
    }

}