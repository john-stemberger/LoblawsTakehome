package john.stemberger.components

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class ComponentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * When a view holder gets recycled we want to free up any data the view was was holding onto.
     * In most cases the data is small (text) but if there is an image it can be quite large.
     */
    open fun unbind() {
        // NO-OP
    }
}