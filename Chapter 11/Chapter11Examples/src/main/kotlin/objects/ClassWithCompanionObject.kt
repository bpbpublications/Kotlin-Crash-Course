package objects


class ClassWithCompanionObject {
    fun useCompanionMethod() = myCompanionMethod()

    companion object {
        fun myCompanionMethod(): String = "Original Response"
    }
}