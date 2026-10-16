package app.filemg.samo1408.core.data

import app.filemg.samo1408.core.remote.RemoteUri
import java.io.File

object BackupLocations {

    const val MAX_BYTES = 1L shl 20

    fun exportFolder(active: String, downloads: File): File {
        val here = active.takeIf { it.isNotBlank() && !RemoteUri.isRemote(it) }?.let(::File)
        return if (here != null && here.isDirectory && here.canWrite()) here else File(downloads, "FileManager")
    }

    fun isBackup(name: String): Boolean = name.startsWith("filemanager-settings") && name.endsWith(".json", ignoreCase = true)

    fun find(folders: List<File>): List<File> =
        folders.distinctBy { runCatching { it.canonicalPath }.getOrDefault(it.absolutePath) }
            .flatMap { dir -> dir.listFiles { f -> f.isFile && isBackup(f.name) && f.length() in 1..MAX_BYTES }.orEmpty().toList() }
            .sortedByDescending { it.lastModified() }
}
