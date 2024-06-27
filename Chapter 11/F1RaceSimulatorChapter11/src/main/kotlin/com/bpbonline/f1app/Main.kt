package com.bpbonline.f1app

import com.bpbonline.f1app.participants.Driver
import com.bpbonline.f1app.participants.RaceCar
import com.bpbonline.f1app.participants.Team
import kotlinx.coroutines.runBlocking

const val MAX_LAPS = 5
const val MAX_TEAMS = 10
const val MAX_DRIVERS = MAX_TEAMS * 2

// Top-level mutable variable, accessible throughout the project
var currentWeather: String = "Sunny"

suspend fun runCustomRace() {
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

suspend fun runPreconfiguredRace() {
    val numberOfLaps = 3 // Predefined number of laps

    val teamHamilton = Team(
        name = "Mercedes",
        drivers = listOf(
            Driver("Lewis Hamilton"),
            Driver("Valtteri Bottas"),
        ),
        raceCars = setOf(
            RaceCar(carNumber = 44, numLaps = numberOfLaps),
            RaceCar(carNumber = 77, numLaps = numberOfLaps),
        )
    )

    val teamVerstappen = Team(
        name = "Red Bull",
        drivers = listOf(
            Driver("Max Verstappen"),
            Driver("Sergio Perez"),
        ),
        raceCars = setOf(
            RaceCar(carNumber = 33, numLaps = numberOfLaps),
            RaceCar(carNumber = 11, numLaps = numberOfLaps),
        )
    )

    Race(
        numberOfLaps = numberOfLaps,
        teams = listOf(teamHamilton, teamVerstappen)
    ).runRace()
}

fun main() = runBlocking {
    println("Choose an option:")
    println("1. Enter race details manually")
    println("2. Run preconfigured race (default)")

    val choice = readLine()?.toIntOrNull() ?: 2

    when (choice) {
        2 -> runPreconfiguredRace()
        else -> runCustomRace()
    }
}

