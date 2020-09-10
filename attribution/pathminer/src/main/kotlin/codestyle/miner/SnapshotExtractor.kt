package codestyle.miner

import astminer.cli.FileLabelExtractor
import astminer.common.model.Node
import java.io.File
import java.lang.IllegalArgumentException

fun detectProjectPath(args: Array<String>): String {
    val index = args.indexOf("--project")
    if (index == -1 || index == args.size - 1) {
        throw IllegalArgumentException("You should pass path to the project for parsing!")
    }
    return args[index + 1]
}

class FirstFolderExtractor(private val projectPath: String) : FileLabelExtractor() {
    private fun getFirstFolder(file: File): String = if (file.parentFile == null) {
        file.name.replace(" ", "_")
    } else {
        getFirstFolder(file.parentFile)
    }

    override fun extractLabel(root: Node, filePath: String): String? {
        return getFirstFolder(File(filePath.removePrefix(projectPath)))
    }
}
