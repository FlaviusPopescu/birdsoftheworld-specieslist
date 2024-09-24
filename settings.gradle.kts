@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
}

rootProject.name = "bow-specieslist"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include("app")
