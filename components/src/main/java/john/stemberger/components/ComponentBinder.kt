package john.stemberger.components

abstract class ComponentBinder {
    var id: Any? = null
    abstract fun bind(viewHolder: ComponentViewHolder)
}