package john.stemberger.components.news

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import john.stemberger.components.ComponentViewHolder
import john.stemberger.components.R

class TopicDetailsViewHolder(itemView: View) : ComponentViewHolder(itemView) {
    val thumbnail: ImageView = ViewCompat.requireViewById(itemView, R.id.topic_details_thumbnail)
    val body: TextView = ViewCompat.requireViewById(itemView, R.id.topic_details_body)

    override fun unbind() {
        thumbnail.setImageDrawable(null)
        itemView.setOnClickListener(null)
        super.unbind()
    }
}