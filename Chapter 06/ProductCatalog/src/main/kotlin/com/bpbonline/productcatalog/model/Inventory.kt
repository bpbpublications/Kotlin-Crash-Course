package com.bpbonline.productcatalog.model

import java.time.LocalDate

data class Inventory(
    var stockCount: Int,
    var lastRestocked: LocalDate? = null,
    var soldCount: Int = 0
)

