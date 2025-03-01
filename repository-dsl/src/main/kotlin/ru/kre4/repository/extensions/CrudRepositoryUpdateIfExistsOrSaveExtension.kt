package ru.kre4.repository.extensions

import org.springframework.data.repository.CrudRepository

@DslMarker
annotation class SaveUpdate

/**
 * Extension function that provides DSL language for updating existing entity or providing new one for save
 */
fun <T, ID : Any, R> R.saveOrUpdate(init: SaveUpdateContext<T, ID, R>.() -> Unit): T where R : CrudRepository<T, ID> {
    val context = SaveUpdateContext(this).apply(init)
    return this.save(
        context.entity ?: throw KotlinNullPointerException("No function for selecting entity was called. CheckIf")
    )
}

open class SaveUpdateContext<T, ID : Any, R>(private val repository: R) where R : CrudRepository<T, ID> {
    private var _entity: T? = null
    val entity: T?
        get() = _entity

    fun byId(id: ID) {
        _entity = repository.findById(id).get() // todo give more API for better user experience
    }

    fun bySearch(init: R.() -> T?) {
        _entity = repository.init()
    }

    fun ifExists(init: T.() -> Unit) {
        _entity?.apply(init)
    }

    fun ifNew(init: () -> T) {
        _entity = init.invoke()
    }

}
