package john.stemberger.loblawstakehome.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import john.stemberger.components.ComponentBinder
import john.stemberger.components.NewsAdapter
import john.stemberger.components.news.TopicSummaryBinder
import john.stemberger.data.Topic
import john.stemberger.data.TopicRepository
import john.stemberger.loblawstakehome.ui.GlideImageLoader

class TopicListViewModel : ViewModel() {
    private val disposables: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val topicRepository: TopicRepository by lazy {
        TopicRepository.getRepository()
    }

    private val listModels: MutableLiveData<List<Pair<Int, ComponentBinder>>> by lazy {
        var liveData = MutableLiveData<List<Pair<Int, ComponentBinder>>>()
        disposables.clear()
        val disposable = topicRepository.getTopics()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.io())
            .map { topicList ->
                mapDataModelToComponentBinder(topicList)
            }
            .subscribe { list ->
                liveData.postValue(list)
            }
        disposables.add(disposable)
        liveData.postValue(emptyList())
        liveData
    }

    private fun mapDataModelToComponentBinder(topicList: List<Topic>): List<Pair<Int, ComponentBinder>> {
        return topicList.map { topic ->
            val binder = TopicSummaryBinder(
                topic.title,
                topic.thumbnail,
                GlideImageLoader.getImageLoader()
            )
            binder.id = topic.id
            Pair(NewsAdapter.VIEW_TYPE_TOPIC_SUMMARY, binder)
        }
    }

    fun getListModels(): LiveData<List<Pair<Int, ComponentBinder>>> {
        return listModels
    }

    fun navigateToDetails(navController: NavController, binder: TopicSummaryBinder) {
        val id = binder.id
        val title = binder.title
        if (!id.isNullOrEmpty()) {
            val action = TopicListFragmentDirections.actionTopicListFragmentToTopicDetailsFragment(
                topicId = id,
                topicTitle = title
            )
            navController.navigate(action)
        }
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}
