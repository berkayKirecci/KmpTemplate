import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal val Project.versionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun VersionCatalog.version(alias: String) =
    findVersion(alias).get().toString().toInt()

internal fun applyPlugin(target: Project, block: Project.(VersionCatalog) -> Unit) =
    target.block(target.versionCatalog)
