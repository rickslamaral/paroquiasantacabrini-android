@file:Suppress("UnstableApiUsage")

pluginManagement {
  includeBuild("build-logic")
  repositories {
    google {
      content {
        includeGroupByRegex("com\\.android.*")
        includeGroupByRegex("com\\.google.*")
        includeGroupByRegex("androidx.*")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}
plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "Paroquia Santa Cabrini"
include(":app")
include(":library-kotlin")
include(":library-android")

// region tests
include(":tests:e2e")
// endregion

// region core
include(":core:navigation")
include(":core:designsystem")
include(":core:testing")
// endregion

// region feature
include(":core:database")
include(":feature:home")
include(":feature:posts:domain")
include(":feature:posts:data")
include(":feature:posts:presentation")
include(":feature:pix")
include(":feature:massschedule")
include(":feature:liturgy")
include(":feature:saint")
include(":feature:prayer")
// endregion
