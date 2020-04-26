package john.stemberger.loblawstakehome.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import john.stemberger.components.ComponentAdapter
import john.stemberger.components.ComponentBinder
import john.stemberger.components.news.TopicSummaryBinder
import john.stemberger.loblawstakehome.R

class TopicListFragment : Fragment(),
    TopicSummaryBinder.TopicSummaryListener {

    companion object {
        fun newInstance() = TopicListFragment()
    }

    private lateinit var viewModel: TopicListViewModel
    private lateinit var adapter: ComponentAdapter
    private lateinit var list: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.topic_list_fragment, container, false)

        adapter = ComponentAdapter()
        list = ViewCompat.requireViewById(view, R.id.topic_list)
        list.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        list.adapter = adapter
        list.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        viewModel = ViewModelProvider(this).get(TopicListViewModel::class.java)
        viewModel.getListModels().observe(viewLifecycleOwner,
            Observer {
                onNewList(it)
            }
        )
        return view
    }

    private fun onNewList(components: List<Pair<Int, ComponentBinder>>) {
        components.forEach { pair ->
            if (pair.second is TopicSummaryBinder) {
                (pair.second as TopicSummaryBinder).setListener(this)
            }
        }
        adapter.setComponents(components)
    }

    //region TopicSummaryBinder.TopicSummaryListener
    override fun onClick(binder: TopicSummaryBinder) {
        // TODO navigate to topic based on the id
    }
    // endregion


}
