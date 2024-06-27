package com.bpbonline.f1app

import com.bpbonline.f1app.participants.Driver
import com.bpbonline.f1app.participants.RaceCar
import com.bpbonline.f1app.participants.Team

const val MAX_LAPS = 5
const val MAX_TEAMS = 10
const val MAX_DRIVERS = MAX_TEAMS * 2

// Top-level mutable variable, accessible throughout the project
var currentWeather: String = "Sunny"

fun main() {
    fun promptString(prompt: String): String {
        print(prompt)
        return readLine() ?: ""
    }
    // Nested functions to prompt for user input
    fun promptInt(prompt: String, max: Int): Int {
        var value: Int
        do {
            print(prompt)
            value = readLine()?.toIntOrNull() ?: 0
        } while (value !in 1..max)
        return value
    }

    fun createDriver(name: String) = Driver(name)

    fun createTeam(name: String, numLaps: Int) = Team(
        name,
        listOf(
            createDriver(
                name = promptString(
                    prompt = "Enter name for driver 1 of team $name: "
                )
            ),
            createDriver(
                name = promptString(
                    prompt = "Enter name for driver 2 of team $name: "
                )
            ),
        ),
        setOf(
            RaceCar(carNumber = 1, numLaps = numLaps),
            RaceCar(carNumber = 2, numLaps = numLaps),
        ),
    )

    val numLaps = promptInt(
        prompt = "Enter number of laps (up to $MAX_LAPS): ",
        max = MAX_LAPS,
    )

    val numTeams = promptInt(
        prompt = "Enter number of teams (up to $MAX_TEAMS): ",
        max = MAX_TEAMS,
    )

    val teams = List(numTeams) {
        val teamName = promptString(
            prompt = "Enter name for team ${it + 1}: "
        )
        createTeam(name = teamName, numLaps = numLaps)
    }

    val race = Race(numberOfLaps = numLaps, teams = teams)
    race.runRace()
}

