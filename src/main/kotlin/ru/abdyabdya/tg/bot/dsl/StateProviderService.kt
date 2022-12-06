package ru.abdyabdya.tg.bot.dsl

import ru.abdyabdya.tg.bot.core.ActionProviderService
import ru.abdyabdya.tg.bot.core.StateProviderService

class StateProviderBuilder<E : Enum<E>> {
    private val stateProviders: MutableMap<E, ActionProviderBuilder<E>> = mutableMapOf()

    fun toService() = StateProviderService(stateProviders.map { Pair(it.key, it.value.toService()) }.toMap())

    private fun buildState(state: E, builder: ActionProviderBuilder<E>.() -> Unit) {
        if (stateProviders.containsKey(state)) {
            stateProviders[state]!!.apply(builder)
        } else {
            stateProviders[state] = ActionProviderBuilder<E>().apply(builder)
        }
    }
    infix fun E.state(builder: ActionProviderBuilder<E>.() -> Unit) = buildState(this, builder)

    infix fun List<E>.withAction(actionName: String) = ActionContainer(this, listOf(actionName))

    infix fun List<E>.withAction(actionName: List<String>) = ActionContainer(this, actionName)

    infix fun E.withAction(actionName: String) = ActionContainer(listOf(this), listOf(actionName))

    infix fun E.withAction(actionName: List<String>) = ActionContainer(listOf(this), actionName)

    infix fun ActionContainer<E>.doing(action: () -> E) = addActionWithActionContainer(this, action)

    private fun addActionWithActionContainer(actionContainer: ActionContainer<E>, action: () -> E) {
        actionContainer.states.forEach {
            it state { actionContainer.actionNames.doing(action) }
        }
    }
}

class ActionProviderBuilder<E : Enum<E>> {
    private val actionProviders: MutableMap<String, () -> E> = mutableMapOf()
    private var defaultProvider: (() -> E)? = null

    fun actions(actions: List<String>, body: () -> E) = actionProviders.apply { putAll(actions.map { Pair(it, body) }) }

    infix fun String.doing(field: () -> E) {
        actions(listOf(this), field)
    }
    infix fun List<String>.doing(field: () -> E) {
        actions(this, field)
    }

    fun toService() = ActionProviderService(actionProviders, defaultProvider)

    fun default(body: () -> E) {
        defaultProvider = body
    }
}

class ActionContainer<E : Enum<E>>(
    val states: List<E>,
    val actionNames: List<String>
)
