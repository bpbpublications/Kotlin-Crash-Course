package com.bpbonline.productcatalog.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ProductCategoryTest{

    @Test
    fun `test get category by display sequence`() {
        assertEquals(
            ProductCategory.ELECTRONICS,
            ProductCategory.getCategoryBySequence(2)
        )
        assertEquals(
            ProductCategory.FURNITURE,
            ProductCategory.getCategoryBySequence(4)
        )
        assertEquals(
            ProductCategory.CLOTHING,
            ProductCategory.getCategoryBySequence(1)
        )
        // Non-existing sequence number should return null
        assertNull(
            ProductCategory.getCategoryBySequence(10)
        )
    }
}