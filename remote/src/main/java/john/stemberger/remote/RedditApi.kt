package john.stemberger.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {
    @GET("r/{topic}/.json")
    fun getTopics(@Query("topic") filter: String): Single<TopicList>
}