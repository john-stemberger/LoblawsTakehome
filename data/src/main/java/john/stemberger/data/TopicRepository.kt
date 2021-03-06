package john.stemberger.data

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import john.stemberger.remote.RemoteSources
import john.stemberger.remote.ThreadData
import john.stemberger.remote.TopicList

class TopicRepository {

    companion object {
        private const val TOPIC_KEY = "kotlin"

        private val INSTANCE: TopicRepository by lazy {
            TopicRepository()
        }

        fun getRepository(): TopicRepository {
            return INSTANCE
        }
    }

    private val topics: BehaviorSubject<List<Topic>> by lazy {
        val behaviour = BehaviorSubject.create<List<Topic>>()
        loadData(behaviour)
        behaviour
    }

    private fun loadData(behaviour: BehaviorSubject<List<Topic>>) {
        val requestInterface = RemoteSources.getRedditSource()
        requestInterface.getTopics(TOPIC_KEY)
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { list ->
                    behaviour.onNext(mapRemoteToDataModel(list))
                },
                {
                    behaviour.onNext(emptyList())
                }
            )
    }

    private fun mapRemoteToDataModel(topics: TopicList): List<Topic> {
        val newTopics = mutableListOf<Topic>()
        topics.threads.threads.forEach {
            newTopics.add(Topic(it.data.id, it.data.title, getBody(it.data), it.data.thumbnail))
        }
        return newTopics
    }

    private fun getBody(data: ThreadData): String {
        return if (data.body.isEmpty()) {
            data.thumbnail ?: ""
        } else {
            data.body
        }
    }

    fun getTopics(): Observable<List<Topic>> {
        return topics
    }
}

