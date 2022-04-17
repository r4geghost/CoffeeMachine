package machine

// const vals for one cup of espresso
const val ESP_WATER = 250
const val ESP_COFFEE_BEANS = 16
const val ESP_COST = 4
// const vals for one cup of latte
const val LAT_WATER = 350
const val LAT_MILK = 75
const val LAT_COFFEE_BEANS = 20
const val LAT_COST = 7
// const vals for one cup of cappuccino
const val CAP_WATER = 200
const val CAP_MILK = 100
const val CAP_COFFEE_BEANS = 12
const val CAP_COST = 6

// function that shows the info about coffee machine
fun remaining() {
    println("The coffee machine has:\n" +
            "$waterAmount ml of water\n" +
            "$milkAmount ml of milk\n" +
            "$coffeeBeansAmount g of coffee beans\n" +
            "$disposableCups disposable cups\n" +
            "\$$money of money")
}
fun buy() {
    print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:  ")
    when (readln()) {
        "1" -> {
            if (waterAmount < ESP_WATER) {
                println("Sorry, not enough water!")
            } else if (coffeeBeansAmount < ESP_COFFEE_BEANS) {
                println("Sorry, not enough coffee beans!")
            } else if (disposableCups <= 0) {
                println("Sorry, not enough cups!")
            } else {
                println("I have enough resources, making you a coffee!")
                waterAmount -= ESP_WATER
                coffeeBeansAmount -= ESP_COFFEE_BEANS
                disposableCups -= 1
                money += ESP_COST
            }
        }
        "2" -> {
            if (waterAmount < LAT_WATER) {
                println("Sorry, not enough water!")
            } else if (milkAmount < LAT_MILK) {
                println("Sorry, not enough milk!")
            } else if (coffeeBeansAmount < LAT_COFFEE_BEANS) {
                println("Sorry, not enough coffee beans!")
            } else if (disposableCups <= 0) {
                println("Sorry, not enough cups!")
            } else {
                println("I have enough resources, making you a coffee!")
                waterAmount -= LAT_WATER
                milkAmount -= LAT_MILK
                coffeeBeansAmount -= LAT_COFFEE_BEANS
                disposableCups -= 1
                money += LAT_COST
            }
        }
        "3" -> {
            if (waterAmount < CAP_WATER) {
                println("Sorry, not enough water!")
            } else if (milkAmount < CAP_MILK) {
                println("Sorry, not enough milk!")
            } else if (coffeeBeansAmount < CAP_COFFEE_BEANS) {
                println("Sorry, not enough coffee beans!")
            } else if (disposableCups <= 0) {
                println("Sorry, not enough cups!")
            } else {
                println("I have enough resources, making you a coffee!")
                waterAmount -= CAP_WATER
                milkAmount -= CAP_MILK
                coffeeBeansAmount -= CAP_COFFEE_BEANS
                disposableCups -= 1
                money += CAP_COST
            }
        }
        // returning to main menu
        "back" -> return
    }
}
fun fill() {
    print("Write how many ml of water do you want to add: ")
    waterAmount += readln().toInt()
    print("Write how many ml of milk do you want to add: ")
    milkAmount += readln().toInt()
    print("Write how many grams of coffee beans do you want to add: ")
    coffeeBeansAmount += readln().toInt()
    print("Write how many disposable cups of coffee do you want to add: ")
    disposableCups += readln().toInt()
}
fun take() {
    println("I gave you \$$money")
    money = 0
}

// amount of supplies at the beginning
var waterAmount: Int = 400
var milkAmount: Int = 540
var coffeeBeansAmount: Int = 120
var disposableCups: Int = 9
var money: Int = 550

fun main() {
    while (true) {
        print("Write action (buy, fill, take, remaining, exit): ")
        when (readln()) {
            "buy" -> buy()
            "fill" -> fill()
            "take" -> take()
            "remaining" -> remaining()
            "exit" -> break
        }
        println() //print blank line as separator
    }
}