plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "cards-scratch"

val modules: List<String> = listOf(
    "cards-domain",
    "cards-gameserver",
    "cards-networking"
)

include(modules)