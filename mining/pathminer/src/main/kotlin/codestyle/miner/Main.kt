package codestyle.miner

import com.github.gumtreediff.client.Run
import com.github.gumtreediff.tree.ITree
import com.github.gumtreediff.tree.TreeContext
import com.google.common.io.Files
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileWriter
import kotlin.concurrent.thread

fun readRepoNames(): List<String> {
    return File("../pythonminer/projects.txt").readLines().map { it.trim() }
}

fun main(args: Array<String>) {
    val repoNames = readRepoNames()
    repoNames.forEach {
        println("Processing repository $it")
        processRepositoryData(it)
    }
}

class CsvSettings(csvHeader: String) {
    private val keyPositions: MutableMap<String, Int> = HashMap()

    init {
        csvHeader.split(',').forEachIndexed { index, key ->
            keyPositions[key] = index
        }
    }

    fun getKeyIndex(key: String): Int {
        return keyPositions[key] ?: -1
    }
}

private fun nullIfEmpty(s: String) = if (s.isEmpty()) null else s

private fun createBlobId(idString: String): BlobId? {
    if (idString.isEmpty()) return null
    return BlobId(idString)
}

private fun parseChangeEntry(id: Int, csvLine: String, csvSettings: CsvSettings): ChangeEntry {
    val values = csvLine.split(',')

    return ChangeEntry(
            id,
            values[csvSettings.getKeyIndex("commit_id")],
            values[csvSettings.getKeyIndex("author_name")],
            values[csvSettings.getKeyIndex("author_email")],
            values[csvSettings.getKeyIndex("committer_name")],
            values[csvSettings.getKeyIndex("committer_email")],
            values[csvSettings.getKeyIndex("author_time")].toLong(),
            values[csvSettings.getKeyIndex("committer_time")].toLong(),
            values[csvSettings.getKeyIndex("change_type")].first(),
            createBlobId(values[csvSettings.getKeyIndex("old_content")]),
            createBlobId(values[csvSettings.getKeyIndex("new_content")]),
            nullIfEmpty(values[csvSettings.getKeyIndex("old_path")]),
            nullIfEmpty(values[csvSettings.getKeyIndex("new_path")])
    )
}


fun processEntries(entries: List<ChangeEntry>, pathStorage: PathStorage, methodMatcher: MethodMatcher): MutableList<FileChangeInfo> {
    val nCores = Runtime.getRuntime().availableProcessors()
    val nchunks = nCores - 1
    val chunkSize = (entries.size / nchunks) + 1
    println("have $nCores cores, running $nchunks threads processing $chunkSize entries each")

    val threads: MutableCollection<Thread> = HashSet()
    val infos: MutableList<FileChangeInfo> = ArrayList()

    entries.chunked(chunkSize).forEachIndexed { threadNumber, chunk ->
        val currentThread = thread {
            var processed = 0
            chunk.forEach {
                val info = processChangeEntry(it, pathStorage, methodMatcher)
                processed += 1
                if (processed % 100 == 0) {
                    println("Thread $threadNumber: processed $processed of ${chunk.size} entries")
                }
                synchronized(pathStorage) {
                    infos.add(info)
                }
            }
            println("Thread $threadNumber done")
        }
        threads.add(currentThread)
    }

    threads.forEach {
        it.join()
    }

    return infos
}

fun processRepositoryData(repoName: String) {
    val blobListFile = "../pythonminer/data/exploded/$repoName/infos_full.csv"
    val lines = Files.readLines(File(blobListFile), Charsets.UTF_8)
    val settings = CsvSettings(lines.first())
    println("${lines.size} entries read")

    Run.initGenerators()

    val startTime = System.currentTimeMillis()

    val pathStorage = PathStorage()

    var counter = 0
    fun getId(): Int = counter++
    val entries = lines.drop(1)
            .map { parseChangeEntry(getId(), it, settings) }

    val methodMatcher = MethodMatcher(repoName)

    val infos = processEntries(entries, pathStorage, methodMatcher)

    val dumper = DataDumper(repoName)

    dumper.dumpData(entries, infos, pathStorage)

    val elapsed = System.currentTimeMillis() - startTime
    println("Processed ${entries.size} entries in ${elapsed / 1000} seconds (${1000.0 * entries.size / elapsed} entries/s)")
}

fun MethodMatcher.getMappingContext(entry: ChangeEntry): MappingContext {
    return getMappingContext(entry.oldContentId, entry.newContentId)
}

fun PathContext.toShortString(): String = "${this.startToken} ${this.pathId} ${this.endToken}"

fun processChangeEntry(entry: ChangeEntry, pathStorage: PathStorage, methodMatcher: MethodMatcher): FileChangeInfo {
    // retrieve the method mappings between the two versions of the file
    val mappingContext = methodMatcher.getMappingContext(entry)

    // extract the changed methods
    val changedMappings = mappingContext.mappings.filter { it.isChanged }

    fun getMethodPaths(node: ITree?, context: TreeContext?): Collection<PathContext> {
        if (node == null || context == null) return emptyList()
        return retrievePaths(context, node, pathStorage, 10, 3)
    }

    val methodChangeInfos: MutableList<MethodChangeInfo> = ArrayList()

    changedMappings.forEach {
        val treeBefore = it.before?.node
        val treeAfter = it.after?.node

        val pathsBefore = getMethodPaths(treeBefore, mappingContext.treeContextBefore)
        val pathsAfter = getMethodPaths(treeAfter, mappingContext.treeContextAfter)
        val methodChangeData = MethodChangeInfo(it.before?.id, it.after?.id,
                pathsBefore.size,
                pathsAfter.size,
                pathsBefore.map { path -> path.toShortString() }.joinToString(separator = ";"),
                pathsAfter.map { path -> path.toShortString() }.joinToString(separator = ";"))
        methodChangeInfos.add(methodChangeData)
    }

    return FileChangeInfo(entry.id, entry.authorName, entry.authorEmail, methodChangeInfos)
}

fun saveInfosToJson(filename: String, infos: List<FileChangeInfo>) {
    GsonBuilder().setPrettyPrinting().create().toJson(infos, FileWriter(filename))
}
