package sourcefiles

import kotlin.math.roundToInt
import com.example.util.CurrencyFormatter

// Top-level function
fun formatPrice(price: Double): String =
    CurrencyFormatter.format(price)

// Top-level constant property
const val SALES_TAX_RATE = 0.07

// Top-level non-constant property
var shopName: String = "My Awesome Shop"

// Top-level class
class Product(val id: Int, val name: String, val price: Double) {
    fun calculatePriceWithTax(): Double =
        price * (1 + SALES_TAX_RATE)
}

// Top-level object
object ProductCatalogue {
    val products = mutableListOf<Product>()

    fun addProduct(product: Product) {
        products.add(product)
    }
}

// Top-level type alias
typealias ShoppingBasket = MutableList<Product>
