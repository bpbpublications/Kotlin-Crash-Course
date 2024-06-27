package apply

import java.time.LocalDate

data class Item(var name: String, var value: Double) {
    var description: String = ""
}

val myItem = Item("OldItem", 100.0)

fun applyGenericExample(){
    val updatedItem = myItem.apply {
        // Inside this block, 'this' refers to the myItem object.
        name = "NewItem"
        value = 150.0
        description = "Updated description"
    }

    println("Updated item: $updatedItem")
}

data class Inventory(
    var stockCount: Int,
    var lastRestocked: LocalDate? = null,
    var soldCount: Int = 0
)

fun withoutApplyExample(){
    val inventoryUpdate = Inventory(10)

    inventoryUpdate.stockCount += 50 // adding 50 more items to stock
    inventoryUpdate.lastRestocked = LocalDate.now()
    inventoryUpdate.soldCount = 0 // resetting the sold count
}

fun withApplyExample(){
    val inventoryUpdate = Inventory(10)

    inventoryUpdate.apply {
        stockCount += 50 // adding 50 more items to stock
        lastRestocked = LocalDate.now()
        soldCount = 0 // resetting the sold count
    }

}

fun addProductToCatalog(product: Any) {

}

