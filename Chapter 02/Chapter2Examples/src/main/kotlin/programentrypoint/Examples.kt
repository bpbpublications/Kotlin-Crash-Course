package programentrypoint

// The program entry point - the main() function
fun main() {
    println("Welcome to the Kotlin Shop Application!")
}

fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        println("Command-line arguments:")
        args.forEach { arg -> println(arg) }
    }
}
