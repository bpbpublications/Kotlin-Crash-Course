package nullsafety

// Get a nullable customer
val customer: Customer? = getCustomerById(1)

// Use safe call operator to access name
val customerName = customer?.name

// Use Elvis operator to provide a default value
val customerNameOrDefault = customer?.name ?: "Unknown Customer"

// Get a nullable product
val product: Product? = getProductById(1)
// Force the compiler to assume product is not null
val nonNullProduct = product!!

// Use safe cast operator to cast to SpecialProduct
val specialProduct = product as? SpecialProduct

class Customer(val name: String)
open class Product
class SpecialProduct : Product()

fun getProductById(i: Int): Product? = null

fun getCustomerById(i: Int): Customer? = null




