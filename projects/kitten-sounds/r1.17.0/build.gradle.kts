plugins {
    id("kit_tunes.java.16")
    id("kit_tunes.modern_submodule")
}

mod {
    intermediaryMappings = "net.fabricmc:intermediary"
    mixin("kitten_sounds.mixins.json")
}

dependencies {
    implementation(project(":projects:kit-tunes-api"))
    implementation(project(":projects:kitten-heart"))
}
