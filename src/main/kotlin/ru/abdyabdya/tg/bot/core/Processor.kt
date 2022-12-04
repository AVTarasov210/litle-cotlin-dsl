package ru.abdyabdya.tg.bot.core

class Processor<E : Enum<E>>(
    private val stateProviderService: StateProviderService<E>,
    private val stateService: StateService<E>
) {
    fun process(action: String, identifier: Any): Set<String> {
        return stateService.getState(identifier).provide(action).getActions()
    }

    private fun E.provide(action: String): E {
        return stateProviderService.provide(this, action)
    }

    private fun E.getActions(): Set<String> {
        return stateProviderService.getActions(this)
    }
}
