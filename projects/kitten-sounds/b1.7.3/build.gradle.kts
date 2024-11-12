plugins {
    id("kit_tunes.java.08")
    id("kit_tunes.legacy_submodule")
}

mod {
    intermediaryMappings = "net.fabricmc:intermediary"
    mixin("kitten_sounds.mixins.json")
}

dependencies {
    implementation(project(":projects:kit-tunes-api"))
    implementation(project(":projects:kitten-heart"))
}
