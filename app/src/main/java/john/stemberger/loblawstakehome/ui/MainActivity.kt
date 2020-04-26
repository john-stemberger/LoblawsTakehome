package john.stemberger.loblawstakehome.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import john.stemberger.loblawstakehome.R
import john.stemberger.loblawstakehome.ui.main.TopicListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TopicListFragment.newInstance())
                .commitNow()
        }
    }

    fun show(topicId: Any?) {
//        val intent = Intent(this, ProductActivity::class.java)
//        intent.putExtra(ProductActivity.KEY_PRODUCT_ID, product.id)
//        startActivity(intent)
    }
}
