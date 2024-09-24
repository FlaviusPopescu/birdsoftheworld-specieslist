package dev.flavius.bow.data.web

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Element
import com.fleeksoft.ksoup.select.Elements
import dev.flavius.bow.data.BowSpeciesRepository.Constants.BASE_URL
import dev.flavius.bow.data.model.Family
import dev.flavius.bow.data.model.Order
import dev.flavius.bow.data.model.Species

class BowHtmlParser(
    private val ksoup: Ksoup = Ksoup,
) {
    operator fun invoke(html: String): List<Order> = ksoup
        .parse(html)
        .orders()
        .map { orderElement ->
            Order(
                scientificName = orderElement.orderName(),
                families = orderElement.getOrderFamilies(),
            )
        }

    private val orders: Element.() -> Elements = { select("ul.UnorderedList--flat > li") }
    private val orderName: Element.() -> String = { select("> h2").text() }

    private val subgroups: Element.() -> Elements = { select("> ul > li") }
    private val fullUrl: String.() -> String = { "$BASE_URL$this" }

    private val familyScientificName: Element.() -> String =
        { select("h3 span.Heading-main").text() }
    private val familyCommonName: Element.() -> String = { select("h3 span.Heading-sub").text() }
    private val familyUrl: Element.() -> String = { select("h3 > a").attr("href").fullUrl() }

    private val speciesScientificName: Element.() -> String = { select("span.Heading-sub").text() }
    private val speciesCommonName: Element.() -> String = { select("span.Heading-main").text() }
    private val speciesUrl: Element.() -> String = { select("a").attr("href").fullUrl() }

    private val getFamilySpecies: Element.() -> List<Species> = {
        subgroups().map { speciesElement ->
            Species(
                commonName = speciesElement.speciesCommonName(),
                scientificName = speciesElement.speciesScientificName(),
                url = speciesElement.speciesUrl(),
            )
        }
    }

    private val getOrderFamilies: Element.() -> List<Family> = {
        subgroups().map { familyElement ->
            Family(
                commonName = familyElement.familyCommonName(),
                scientificName = familyElement.familyScientificName(),
                url = familyElement.familyUrl(),
                species = familyElement.getFamilySpecies()
            )
        }
    }
}
