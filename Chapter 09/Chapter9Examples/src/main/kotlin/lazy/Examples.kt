package lazy

val expensiveResource: Resource by lazy {
    Resource()
}


val milkFrother: MilkFrother by lazy {
    MilkFrother()
}

class Resource {
    // very expensive initialization
}

class MilkFrother {
    // very expensive initialization
}
