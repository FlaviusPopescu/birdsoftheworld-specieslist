package dev.flavius.bow.data.model

data class Family(
    val commonName: String,
    val scientificName: String,
    val url: String,
    val species: List<Species>,
)
