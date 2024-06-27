package com.bpbonline.productcatalog.service

import com.bpbonline.productcatalog.model.Product
import com.bpbonline.productcatalog.model.ProductCategory
import com.bpbonline.productcatalog.repository.ProductCatalogRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ProductCatalogServiceTest {

    private lateinit var productCatalogService: ProductCatalogService

    @BeforeEach
    fun setUp() {
        ProductCatalogRepository.reset()
        productCatalogService = ProductCatalogService()
    }


    @Test
    fun `test top three expensive products`() {
        val topProducts =
            productCatalogService.getTopThreeExpensiveProducts()
        assertEquals(3, topProducts.size)
        assertEquals("Laptop", topProducts[0].name)
        assertEquals(1000.0, topProducts[0].price)
        assertEquals("Phone", topProducts[1].name)
        assertEquals(500.0, topProducts[1].price)
        assertEquals("Headphones", topProducts[2].name)
        assertEquals(100.0, topProducts[2].price)
    }

    @Test
    fun `test get number of items in electronics category`() {
        val numberOfItems =
            productCatalogService.getNumberOfItemsInCategory(
                ProductCategory.ELECTRONICS
            )
        assertEquals(3, numberOfItems)
    }

    @Test
    fun `test get number of items in an empty category`() {
        val numberOfItems =
            productCatalogService.getNumberOfItemsInCategory(
                ProductCategory.KITCHENWARE
            )
        assertEquals(0, numberOfItems)
    }

    @Test
    fun `test get average price in electronics category`() {
        ProductCatalogRepository.addProduct(
            Product(
                4,
                "Tablet",
                600.0,
                ProductCategory.ELECTRONICS
            )
        )
        val avgPrice =
            productCatalogService.getAveragePriceInCategory(
                ProductCategory.ELECTRONICS
            )
        assertEquals(550.0, avgPrice)
    }

    @Test
    fun `test get average price in an empty category`() {
        val avgPrice =
            productCatalogService.getAveragePriceInCategory(
                ProductCategory.KITCHENWARE
            )
        assertTrue(avgPrice.isNaN())
    }

    @Test
    fun `test get all product names in alphabetical order`() {
        val productNames = productCatalogService.getAllProductNames()

        val expectedNames = setOf("Headphones", "Laptop", "Phone")

        assertEquals(expectedNames, productNames)
    }

    @Test
    fun `test search products by name`() {
        // Repository already has: Phone, Headphones, and Laptop
        ProductCatalogRepository.addProduct(
            Product(
                4,
                "Smartphone",
                1000.0,
                ProductCategory.ELECTRONICS
            )
        )

        val searchResults =
            productCatalogService.searchProductsByName("phone")

        // Validate the results
        assertEquals(3, searchResults.size)
        assertTrue(searchResults.any { it.name == "Phone" })
        assertTrue(searchResults.any { it.name == "Smartphone" })
        assertTrue(searchResults.any { it.name == "Headphones" })
        assertFalse(searchResults.any { it.name == "Laptop" })
    }

    @Test
    fun `test get product count by category`() {
        val counts = productCatalogService.getProductCountByCategory()

        assertEquals(1, counts.size)
        assertTrue(counts.containsKey(ProductCategory.ELECTRONICS))
        assertEquals(3, counts[ProductCategory.ELECTRONICS])
    }
}
