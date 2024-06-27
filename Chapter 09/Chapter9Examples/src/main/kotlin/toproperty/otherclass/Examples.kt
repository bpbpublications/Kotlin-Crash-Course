package toproperty.otherclass

import java.time.LocalDate

// MaintenanceLog class holds maintenance details for a coffee machine
class MaintenanceLog {
    var lastServiceDate: LocalDate = LocalDate.now()
}

// CoffeeMachine class that receives a MaintenanceLog instance in its constructor
class CoffeeMachine(private val maintenanceLog: MaintenanceLog) {
    // Delegate the lastServiceDate property to the MaintenanceLog instance
    var lastServiceDate: LocalDate by maintenanceLog::lastServiceDate
}

fun main() {
    // Create a MaintenanceLog instance
    val log = MaintenanceLog()

    // Create a CoffeeMachine instance with the log
    val myCoffeeMachine = CoffeeMachine(log)

    // Print the last service date using the CoffeeMachine instance
    println("Last service was on: ${myCoffeeMachine.lastServiceDate}")

    // Set a new service date through the CoffeeMachine instance
    myCoffeeMachine.lastServiceDate = LocalDate.of(2023, 11, 4)

    // Print the updated last service date
    println("New service date set to: ${myCoffeeMachine.lastServiceDate}")
}
