package ru.abdyabdya.tg.bot.dsl

import ru.abdyabdya.tg.bot.core.Processor
import ru.abdyabdya.tg.bot.core.StateService

fun <E : Enum<E>> processor(stateService: StateService<E>, builder: StateProviderBuilder<E>.() -> Unit): Processor<E> =
    Processor(StateProviderBuilder<E>().apply(builder).toService(), stateService)
