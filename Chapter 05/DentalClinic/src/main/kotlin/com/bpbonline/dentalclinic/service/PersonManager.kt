package com.bpbonline.dentalclinic.service

import com.bpbonline.dentalclinic.model.Person

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

    fun findPersonsByCriteria(criteria: (T) -> Boolean): List<T> {
        return persons.values.filter(criteria)
    }
}
