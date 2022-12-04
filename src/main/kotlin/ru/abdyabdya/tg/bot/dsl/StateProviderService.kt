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
}

class ActionProviderBuilder<E : Enum<E>> {
    private val actionProviders: MutableMap<String, () -> E> = mutableMapOf()
    private var defaultProvider: (() -> E)? = null

    fun toService() = ActionProviderService(actionProviders, defaultProvider)

    fun action(action: String, body: () -> E) = actionProviders.put(action, body)

    fun default(body: () -> E) {
        defaultProvider = body
    }
}
