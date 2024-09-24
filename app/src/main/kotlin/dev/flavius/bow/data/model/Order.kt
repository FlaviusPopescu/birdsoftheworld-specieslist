package dev.flavius.bow.data.model

data class Order(
    val scientificName: String,
    val families: List<Family>,
)
