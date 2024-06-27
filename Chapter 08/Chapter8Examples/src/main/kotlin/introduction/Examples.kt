package introduction

fun square(number: Int): Int = number * number

val numbers = listOf(1, 2, 3, 4, 5)
val doubled = numbers.map { it * 2 }

data class User(val name: String, val age: Int)
val user = User("Alex", 29)
val olderUser = user.copy(age = 30)
