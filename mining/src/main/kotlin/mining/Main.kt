package mining

import mining.joern.parseJoernAst
import astminer.paths.toPathContext
import astminer.paths.PathMiner
import astminer.paths.PathRetrievalSettings
import astminer.paths.VocabularyPathStorage
import java.io.File

fun main(args: Array<String>) {
    val folder = "../parsed/datasets/cppSample/"

    val miner = PathMiner(PathRetrievalSettings(8, 3))
    val storage = VocabularyPathStorage()

    File(folder).walkTopDown().filter { it.path.endsWith(".cpp") && it.isDirectory }.forEach { directory ->
        println(directory.path)
        val nodesFile = File(directory, "nodes.csv")
        val edgesFile = File(directory, "edges.csv")

        val node = parseJoernAst(nodesFile, edgesFile) ?: return@forEach
        val paths = miner.retrievePaths(node)

        storage.store(paths.map { toPathContext(it) }, entityId = directory.path)
    }

    storage.save("../processed/cppSample/")
}
