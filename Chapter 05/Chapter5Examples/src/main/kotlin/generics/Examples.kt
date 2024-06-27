package generics

class PersonManager<T : Person> {
    private val persons = mutableMapOf<String, T>()

    fun addPerson(person: T) {
        PersonIdRegistry.registerID(person.id)
        persons[person.id] = person
    }

    fun removePerson(id: String) {
        persons.remove(id)?.let {
            PersonIdRegistry.unregisterID(it.id)
        }
    }

    fun getPersonById(id: String): T? = persons[id]
}

inline fun <reified T : Person> findPersonAcrossManagers(
    id: String,
    vararg managers: PersonManager<*>,
): T? {
    for (manager in managers) {
        val person = manager.getPersonById(id)
        if (person is T) {
            return person
        }
    }
    return null
}


open class Person(val id: String)

object PersonIdRegistry {
    private val registeredIDs = mutableSetOf<String>()

    fun registerID(id: String) {
        if (!registeredIDs.add(id)) {
            throw IllegalArgumentException("ID already exists: $id")
        }
    }

    fun unregisterID(id: String) {
        registeredIDs.remove(id)
    }
}
