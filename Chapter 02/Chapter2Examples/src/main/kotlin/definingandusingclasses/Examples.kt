package definingandusingclasses

class Product(val name: String, val price: Double) {
    val isExpensive: Boolean

    init {
        isExpensive = price > 50
    }

    constructor(name: String) : this(name, 0.0) {
        println("Creating a product with unknown price")
    }

    fun displayDetails() {
        println("Product name: $name")
        println("Product price: $price")
    }

    companion object {
        const val DEFAULT_NAME = "Sample Product"
        fun createSampleProduct(): Product {
            return Product(DEFAULT_NAME, 0.0)
        }
    }
}

fun exampleCreatingInstancesAndUsingPropertiesAndMethods() {
    //use primary constructor
    val productA = Product("ProductA", 49.99)

    //use secondary constructor
    val productB = Product("ProductB")

    //use a function from companion object, which calls a constructor
    val productC = Product.createSampleProduct()

    //Access a method
    println("Product details: ${productB.displayDetails()}")
    //Access a property
    println("Product is expensive: ${productB.isExpensive}")
}



