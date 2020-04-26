package john.stemberger.loblawstakehome.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import john.stemberger.loblawstakehome.R

class TopicDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = TopicDetailsFragment()
    }

    private lateinit var viewModel: TopicDetailsViewModel

    private val args: TopicDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.topic_details_fragment, container, false)
        ViewCompat.requireViewById<TextView>(view, R.id.topic_id).text = args.topicId
        viewModel = ViewModelProvider(this).get(TopicDetailsViewModel::class.java)
        return view
    }
}
