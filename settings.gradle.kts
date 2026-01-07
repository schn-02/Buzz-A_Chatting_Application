pluginManagement {
    repositories {
        google {
            // Restrict the Google repository to certain groups only
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()  // Add Maven Central for general dependencies
        gradlePluginPortal()  // Add Gradle Plugin Portal for any Gradle plugins
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)  // Fail if any module declares its own repositories
    repositories {
        google()  // Google's repository for Android dependencies
        mavenCentral()  // Maven Central for other dependencies
    }
}

rootProject.name = "Buzz!"  // Name of the root project
include(":app")  // Include the app module
