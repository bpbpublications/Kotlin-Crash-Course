package com.bpbonline.f1app.participants

import java.util.*

class Driver(
    val name: String,
    var points: Int = 0,
    val uuid: UUID = UUID.randomUUID(),
) {
    fun addPoints(newPoints: Int) {
        points += newPoints
    }

    override fun toString(): String {
        return "Driver(name='$name', points=$points)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Driver

        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }

}