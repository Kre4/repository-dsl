package ru.kre4.repository.extensions

import org.springframework.data.repository.CrudRepository
import java.util.*

@Target(AnnotationTarget.CLASS, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION, AnnotationTarget.TYPE)
@DslMarker
annotation class SaveUpdateMarker


/**
 * Extension function that provides DSL language for updating existing entity or providing new one for save
 */
fun <T, ID : Any, R> R.saveUpdate(init: SaveUpdateContext<T, ID, R>.() -> Unit): T where R : CrudRepository<T, ID>, T : Any {
    val context = SaveUpdateContextEntityCalculator(this).apply(init)
    return this.save(context.calculateEntity())
}

@SaveUpdateMarker
open class SaveUpdateContext<T, ID : Any, R>(private val repository: R) where R : CrudRepository<T, ID>, T : Any {
    protected var _entity: Optional<T> = Optional.empty()
    protected lateinit var ifExistsConsumer: ((T).() -> Unit)
    protected lateinit var ifNewSupplier: ((EmptyContext).() -> T)

    protected val entity: T?
        get() = _entity.orElse(null)

    fun byId(id: ID) {
        _entity = repository.findById(id)
    }

    fun bySearch(init: (@SaveUpdateMarker R).() -> T?) {
        _entity = Optional.ofNullable(repository.init())
    }

    fun ifExists(init: (@SaveUpdateMarker T).() -> Unit) {
        ifExistsConsumer = init
    }

    fun ifNew(init: (@SaveUpdateMarker EmptyContext).() -> T) {
        ifNewSupplier = init
    }
}

private class SaveUpdateContextEntityCalculator<T, ID : Any, R>(private val repository: R) :
    SaveUpdateContext<T, ID, R>(repository) where R : CrudRepository<T, ID>, T : Any {

    fun calculateEntity(): T {
        return _entity.map {
            it.ifExistsConsumer()
            it
        }.orElseGet {
            EmptyContext().ifNewSupplier()
        }
    }
}

/**
 * Fake context for correct DSL usage in Supplier-like functions ( () -> T )
 */
class EmptyContext
