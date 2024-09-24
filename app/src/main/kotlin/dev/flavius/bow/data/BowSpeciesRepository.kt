package dev.flavius.bow.data

import dev.flavius.bow.data.BowSpeciesRepository.Constants.SPECIES_LIST_URL
import dev.flavius.bow.data.model.Order
import dev.flavius.bow.data.web.BowHtmlParser
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import java.util.zip.GZIPInputStream

/**
 * Fetch and parse the species list from the taxonomy webpage
 * at Birds of the World: https://birdsoftheworld.org/bow/specieslist
 */
class BowSpeciesRepository(
    private val bowHtmlParser: BowHtmlParser = BowHtmlParser(),
) {
    suspend fun getAllOrders(): List<Order> = bowHtmlParser(getSpeciesHtml())

    private val httpClient by lazy { HttpClient(OkHttp) }

    private suspend fun getSpeciesHtml(): String {
        return javaClass.getResourceAsStream("/specieslist.html")
            ?.use { it.bufferedReader().use { it.readText() } }
            ?: javaClass.getResourceAsStream("/specieslist.gz")
                ?.use { inputStream ->
                    GZIPInputStream(inputStream)
                        .bufferedReader()
                        .use { it.readText() }
                }
            ?: run {
                httpClient.get(SPECIES_LIST_URL).bodyAsText()
            }
    }

    object Constants {
        const val BASE_URL = "https://birdsoftheworld.org"
        const val SPECIES_LIST_URL = "$BASE_URL/bow/specieslist"
    }
}
