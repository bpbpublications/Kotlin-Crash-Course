package visibilitymodifiers

// Accessible from any Kotlin file
class Product(val id: Int, val name: String, val price: Double)

// Accessible only within the same file
private fun calculateDiscount(price: Double,
                              rate: Double) = price * rate
// Accessible only within the same module
internal fun checkProductAvailability(
    product: Product,
    requiredQuantity: Int,
): Boolean {
    val currentStock = getStockForProduct(product)
    return currentStock >= requiredQuantity
}

fun getStockForProduct(product: Product): Int = 0

open class ShopUser(private val accountId: String) {
    // protected - accessible within the class and its subclasses
    protected fun getAccountInfo() = "Account ID: $accountId"
}

class Customer(accountId: String) : ShopUser(accountId) {

    // Accessible only within the Customer class
    private fun sendEmailNotification() { }

    fun printAccountInfo() {
        // Accessible since it's a subclass of ShopUser
        println(getAccountInfo())
    }
}

