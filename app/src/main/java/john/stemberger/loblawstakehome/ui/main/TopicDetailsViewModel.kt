package john.stemberger.loblawstakehome.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import john.stemberger.components.news.TopicDetailsBinder
import john.stemberger.data.Topic
import john.stemberger.data.TopicRepository
import john.stemberger.loblawstakehome.ui.GlideImageLoader

class TopicDetailsViewModel : ViewModel() {
    private val disposables: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val topicLiveData: MutableLiveData<TopicDetailsBinder?> by lazy {
        MutableLiveData<TopicDetailsBinder?>()
    }

    private val topicRepository: TopicRepository by lazy {
        TopicRepository.getRepository()
    }

    private var topicId: String? = null
    fun updateTopicId(id: String) {
        if (topicId != id) {
            // clear the live data object and re-fetch the full topic
            topicLiveData.postValue(null)
            topicId = id
            refreshTopicData()
        }
    }

    fun getTopic(): LiveData<TopicDetailsBinder?> {
        return topicLiveData
    }

    private fun refreshTopicData() {
        disposables.clear()
        val disposable = topicRepository.getTopics()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.io())
            .map { topicList ->
                mapDataModelToComponentBinder(topicList)
            }
            .subscribe { binder ->
                topicLiveData.postValue(binder)
            }
        disposables.add(disposable)
    }

    private fun mapDataModelToComponentBinder(topicList: List<Topic>): TopicDetailsBinder? {
        val myTopic = topicList.firstOrNull { it.id == topicId }
        return if (myTopic != null) {
            TopicDetailsBinder(myTopic.thumbnail, myTopic.body, GlideImageLoader.getImageLoader())
        } else {
            null
        }
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

}
