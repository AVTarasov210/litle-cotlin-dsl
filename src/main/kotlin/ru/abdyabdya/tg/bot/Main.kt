import ru.abdyabdya.tg.bot.core.StateService
import ru.abdyabdya.tg.bot.dsl.processor

fun main(args: Array<String>) {
    processor(Test()) {
        state(TestState.A) {
            action("print") {
                println("I am printing")
                TestState.B
            }
        }
        state(TestState.A) {
            action("back") {
                println("Back to A")
                TestState.A
            }
        }
    }
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
        TODO("Not yet implemented")
    }
}
