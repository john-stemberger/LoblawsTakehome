package john.stemberger.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditApi {
    @GET("r/{topic}/.json")
    fun getTopics(@Path("topic") filter: String): Single<TopicList>
}