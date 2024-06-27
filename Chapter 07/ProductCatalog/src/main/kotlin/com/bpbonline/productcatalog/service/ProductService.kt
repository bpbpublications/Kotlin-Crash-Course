package com.bpbonline.productcatalog.service

import com.bpbonline.productcatalog.api.dto.CreateProductDTO
import com.bpbonline.productcatalog.api.dto.ProductDetailsDTO
import com.bpbonline.productcatalog.api.dto.ProductUpdateDTO
import com.bpbonline.productcatalog.model.Product
import com.bpbonline.productcatalog.repository.InventoryRepository
import com.bpbonline.productcatalog.repository.ProductCatalogRepository
import org.springframework.stereotype.Service

@Service
class ProductService {
    fun getProductDetailsById(productId: Int) =
        ProductCatalogRepository.getProductById(
            productId
        )?.toDto()

    fun updateProductDetails(productUpdateDTO: ProductUpdateDTO) {
        // Within 'with' block access productUpdateDTO's properties directly.
        with(productUpdateDTO) {
            newPrice?.let {
                ProductCatalogRepository.updateProductPriceById(productId,it)
            }
            stockAdjustment?.let {
                InventoryRepository.updateStock(productId,it)
            }
        }
    }


    private fun Product.toDto(): ProductDetailsDTO? {
        // Fetch the inventory details for the given product.
        val nullableInventory =
            InventoryRepository.getStockForProduct(
                id
            )

        // 'let' is used to perform an action if inventory is not null.
        return nullableInventory?.let { inventory ->
            // Construct the DTO using the non-null product & inventory.
            ProductDetailsDTO(
                id = id,
                name = name,
                price = price,
                category = productCategory,
                stockCount = inventory.stockCount,
                lastRestocked = inventory.lastRestocked
            )
        }
    }
    fun createProduct(dto: CreateProductDTO): Product {
        return with(dto) {
            Product(
                id = id,
                name = name,
                price = initialPrice,
                productCategory = category
            ).also {
                ProductCatalogRepository.addProduct(it)
                InventoryRepository.addInitialStock(it.id, initialStock)
            }
        }
    }


}
