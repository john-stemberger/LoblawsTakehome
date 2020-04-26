package john.stemberger.components

import android.view.ViewGroup
import john.stemberger.components.news.TopicDetailsViewHolder
import john.stemberger.components.news.TopicSummaryViewHolder

open class NewsAdapter : BaseComponentAdapter() {
    companion object {
        val VIEW_TYPE_TOPIC_SUMMARY = getNextViewType()
        val VIEW_TYPE_TOPIC_DETAILS = getNextViewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentViewHolder {
        return when (viewType) {
            VIEW_TYPE_TOPIC_SUMMARY -> TopicSummaryViewHolder(
                inflateLayout(
                    parent,
                    R.layout.topic_summary
                )
            )
            VIEW_TYPE_TOPIC_DETAILS -> TopicDetailsViewHolder(
                inflateLayout(
                    parent,
                    R.layout.topic_details
                )
            )
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }
}