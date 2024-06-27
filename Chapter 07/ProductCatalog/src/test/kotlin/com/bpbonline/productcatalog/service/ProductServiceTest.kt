package com.bpbonline.productcatalog.service

import com.bpbonline.productcatalog.api.dto.CreateProductDTO
import com.bpbonline.productcatalog.api.dto.ProductUpdateDTO
import com.bpbonline.productcatalog.model.Product
import com.bpbonline.productcatalog.model.ProductCategory
import com.bpbonline.productcatalog.repository.InventoryRepository
import com.bpbonline.productcatalog.repository.ProductCatalogRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class ProductServiceTest {

    private lateinit var productService: ProductService
    private lateinit var initialProduct: Product

    @BeforeEach
    fun setUp() {
        ProductCatalogRepository.reset()
        // Prepare data
        initialProduct = Product(
            4,
            "Smartphone",
            1000.0,
            ProductCategory.ELECTRONICS
        )
        ProductCatalogRepository.addProduct(
            initialProduct
        )
        InventoryRepository.addInitialStock(
            initialProduct.id,
            50
        )
        productService = ProductService()
    }

    @Test
    fun `test get product details by ID`() {
        // Get product we just set up
        val productDetailsDTO = productService.getProductDetailsById(4)

        // Assert
        assertNotNull(productDetailsDTO)
        assertEquals(4, productDetailsDTO!!.id)
        assertEquals("Smartphone", productDetailsDTO.name)
        assertEquals(1000.0, productDetailsDTO.price)
        assertEquals(
            ProductCategory.ELECTRONICS,
            productDetailsDTO.category,
        )
        assertEquals(50, productDetailsDTO.stockCount)
        assertEquals(LocalDate.now(), productDetailsDTO.lastRestocked)
    }

    @Test
    fun `test get product details by ID for nonexistent product`() {
        // Some non-existent product ID
        val productDetailsDTO = productService.getProductDetailsById(99)

        // Assert
        assertNull(productDetailsDTO)
    }

    @Test
    fun `test update product details`() {
        // Prepare data
        val initialProduct = Product(
            4,
            "Smartphone",
            1000.0,
            ProductCategory.ELECTRONICS
        )
        ProductCatalogRepository.addProduct(initialProduct)
        InventoryRepository.addInitialStock(initialProduct.id, 50)

        // Update product details
        val updateDTO = ProductUpdateDTO(
            productId = initialProduct.id,
            newPrice = 1100.0,
            stockAdjustment = 20
        )
        productService.updateProductDetails(updateDTO)

        // Validate updated details
        assertEquals(
            1100.0,
            ProductCatalogRepository.getProductById(
                initialProduct.id
            )?.price
        )
        assertEquals(
            70, // Initial 50 + 20
            InventoryRepository.getStockForProduct(
                initialProduct.id
            )?.stockCount
        )
    }

    @Test
    fun `test create product`() {
        // Given
        val dto = CreateProductDTO(
            id = 1,
            name = "TestProduct",
            initialPrice = 100.0,
            ProductCategory.ELECTRONICS,
            10
        )

        // When
        val resultProduct =
            productService.createProduct(dto)

        // Then
        assertEquals(dto.id, resultProduct.id)
        assertEquals(dto.name, resultProduct.name)
        assertEquals(dto.initialPrice, resultProduct.price)
        assertEquals(dto.category, resultProduct.productCategory)
        // Validate: product has been added to the ProductCatalogRepository
        val productFromCatalog =
            ProductCatalogRepository.getProductById(dto.id)
        assertNotNull(productFromCatalog)
        assertEquals(dto.id, productFromCatalog?.id)
        // Validate: initial stock has been added to the InventoryRepository
        val stockCount =
            InventoryRepository.getStockForProduct(dto.id)?.stockCount
        assertEquals(dto.initialStock, stockCount)
    }
}
