import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.applicationId: String
    get() = providers.gradleProperty("kmptemplate.applicationId").get()

internal val Project.versionCatalog: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun VersionCatalog.version(alias: String): Int =
    findVersion(alias).get().requiredVersion.toInt()

internal fun VersionCatalog.library(alias: String) =
    findLibrary(alias).get()

internal fun convention(target: Project, block: Project.(VersionCatalog) -> Unit) =
    target.block(target.versionCatalog)
