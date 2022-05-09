package machine

private enum class Coffee(
    val id: Int,
    val waterPerCup: Int,
    val milkPerCup: Int,
    val beansPerCup: Int,
    val pricePerCup: Int
) {
    ESPRESSO(1, 250, 0, 16, 4),
    LATTER(2, 350, 75, 20, 7),
    CAPPUCCINO(3, 200, 100, 12, 6)
}

private enum class Action {
    BUY {
        override fun act(machine: Machine) {
            when (machine.state) {
                State.CHOOSING_ACTION -> machine.state = State.CHOOSING_COFFEE
                State.CHOOSING_COFFEE -> {
                    if ("back" == machine.lastInput) {
                        machine.state = State.CHOOSING_ACTION
                        return
                    }
                    val coffee: Coffee = Coffee.values().find { it.id == machine.lastInput.toInt() }!!
                    if (machine.waterAvailable >= coffee.waterPerCup &&
                        machine.milkAvailable >= coffee.milkPerCup &&
                        machine.beansAvailable >= coffee.beansPerCup &&
                        machine.cupsAvailable >= 1
                    ) {
                        println("I have enough resources, making you a coffee!")
                        machine.waterAvailable -= coffee.waterPerCup
                        machine.milkAvailable -= coffee.milkPerCup
                        machine.beansAvailable -= coffee.beansPerCup
                        machine.cupsAvailable -= 1
                        machine.moneyAvailable += coffee.pricePerCup
                    } else {
                        println("Sorry, not enough resources! I need more:")
                        if (machine.waterAvailable < coffee.waterPerCup) println("water")
                        if (machine.milkAvailable < coffee.milkPerCup) println("milk")
                        if (machine.beansAvailable < coffee.beansPerCup) println("coffee beans")
                        if (machine.cupsAvailable < 1) println("disposable cups of coffee")
                    }
                    machine.state = State.CHOOSING_ACTION
                }
                else -> throw IllegalStateException("Something went wrong")
            }
        }
    },
    FILL {
        override fun act(machine: Machine) {
            when (machine.state) {
                State.CHOOSING_ACTION -> machine.state = State.FILL_WATER
                State.FILL_WATER -> {
                    machine.waterAvailable += machine.lastInput.toInt()
                    machine.state = State.FILL_MILK
                }
                State.FILL_MILK -> {
                    machine.milkAvailable += machine.lastInput.toInt()
                    machine.state = State.FILL_BEANS
                }
                State.FILL_BEANS -> {
                    machine.beansAvailable += machine.lastInput.toInt()
                    machine.state = State.FILL_CUPS
                }
                State.FILL_CUPS -> {
                    machine.cupsAvailable += machine.lastInput.toInt()
                    machine.state = State.CHOOSING_ACTION
                }
                else -> throw IllegalStateException("Something went wrong")
            }
        }
    },
    TAKE {
        override fun act(machine: Machine) {
            println("I gave you \$${machine.moneyAvailable}")
            machine.moneyAvailable = 0
        }
    },
    REMAINING {
        override fun act(machine: Machine) {
            println("The coffee machine has:")
            println("${machine.waterAvailable} ml of water")
            println("${machine.milkAvailable} ml of milk")
            println("${machine.beansAvailable} g of coffee beans")
            println("${machine.cupsAvailable} disposable cups")
            println("\$${machine.moneyAvailable} of money")
        }
    },
    EXIT {
        override fun act(machine: Machine) {
            machine.state = State.DONE
        }
    };

    abstract fun act(machine: Machine)
}

fun main() {
    val machine = Machine()
    while (machine.state != State.DONE) {
        machine.promptInput()
        machine.parseInput(readLine()!!)
    }
}

enum class State {
    CHOOSING_ACTION, CHOOSING_COFFEE, FILL_WATER, FILL_MILK, FILL_BEANS, FILL_CUPS, DONE
}

class Machine {
    var waterAvailable = 400
    var milkAvailable = 540
    var beansAvailable = 120
    var cupsAvailable = 9
    var moneyAvailable = 550

    var state: State = State.CHOOSING_ACTION
    var lastInput: String = ""

    fun promptInput() {
        val prompt = when (state) {
            State.CHOOSING_ACTION -> "Write action (buy, fill, take, remaining, exit): > "
            State.CHOOSING_COFFEE -> "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: > "
            State.FILL_WATER -> "Write how many ml of water do you want to add: > "
            State.FILL_MILK -> "Write how many ml of milk do you want to add: > "
            State.FILL_BEANS -> "Write how many grams of coffee beans do you want to add: > "
            State.FILL_CUPS -> "Write how many disposable cups of coffee do you want to add: > "
            State.DONE -> ""
        }
        print(prompt)
    }

    fun parseInput(input: String) {
        lastInput = input
        when (state) {
            State.CHOOSING_ACTION -> {
                val action: Action = Action.valueOf(input.uppercase())
                action.act(this)
            }
            State.CHOOSING_COFFEE -> {
                Action.BUY.act(this)
            }
            State.FILL_WATER,
            State.FILL_MILK,
            State.FILL_CUPS,
            State.FILL_BEANS -> Action.FILL.act(this)
            State.DONE -> throw IllegalStateException("Something went wrong")
        }
        promptInput()
    }
}
