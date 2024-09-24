package dev.flavius.bow.data

import dev.flavius.bow.data.BowSpeciesRepository.Constants.BASE_URL
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BowSpeciesRepositoryTest {
    @Test fun `parses the BOW page html`() {
        val orders = runBlocking { BowSpeciesRepository().getAllOrders() }
        assertEquals(41, orders.size)
        assertTrue { orders.all { it.scientificName.isNotBlank() } }
        orders
            .first { it.scientificName.lowercase() == "anseriformes" }
            .families
            .run {
                assertEquals(3, size)
                first { family -> family.commonName.lowercase() == "screamers" }
                    .species
                    .let { screamers ->
                        assertEquals(3, screamers.size)
                        assertTrue {
                            screamers.all { it.commonName.lowercase().endsWith("screamer") }
                            screamers.all { it.url.startsWith(BASE_URL) }
                            screamers.all { it.scientificName.isNotBlank() }
                        }
                    }
                first { it.scientificName.lowercase() == "anatidae" }
                    .also { assertEquals("Ducks, Geese, and Waterfowl", it.commonName) }
                    .species
                    .run {
                        assertEquals(174, size)
                        all { it.commonName.isNotBlank() }
                        all { it.scientificName.isNotBlank() }
                        all { it.url.startsWith(BASE_URL) }
                    }
            }
    }
}