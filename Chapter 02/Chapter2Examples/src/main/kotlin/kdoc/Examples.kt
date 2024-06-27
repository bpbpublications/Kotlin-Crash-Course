/**
 * This file contains the implementation of the
 * Shop, Product, and ShoppingCart classes. These
 * classes are used to represent the basic structure
 * of an online store.
 */

package kdoc

/**
 * This is a KDoc documentation comment for the Product class
 *
 * @property id The unique identifier of the product
 * @property name The name of the product
 * @property price The price of the product
 */
data class Product(val id: Int, val name: String, val price: Double)

/**
 * Calculates the discounted price for a given original price
 * and discount percentage.
 *
 * @param price The original price of the item.
 * @param discount The discount percentage to apply.
 * @return The discounted price.
 * @throws IllegalArgumentException If the discount percentage
 * is not in the range of 0 to 100.
 */
fun calculateDiscount(price: Double, discount: Double): Double {
    /*...*/
    return 0.0
}

class Shop{
    /** The tax rate applied to each item in the shopping cart. */
    val taxRate = 0.05

}