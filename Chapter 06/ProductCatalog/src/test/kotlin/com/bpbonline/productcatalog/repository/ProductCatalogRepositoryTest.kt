package com.bpbonline.productcatalog.repository

import com.bpbonline.productcatalog.model.Product
import com.bpbonline.productcatalog.model.ProductCategory
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ProductCatalogRepositoryTest {

    @BeforeEach
    fun setUp() { // Resets repository before each test
        ProductCatalogRepository.reset()
    }

    @Test
    fun `test fetch all products`() {
        val products =
            ProductCatalogRepository.getAllProducts()
        assertEquals(3, products.size)
        assertEquals("Laptop", products[0].name)
    }

    @Test
    fun `test fetch product by ID`() {
        val product =
            ProductCatalogRepository.getProductById(2)
        assertEquals("Phone", product?.name)
    }

    @Test
    fun `test add product`() {
        val newProduct = Product(4, "iPad", 650.0)
        ProductCatalogRepository.addProduct(newProduct)
        assertEquals(4, ProductCatalogRepository.getAllProducts().size)
    }

    @Test
    fun `test update product price by ID`() {
        ProductCatalogRepository.updateProductPriceById(
            1,
            1100.0
        )
        val updatedProduct =
            ProductCatalogRepository.getProductById(1)
        assertEquals(1100.0, updatedProduct?.price)
    }

    @Test
    fun `test remove product by ID`() {
        ProductCatalogRepository.removeProductById(2)
        assertEquals(2, ProductCatalogRepository.getAllProducts().size)
        assertNull(
            ProductCatalogRepository.getProductById(
                2
            )
        )
    }

    @Test
    fun `test all categories is a union of product categories`() {
        val expectedCategories = setOf(
            ProductCategory.FURNITURE,
            ProductCategory.BEDDING,
            ProductCategory.KITCHENWARE,
            ProductCategory.CLOTHING,
            ProductCategory.TOYS
        )

        val result =
            ProductCatalogRepository.allCategories()

        assertEquals(expectedCategories, result)
    }

    @Test
    fun `test get products in range`() {
        val filteredProducts =
            ProductCatalogRepository.getProductsInRange(
                500.0,
                1000.0
            )
        assertEquals(2, filteredProducts.size)
        assertTrue(filteredProducts.all { it.price in 500.0..1000.0 })
    }

    @Test
    fun `test products sorted by price in ascending order`() {
        val sortedProducts =
            ProductCatalogRepository.getProductsSortedByPrice()

        assertEquals(3, sortedProducts.size)
        assertEquals(100.0, sortedProducts[0].price)
        assertEquals(500.0, sortedProducts[1].price)
        assertEquals(1000.0, sortedProducts[2].price)
    }

    @Test
    fun `test products sorted by price in descending order`() {
        val sortedProducts =
            ProductCatalogRepository.getProductsSortedByPriceDescending()

        assertEquals(3, sortedProducts.size)
        assertEquals(1000.0, sortedProducts[0].price)
        assertEquals(500.0, sortedProducts[1].price)
        assertEquals(100.0, sortedProducts[2].price)
    }


}
