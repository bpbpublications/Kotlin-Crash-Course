package com.bpbonline.productcatalog.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val productCategory: ProductCategory,
    ) {
    constructor(
        id: Int,
        name: String,
        price: Double,
    ) : this(
        id,
        name,
        price,
        ProductCategory.ELECTRONICS,
    )

}
