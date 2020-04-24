package john.stemberger.components

import android.view.LayoutInflater
import android.view.ViewGroup
import john.stemberger.components.news.TopicSummaryViewHolder

open class NewsAdapter : BaseComponentAdapter() {
    companion object {
        val VIEW_TYPE_TOPIC_SUMMARY = getNextViewType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_TOPIC_SUMMARY -> TopicSummaryViewHolder(
                inflateLayout(
                    parent,
                    R.layout.topic_summary
                )
            )
            else -> super.onCreateViewHolder(parent, viewType)

        }

    }
}