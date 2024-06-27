package typeinference

// Type inference: return type inferred to be String
fun productDescription(product: Product) =
    "Product: ${product.name}, Price: ${product.price}"

// Type inference: List<Product>
val products = listOf(
    Product("Phone", 299.99),
    Product("Laptop", 999.99),
    Product("Headphones", 49.99),
)

// Type inference: Double (expression)
val totalPrice = products.sumOf { it.price }

// Type inference: List<String> (collection)
val productDescriptions = products.map(::productDescription)

// Type inference: inferred to be (Product) -> Boolean
val isExpensive = { product: Product -> product.price > 500 }

// Type inference: List<Product>
val expensiveProducts = products.filter(isExpensive)

class Product(val name: String, val price: Double)
