package inheritance

import java.util.UUID

open class Person(
    val id: String,
    val name: String,
) {
    open fun introduce() = "Hello, my name is $name."
}

class Dentist(
    id: String,
    name: String,
) : Person(id, name) {

    override fun introduce() = "${super.introduce()} I'm a dentist."
}

class Patient(
    id: String,
    name: String,
) : Person(id, name) {
    var insurance: String? = null

    constructor(id: String, name: String, insurance: String)
            : this(id, name) {
        println("Secondary constructor called. Insurance: $insurance")
        this.insurance = insurance
    }

    override fun introduce() = "${super.introduce()} I'm a patient."
}

fun exampleOverrideProperties(){
    open class Person(
        open val id: String,
        open val name: String,
    ) {
        open fun introduce() = "Hello, my name is $name."
    }
    class Dentist(
        override val id: String = UUID.randomUUID().toString(),
        override val name: String = "Default Name",
    ) : Person(id, name) {

        override fun introduce() = "${super.introduce()} I'm a dentist."
    }
    class Patient(
        id: String,
        name: String,
    ) : Person(id, name) {
        var insurance: String? = null

        constructor(id: String, name: String, insurance: String)
                : this(id, name) {
            println("Secondary constructor called. Insurance: $insurance")
            this.insurance = insurance
        }

        override fun introduce() = "${super.introduce()} I'm a patient."
    }
}

