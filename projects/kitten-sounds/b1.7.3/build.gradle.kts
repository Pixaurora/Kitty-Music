plugins {
    id("kit_tunes.java.08")
    id("kit_tunes.legacy_submodule")
}

mod {
    intermediaryMappings = "net.fabricmc:intermediary"
    accessWidener = "kitten_sounds.accesswidener"
    mixin("kitten_sounds.mixins.json")
}

loom {
    accessWidenerPath = file("src/main/resources/kitten_sounds.accesswidener")
}

dependencies {
    implementation(project(":projects:kit-tunes-api"))
    implementation(project(":projects:kitten-heart"))
}
