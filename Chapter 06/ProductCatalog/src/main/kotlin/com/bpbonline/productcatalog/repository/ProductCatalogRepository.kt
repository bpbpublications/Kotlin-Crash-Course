package com.bpbonline.productcatalog.repository

import com.bpbonline.productcatalog.model.Product
import com.bpbonline.productcatalog.model.ProductCategory

object ProductCatalogRepository {
    private val products: MutableList<Product> = mutableListOf()

    private val householdCategories: Set<ProductCategory> = setOf(
        ProductCategory.FURNITURE,
        ProductCategory.BEDDING,
        ProductCategory.KITCHENWARE
    )

    private val personalCategories: Set<ProductCategory> = setOf(
        ProductCategory.CLOTHING,
        ProductCategory.TOYS,
        ProductCategory.BEDDING
    )

    init {
        // Reset repository to initial state
        reset()
    }

    internal fun reset() {
        // Clear all products
        products.clear()
        // Initialize the repository with some sample products
        products.add(Product(1, "Laptop", 1000.0))
        products.add(Product(2, "Phone", 500.0))
        products.add(Product(3, "Headphones", 100.0))
    }

    // Fetch all products
    fun getAllProducts(): List<Product> = products.toList()

    fun getProductById(id: Int): Product? = products.find { it.id == id }

    fun addProduct(product: Product) {
        products.add(product)
    }

    fun updateProductPriceById(id: Int, newPrice: Double) {
        val index = products.indexOfFirst { it.id == id }
        if (index != -1) { // Product is found
            val updatedProduct = products[index].copy(price = newPrice)
            products[index] = updatedProduct
        }
    }

    fun removeProductById(id: Int) {
        products.removeAll { it.id == id }
    }

    // Returns all distinct categories from both sets
    fun allCategories(): Set<ProductCategory> {
        return householdCategories union personalCategories
    }

    fun getProductsInRange(
        minPrice: Double,
        maxPrice: Double,
    ): List<Product> {
        return products.filter { it.price in minPrice..maxPrice }
    }

    fun getProductsSortedByPrice(): List<Product> {
        return products.sortedBy { it.price }
    }

    fun getProductsSortedByPriceDescending(): List<Product> {
        return products.sortedByDescending { it.price }
    }

}
