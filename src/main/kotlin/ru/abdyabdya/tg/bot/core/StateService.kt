package ru.abdyabdya.tg.bot.core

interface StateService<E : Enum<E>> {
    fun getState(identifier: Any): E
    fun nextState(state: E, identifier: Any): E
}
