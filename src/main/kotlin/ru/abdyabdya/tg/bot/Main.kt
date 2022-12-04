import ru.abdyabdya.tg.bot.core.StateService
import ru.abdyabdya.tg.bot.dsl.processor

fun main(args: Array<String>) {
    var proc = processor(Test()) {
        TestState.A state {
            "print" doing {
                println("I am printing")
                TestState.B
            }
        }
        TestState.B state {
            "back" doing {
                println("Back to A")
                TestState.A
            }
        }
        TestState.A withAction "banana" doing {
            println("potato")
            TestState.B
        }
    }

    println("You can use: ${proc.process("print", "me")}")
    println("You can use: ${proc.process("back", "me")}")
    println("You can use: ${proc.process("banana", "me")}")
}

enum class TestState {
    A,
    B,
}
class Test : StateService<TestState> {
    private val states: MutableMap<Any, TestState> = mutableMapOf()
    override fun getState(identifier: Any): TestState {
        return if (identifier in states) {
            states[identifier]!!
        } else {
            states[identifier] = TestState.A
            TestState.A
        }
    }

    override fun nextState(state: TestState, identifier: Any): TestState {
        states[identifier] = state
        return state
    }
}
