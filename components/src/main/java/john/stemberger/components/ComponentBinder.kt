package john.stemberger.components

abstract class ComponentBinder {
    var id: String? = null
    abstract fun bind(viewHolder: ComponentViewHolder)
}