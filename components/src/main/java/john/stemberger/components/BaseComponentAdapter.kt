package john.stemberger.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * A common  base for all view holders.
 * The standard view holder/binder structure provided by android has been
 * replaced by a component view holder and component view model structure. This
 * decouples the data layer from the presentation layer.
 *
 * Here the purpose of the view holder is two fold:
 * 1) provides the standard view caching for efficient binding of data
 * 2) provides a "layout contract" which enforces an shared definition of what a particular component is capable of displaying
 *    This is used to allow designers to display a ui component in distinct ways depending on the use case. For example
 *    a story may be styled as a book cover (thumbnail image, with a title, author over top of the image in a "Library"
 *    context or as a recommendation card with the image appearing above the title and author in a "search" context.
 *    By defining the view holder independent of a specific layout we are able to reuse the view model for multiple
 *    layouts (Examples will be provided)
 *
 */
open class BaseComponentAdapter : RecyclerView.Adapter<ComponentViewHolder>() {

    companion object {
        private var sLastViewType = RecyclerView.INVALID_TYPE

        /**
         * Get next available code
         * This is used to prevent view type collisions between the different adapters. The standard
         * way a view type would be defined is to use a series of constants but that means there needs
         * to be a single location in which all those constants are defined. That on its own is not
         * a bad thing but as the pattern library grows I want to be able to group common themed patterns
         * together for easier maintenance. I dont want to separate the constants from their primary usage
         * (i.e. the adapter that defines the view types) but if we are not careful by adding a view at a
         * lower level of the component adapter extension chain you could accidentally collide with an
         * already used number. One solution is to use a dynamic indexing system like this in which the
         * specific view type integer is determined at runtime.
         *
         * Other alternatives would be to create some form of structure to the integer values themselves
         * like having all view types for a given grouping start at 100-199, the next grouping could
         * start at 200-299, etc
         */
        @Synchronized
        @JvmStatic
        protected fun getNextViewType(): Int {
            return ++sLastViewType
        }

        @JvmStatic
        protected fun inflateLayout(parent: ViewGroup, @LayoutRes layout: Int): View {
            return LayoutInflater.from(parent.context).inflate(layout, parent, false)
        }
    }

    private val components = mutableListOf<Pair<Int, ComponentBinder>>()

    private class ComponentDiffCallback(
        private val oldList: List<Pair<Int, ComponentBinder>>,
        private val newList: List<Pair<Int, ComponentBinder>>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size


        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val sameType = oldList[oldItemPosition].first == newList[newItemPosition].first
            // because the id is optional it is possible that 2 binders will not have an id set but
            // will be classified as being the same item. to solve this we have to compare the contents
            // of the binders if the ids are null.
            val sameItem = if (oldList[oldItemPosition].second.id == null) {
                // compare the contents of the binders
                oldList[oldItemPosition].second == newList[newItemPosition].second
            } else {
                // at least one of the ids is not null, just compare the ids
                oldList[oldItemPosition].second.id == newList[newItemPosition].second.id
            }
            return sameType && sameItem
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].second == newList[newItemPosition].second
        }
    }

    fun setComponents(newComponents: List<Pair<Int, ComponentBinder>>) {
        val diffCallback = ComponentDiffCallback(components, newComponents)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        components.clear()
        components.addAll(newComponents)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentViewHolder {
        throw IllegalArgumentException("invalid view type $viewType")
    }

    override fun getItemViewType(position: Int): Int {
        return components[position].first
    }

    override fun getItemCount(): Int {
        return components.size
    }

    override fun onBindViewHolder(holder: ComponentViewHolder, position: Int) {
        components[position].second.bind(holder)
    }
}