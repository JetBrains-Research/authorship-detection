package codestyle.miner

import com.github.gumtreediff.tree.ITree
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

data class ChangeCode(val loadPath: String, val savePath: String, val startPos: Int, val endPos: Int)

class CodeStorage(private val loadPrefix: String, private val savePrefix: String) {

    init {
        File(savePrefix).mkdirs()
    }

    private fun blobIdToPath(blobId: BlobId?): String? {
        if (blobId == null) {
            return null
        }
        return "$loadPrefix/${blobId.id}"
    }

    private fun getLocalFolder(before: ITree?, after: ITree?) = when {
        before == null -> {
            "creations"
        }
        after == null -> {
            "deletions"
        }
        else -> {
            "modifications"
        }
    }

    private fun loadMethod(filePath: String, startPos: Int, endPos: Int) =
            String(Files.readAllBytes(Paths.get(filePath))).substring(startPos, endPos)

    private fun getSaveFilename(changeEntry: ChangeEntry, suffix: String) =
            "${changeEntry.id}_\$TEMPLATE_ID\$.$suffix.java"

    private val codeChanges = IncrementalIdStorage<ChangeCode>()

    @Synchronized
    fun store(before: ITree?, after: ITree?, changeEntry: ChangeEntry) {
        val localFolder = getLocalFolder(before, after)
        val pathBefore = blobIdToPath(changeEntry.oldContentId)
        val pathAfter = blobIdToPath(changeEntry.newContentId)
        if (before != null && pathBefore != null) {
            codeChanges.record(ChangeCode(
                    pathBefore, "$savePrefix/$localFolder/${getSaveFilename(changeEntry, "before")}",
                    before.pos, before.endPos
            ))
        }
        if (after != null && pathAfter != null) {
            codeChanges.record(ChangeCode(
                    pathAfter, "$savePrefix/$localFolder/${getSaveFilename(changeEntry, "after")}",
                    after.pos, after.endPos
            ))
        }
    }

    fun dump() {
        codeChanges.valueMap.forEach { change, id ->
            try {
                val methodText = loadMethod(change.loadPath, change.startPos, change.endPos)
                val saveFile = File(change.savePath.replace("\$TEMPLATE_ID\$", "$id"))
                saveFile.parentFile.mkdirs()
                saveFile.writeText(methodText)
            } catch (e: Exception) {
                println("${change.savePath.replace("\$TEMPLATE_ID\$", "$id")}, ${change.startPos}, ${change.endPos}")
                println(e.message)
            }
        }
    }
}
