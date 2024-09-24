package dev.flavius.bow

import dev.flavius.bow.data.BowSpeciesRepository
import dev.flavius.bow.data.BowSpeciesRepository.Constants.SPECIES_LIST_URL
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        BowSpeciesRepository().getAllOrders().run {
            println(
                "There are $size orders in the taxonomy at Birds of the World ($SPECIES_LIST_URL)."
            )
            forEach { order ->
                println("\n${order.scientificName}")
                order.families.forEach { family ->
                    println(
                        "\t${family.commonName} (${family.scientificName}) -> " +
                                "${family.species.size} species"
                    )
                }
            }
        }
    }
}

