package john.stemberger.data

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import john.stemberger.remote.RemoteSources
import john.stemberger.remote.TopicList

class TopicRepository {

    private val kotlinTopics: BehaviorSubject<List<Topic>> by lazy {
        val behaviour = BehaviorSubject.create<List<Topic>>()
        loadData(behaviour)
        behaviour
    }

    private fun loadData(behaviour: BehaviorSubject<List<Topic>>) {
        val requestInterface = RemoteSources.getRedditSource()
        requestInterface.getTopics("")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { topicList ->
                mapRemoteToDataModel(topicList)
            }
            .subscribe { list ->
                behaviour.onNext(list)
            }
    }

    private fun mapRemoteToDataModel(topics: TopicList): List<Topic> {
        val newTopics = mutableListOf<Topic>()
        topics.threads.threads.forEach {
            newTopics.add(Topic(it.data.title, it.data.thumbnail))
        }
        return newTopics
    }

    fun getKotlinTopics(): Observable<List<Topic>> {
        return kotlinTopics
    }
}

