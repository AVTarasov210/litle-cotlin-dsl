package ru.abdyabdya.tg.bot.core

class Processor<E : Enum<E>>(
    private val stateProviderService: StateProviderService<E>,
    private val stateService: StateService<E>
) {
    fun process(action: String, identifier: Any): Set<String> {
        return stateService.getState(identifier).provide(action).nextState(identifier).getActions()
    }

    private fun E.provide(action: String): E {
        return stateProviderService.provide(this, action)
    }

    private fun E.nextState(identifier: Any): E {
        return stateService.nextState(this, identifier)
    }

    private fun E.getActions(): Set<String> {
        return stateProviderService.getActions(this)
    }
}
