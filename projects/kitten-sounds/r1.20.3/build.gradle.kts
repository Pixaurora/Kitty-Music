plugins {
    id("kit_tunes.java.17")
    id("kit_tunes.submodule")
}

mod {
    intermediaryMappings = "net.fabricmc:intermediary"
    mixin("kitten_sounds.mixins.json")
}

dependencies {
    implementation(project(":projects:kit-tunes-api"))
    implementation(project(":projects:kitten-heart"))
}
