package namingandorganisation.modifiers

// Abstract class with a protected, suspend function
abstract class ProductRepository {
    protected abstract suspend fun getProductList(): List<Product>
}

@JvmInline
value class ProductId(val id: Int)

class Product {

}

