package machine

// const vals for one cup of coffee
const val WATER = 200
const val MILK = 50
const val COFFEE_BEANS = 15

fun main() {
    println("Starting to make a coffee\n" +
            "Grinding coffee beans\n" +
            "Boiling water\n" +
            "Mixing boiled water with crushed coffee beans\n" +
            "Pouring coffee into the cup\n" +
            "Pouring some milk into the cup\n" +
            "Coffee is ready!")

    print("Write how many ml of water the coffee machine has: ")
    val waterAmount: Int = readln().toInt()
    print("Write how many ml of milk the coffee machine has: ")
    val milkAmount: Int = readln().toInt()
    print("Write how many grams of coffee beans the coffee machine has: ")
    val coffeeBeansAmount: Int = readln().toInt()
    print("Write how many cups of coffee you will need: ")
    val amount: Int = readln().toInt()
    // how many cups can be made with this amount
    val cups = minOf(waterAmount / WATER, milkAmount / MILK, coffeeBeansAmount / COFFEE_BEANS)
    if (cups > amount) {
        println("Yes, I can make that amount of coffee (and even ${cups - amount} more than that)")
    } else if (cups == amount) {
        println("Yes, I can make that amount of coffee")
    } else {
        println("No, I can make only $cups cups of coffee")
    }
}