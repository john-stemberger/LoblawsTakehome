package john.stemberger.remote

import com.google.gson.annotations.SerializedName;

data class TopicList(
    @SerializedName("data")
    val threads: TopicThreads)

data class TopicThreads(
    @SerializedName("children") val threads: List<RedditThread>
)

data class RedditThread(val kind: String,
                        @SerializedName("data") val data: ThreadData)

data class ThreadData(val thumbnail: String?,
                      val title: String)
