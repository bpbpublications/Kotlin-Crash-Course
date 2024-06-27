package com.bpbonline.dentalclinic.service

object PersonIdRegistry {
    private val registeredIDs = mutableSetOf<String>()

    fun registerID(id: String) {
        require(registeredIDs.add(id)) { "ID already exists: $id" }
    }

    fun unregisterID(id: String) {
        registeredIDs.remove(id)
    }
}
