package staticmethod

import java.util.UUID

class IdGenerator {
    fun generateId() = UUID.randomUUID().toString()
}