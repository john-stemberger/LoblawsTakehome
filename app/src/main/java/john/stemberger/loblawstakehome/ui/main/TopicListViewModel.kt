package john.stemberger.loblawstakehome.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import john.stemberger.components.ComponentBinder
import john.stemberger.components.NewsAdapter
import john.stemberger.components.news.TopicSummaryBinder
import john.stemberger.data.Topic
import john.stemberger.data.TopicRepository
import john.stemberger.loblawstakehome.ui.GlideImageLoader
import java.lang.ref.WeakReference

class TopicListViewModel : ViewModel(),
    TopicSummaryBinder.TopicSummaryListener {
    private val topicRepository: TopicRepository by lazy {
        TopicRepository.getRepository()
    }

    private val listModels: MutableLiveData<List<Pair<Int, ComponentBinder>>> by lazy {
        var liveData = MutableLiveData<List<Pair<Int, ComponentBinder>>>()
        topicRepository.getTopics()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { topicList ->
                mapDataModelToComponentBinder(topicList)
            }
            .subscribe { list ->
                liveData.postValue(list)
            }
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

    // region TopicSummaryBinder.TopicSummaryListener
    override fun onClick(binder: TopicSummaryBinder) {

    }
    // endregion
}
