package extensionfunction

class StringTransformer {
    fun transform(input: String) = input.myExtensionFunction()
}

fun String.myExtensionFunction(): String = this.reversed()