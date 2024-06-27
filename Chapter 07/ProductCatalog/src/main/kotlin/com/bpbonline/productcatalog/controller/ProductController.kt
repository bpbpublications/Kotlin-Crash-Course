package com.bpbonline.productcatalog.controller

import com.bpbonline.productcatalog.api.dto.CreateProductDTO
import com.bpbonline.productcatalog.api.dto.ProductUpdateDTO
import com.bpbonline.productcatalog.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(private val productService: ProductService) {
    @GetMapping("/{productId}")
    fun getProductDetailsById(
        @PathVariable productId: Int,
    ): ResponseEntity<Any> {
        val productDetailsDTO =
            productService.getProductDetailsById(
                productId
            )
        return if (productDetailsDTO != null) {
            ResponseEntity.ok(productDetailsDTO)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PatchMapping("/")
    fun updateProductDetails(
        @RequestBody productUpdateDTO: ProductUpdateDTO,
    ): ResponseEntity<String> {
        return try {
            productService.updateProductDetails(
                productUpdateDTO
            )
            ResponseEntity.ok("Product details updated successfully.")
        } catch (e: Exception) {
            // handle the exception as needed, e.g. log it
            ResponseEntity.badRequest()
                .body("Error updating product details: ${e.message}")
        }
    }

    @PostMapping("/")
    fun createProduct(
        @RequestBody dto: CreateProductDTO,
    ): ResponseEntity<Any> {
        // Check if the product with the given ID already exists.
        productService.getProductDetailsById(dto.id)?.let {
            // If it exists, return a conflict status.
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
        // If not, create the product and return a created status.
        productService.createProduct(dto).let {
            return ResponseEntity.status(HttpStatus.CREATED).build()
        }
    }

}
