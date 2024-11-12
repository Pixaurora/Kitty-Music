import org.gradle.accessors.dm.LibrariesForLibs
import net.pixaurora.kit_tunes.build_logic.ProjectMetadata
import net.pixaurora.kit_tunes.build_logic.mod_resources_gen.extension.ModResourcesExtension

plugins {
    id("kit_tunes.submodule")
    id("ploceus")
}

val libs = the<LibrariesForLibs>()

loom {
    clientOnlyMinecraftJar()
}

ploceus {
    clientOnlyMappings()
}

val minecraft_version = project.property("minecraft_version")

dependencies {
    minecraft("com.mojang:minecraft:${minecraft_version}")
    mappings(ploceus.featherMappings(project.property("feather_build") as String))

    exceptions(ploceus.raven(project.property("raven_build") as String))
    signatures(ploceus.sparrow(project.property("sparrow_build") as String))
    nests(ploceus.nests(project.property("nests_build") as String))


    modImplementation(libs.quilt.loader)
}

