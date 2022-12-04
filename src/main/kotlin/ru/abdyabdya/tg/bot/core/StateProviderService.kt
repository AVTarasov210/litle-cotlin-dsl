package ru.abdyabdya.tg.bot.core

import java.lang.IllegalArgumentException

class StateProviderService<E : Enum<E>> (
    private val stateProviders: Map<E, ActionProviderService<E>>
) {
    fun provide(state: E, action: String): E {
        return stateProviders[state] ?.provide(action) ?: throw Exception("StateProvider must provide all states")
    }

    fun getActions(state: E): Set<String> {
        return stateProviders[state]?.actionProviders?.keys ?: throw IllegalArgumentException("State $state has no actions")
    }
}

class ActionProviderService<E : Enum<E>>(
    val actionProviders: Map<String, () -> E>,
    val defaultProvider: (() -> E)?
) {
    fun provide(action: String): E {
        return actionProviders[action] ?.invoke() ?: defaultProvider?.invoke() ?: throw Exception("ActionProvider must provide all actions")
    }
}
