pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        includeBuild("plugin")
    }
}

include(":app")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "LiveSportTask"

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
