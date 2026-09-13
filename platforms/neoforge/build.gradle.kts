import java.util.*

plugins {
    id("dev.architectury.loom") version Versions.Mod.architecuryLoom
    id("architectury-plugin") version Versions.Mod.architecturyPlugin
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

dependencies {
    shadedApi(project(":common:implementation:base"))
    "forgeRuntimeLibrary"(project(":common:implementation:base"))

    implementation(project(path = ":platforms:mixin-common", configuration = "namedElements")) { isTransitive = false }
    "developmentNeoForge"(project(path = ":platforms:mixin-common", configuration = "namedElements")) { isTransitive = false }
    shaded(project(path = ":platforms:mixin-common", configuration = "transformProductionNeoForge")) { isTransitive = false }
    implementation(project(path = ":platforms:mixin-lifecycle", configuration = "namedElements")) { isTransitive = false }
    "developmentNeoForge"(project(path = ":platforms:mixin-lifecycle", configuration = "namedElements")) { isTransitive = false }
    shaded(project(path = ":platforms:mixin-lifecycle", configuration = "transformProductionNeoForge")) { isTransitive = false }

    minecraft("com.mojang", "minecraft", Versions.Mod.minecraft)
    mappings(
        loom.layered {
                mappings("net.fabricmc:yarn:${Versions.Mod.yarn}:v2")
                mappings("dev.architectury:yarn-mappings-patch-neoforge:${Versions.NeoForge.yarnPatch}")
            }
    )

    neoForge("net.neoforged", "neoforge", Versions.NeoForge.neoForge)

    modImplementation("org.incendo", "cloud-neoforge", Versions.NeoForge.cloud)
    include("org.incendo", "cloud-neoforge", Versions.NeoForge.cloud)
    // NeoForge does not recursively discover JarJar dependencies inside another
    // JarJar dependency, so expose Cloud's runtime libraries at Terra's top level.
    include("org.incendo", "cloud-minecraft-modded-common", Versions.NeoForge.cloud)
    include("org.incendo", "cloud-brigadier", Versions.NeoForge.cloud)
    include("org.incendo", "cloud-core", "2.0.0")
    include("org.incendo", "cloud-services", "2.0.0")
    include("io.leangen.geantyref", "geantyref", "1.3.15")

}

loom {
    accessWidenerPath.set(project(":platforms:mixin-common").file("src/main/resources/terra.accesswidener"))

//    mixin {
//        defaultRefmapName.set("terra.neoforge.refmap.json")
//    }
}


addonDir(project.file("./run/config/Terra/addons"), tasks.named("configureLaunch").get())

tasks {
    jar {
        manifest {
            attributes(
                mapOf(
                    "Implementation-Title" to rootProject.name,
                    "Implementation-Version" to project.version,
                )
            )
        }
    }

    shadowJar {
        // Checker Framework annotations are compile-time metadata. Bundling them
        // creates a split Java module package with NeoForge's runtime copy.
        exclude("org/checkerframework/**")
    }

    remapJar {
        dependsOn("installAddons")

        injectAccessWidener.set(true)
        inputFile.set(shadowJar.get().archiveFile)
        archiveFileName.set("${rootProject.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}-neoforge-${project.version}.jar")
    }
}
