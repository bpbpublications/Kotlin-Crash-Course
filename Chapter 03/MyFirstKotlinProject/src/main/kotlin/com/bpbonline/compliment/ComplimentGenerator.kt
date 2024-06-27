package com.bpbonline.compliment

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * This class is responsible for generating random compliments.
 * A compliment is a combination of a randomly selected adjective and subject.
 */
class ComplimentGenerator {
    private val adjectives = listOf("Great", "Amazing", "Incredible", "Fantastic", "Awesome")
    private val subjects = listOf("job", "work", "effort", "performance", "attitude")

    /**
     * Generates a random compliment by picking one adjective and one subject randomly.
     * Logs the generated compliment.
     *
     * @return a string representing the generated compliment.
     */
    fun generate(): String {
        // String template of a random adjective and a random subject
        val compliment = "${adjectives.random()} ${subjects.random()}"
        logger.info("Generated compliment: $compliment")
        // Return the generated compliment
        return compliment
    }

}

fun main() {
    // Create an instance of ComplimentGenerator
    val generator = ComplimentGenerator()

    // Generate and print 5 random compliments
    repeat(5) {
        // Call the generate method on the instance
        // and print the returned compliment to the console
        println("Compliment: ${generator.generate()}")
    }
}
