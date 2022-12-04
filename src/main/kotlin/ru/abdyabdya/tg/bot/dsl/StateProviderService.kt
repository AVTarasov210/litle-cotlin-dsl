package ru.abdyabdya.tg.bot.dsl

import ru.abdyabdya.tg.bot.core.ActionProviderService
import ru.abdyabdya.tg.bot.core.StateProviderService

fun <E : Enum<E>> StateProviderService<E>.stateProviders(
    builder: StateProviderBuilder<E>.() -> Unit
) = StateProviderBuilder<E>().apply(builder).toService()

class StateProviderBuilder<E : Enum<E>> {
    private val stateProviders: MutableMap<E, ActionProviderService<E>> = mutableMapOf()

    fun toService() = StateProviderService(stateProviders)

    fun state(state: E, builder: ActionProviderBuilder<E>.() -> Unit) =
        stateProviders.put(state, ActionProviderBuilder<E>().apply(builder).toService())

    infix fun E.state(builder: ActionProviderBuilder<E>.() -> Unit) {
        state(this, builder)
    }
}

class ActionProviderBuilder<E : Enum<E>> {
    private val actionProviders: MutableMap<String, () -> E> = mutableMapOf()
    private var defaultProvider: (() -> E)? = null

    fun action(action: String, body: () -> E) = actionProviders.put(action, body)
    infix fun String.action(field: () -> E) {
        action(this, field)
    }

    fun toService() = ActionProviderService(actionProviders, defaultProvider)

    fun default(body: () -> E) {
        defaultProvider = body
    }
}
