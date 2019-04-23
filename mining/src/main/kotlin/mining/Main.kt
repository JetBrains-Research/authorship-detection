package mining

import astminer.parse.antlr.java.JavaParser
import astminer.paths.PathMiner
import astminer.paths.PathRetrievalSettings
import astminer.paths.VocabularyPathStorage
import astminer.paths.toPathContext
import mining.joern.parseJoernAst
import mining.python.parseFile
import java.io.File


fun parsePy() {
    val folder = "../datasets/gcjpy/"

    val miner = PathMiner(PathRetrievalSettings(8, 3))
    val storage = VocabularyPathStorage()

    File(folder).walkTopDown().filter { it.path.endsWith(".py") }.forEach { file ->
        try {
            val node = parseFile(file.path) ?: return@forEach
            val paths = miner.retrievePaths(node)

            storage.store(paths.map { toPathContext(it) }, entityId = file.path)
        } catch (e: IllegalStateException) {
            println(e.message)
            println("Oops, unable to parse ${file.name}")
        }
    }

    storage.save("../processed/gcjpy/")
}

fun parseJoern() {
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


fun parseJava() {
    val folder = "../datasets/java40/"

    val miner = PathMiner(PathRetrievalSettings(14, 5))
    val storage = VocabularyPathStorage()

    File(folder).walkTopDown().filter { it.path.endsWith(".java") }.forEach { file ->
        try {
            val node = JavaParser().parse(file.inputStream()) ?: return@forEach
            val paths = miner.retrievePaths(node)

            storage.store(paths.map { toPathContext(it) }, entityId = file.path)
        } catch (e: IllegalStateException) {
            println(e.message)
            println("Oops, unable to parse ${file.name}")
        }
    }

    storage.save("../processed/java40Large/")
}


fun main(args: Array<String>) {
    parseJava()
}
