package john.stemberger.loblawstakehome.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import john.stemberger.components.news.TopicDetailsBinder
import john.stemberger.components.news.TopicDetailsViewHolder
import john.stemberger.loblawstakehome.R

class TopicDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = TopicDetailsFragment()
    }

    private lateinit var viewModel: TopicDetailsViewModel
    private lateinit var topicViewHolder: TopicDetailsViewHolder

    private val args: TopicDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.topic_details_fragment, container, false)
        topicViewHolder = TopicDetailsViewHolder(ViewCompat.requireViewById(view, R.id.topic))

        viewModel = ViewModelProvider(this).get(TopicDetailsViewModel::class.java)
        viewModel.updateTopicId(args.topicId)
        viewModel.getTopic()
            .observe(viewLifecycleOwner,
                Observer {
                    onNewTopic(it)
                }
            )
        return view
    }

    private fun onNewTopic(topic: TopicDetailsBinder?) {
        if (topic == null) {
            topicViewHolder.itemView.visibility = View.GONE
            topicViewHolder.unbind()
        } else {
            topicViewHolder.itemView.visibility = View.VISIBLE
            topic.bind(topicViewHolder)
        }
    }


}
