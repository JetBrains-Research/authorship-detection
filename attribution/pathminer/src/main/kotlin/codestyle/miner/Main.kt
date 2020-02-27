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
    return File("../projects.txt").readLines().map { it.trim() }
}

enum class Mode {
    ExtractCode,
    ExtractContexts
}

fun main(args: Array<String>) {
    if (args.size != 1) {
        throw IllegalArgumentException("Specify exactly one argument: type of extracted data `contexts` or `code`")
    }
    val mode = when (args[0]) {
        "contexts" -> Mode.ExtractContexts
        "code" -> Mode.ExtractCode
        else -> throw IllegalArgumentException("Mode should be either `contexts` or `code`, not ${args[0]}")
    }
    val repoNames = readRepoNames()
    repoNames.forEach {
        println("Processing repository $it")
        processRepositoryData(it, mode)
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


fun <StorageType : Any, InfoType> processEntries(
        entries: List<ChangeEntry>, storage: StorageType, methodMatcher: MethodMatcher,
        processEntry: (ChangeEntry, StorageType, MethodMatcher) -> InfoType
): MutableList<InfoType> {

    val nCores = Runtime.getRuntime().availableProcessors()
    val nchunks = nCores - 1
    val chunkSize = (entries.size / nchunks) + 1
    println("have $nCores cores, running $nchunks threads processing $chunkSize entries each")

    val threads: MutableCollection<Thread> = HashSet()
    val infos: MutableList<InfoType> = ArrayList()

    entries.chunked(chunkSize).forEachIndexed { threadNumber, chunk ->
        val currentThread = thread {
            var processed = 0
            chunk.forEach {
                val info = processEntry(it, storage, methodMatcher)
                processed += 1
                if (processed % 100 == 0) {
                    println("Thread $threadNumber: processed $processed of ${chunk.size} entries")
                }
                synchronized(storage) {
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

fun processRepositoryData(repoName: String, mode: Mode) {
    val blobListFile = "../gitminer/data/exploded/$repoName/infos_full.csv"
    val lines = Files.readLines(File(blobListFile), Charsets.UTF_8)
    val settings = CsvSettings(lines.first())
    println("${lines.size} entries read")

    Run.initGenerators()

    val startTime = System.currentTimeMillis()

    var counter = 0
    fun getId(): Int = counter++
    val entries = lines.drop(1)
            .map { parseChangeEntry(getId(), it, settings) }

    val methodMatcher = MethodMatcher(repoName)
    val dumper = DataDumper(repoName)
    when (mode) {
        Mode.ExtractContexts -> {
            val pathStorage = PathStorage()
            val infos = processEntries(entries, pathStorage, methodMatcher) { changeEntry, storage, matcher ->
                processChangeToContext(changeEntry, storage, matcher)
            }
            dumper.dumpData(entries, infos, pathStorage)
        }
        Mode.ExtractCode -> {
            val codeStorage = CodeStorage(
                    "../gitminer/data/exploded/$repoName/blobs",
                    "../gitminer/out/$repoName/out_code"
            )
            val infos = processEntries(entries, codeStorage, methodMatcher) { changeEntry, storage, matcher ->
                processChangeToCode(changeEntry, storage, matcher)
            }
            dumper.dumpData(entries, infos, codeStorage)
        }
    }

    val elapsed = System.currentTimeMillis() - startTime
    println("Processed ${entries.size} entries in ${elapsed / 1000} seconds (${1000.0 * entries.size / elapsed} entries/s)")
}

fun MethodMatcher.getMappingContext(entry: ChangeEntry): MappingContext {
    return getMappingContext(entry.oldContentId, entry.newContentId)
}

fun PathContext.toShortString(): String = "${this.startToken} ${this.pathId} ${this.endToken}"

fun processChangeToContext(entry: ChangeEntry, pathStorage: PathStorage, methodMatcher: MethodMatcher): FileChangeContextInfo {
    // retrieve the method mappings between the two versions of the file
    val mappingContext = methodMatcher.getMappingContext(entry)

    // extract the changed methods
    val changedMappings = mappingContext.mappings.filter { it.isChanged }

    fun getMethodPaths(node: ITree?, context: TreeContext?): Collection<PathContext> {
        if (node == null || context == null) return emptyList()
        return retrievePaths(context, node, pathStorage, 10, 3)
    }

    val methodChangeInfos: MutableList<MethodChangeContextInfo> = ArrayList()

    changedMappings.forEach {
        val treeBefore = it.before?.node
        val treeAfter = it.after?.node

        val pathsBefore = getMethodPaths(treeBefore, mappingContext.treeContextBefore)
        val pathsAfter = getMethodPaths(treeAfter, mappingContext.treeContextAfter)
        val methodChangeData = MethodChangeContextInfo(it.before?.id, it.after?.id,
                pathsBefore.size,
                pathsAfter.size,
                pathsBefore.map { path -> path.toShortString() }.joinToString(separator = ";"),
                pathsAfter.map { path -> path.toShortString() }.joinToString(separator = ";"))
        methodChangeInfos.add(methodChangeData)
    }

    return FileChangeContextInfo(entry.id, entry.authorName, entry.authorEmail, methodChangeInfos)
}

fun processChangeToCode(entry: ChangeEntry, codeStorage: CodeStorage, methodMatcher: MethodMatcher): FileChangeCodeInfo {
    // retrieve the method mappings between the two versions of the file
    val mappingContext = methodMatcher.getMappingContext(entry)

    // extract the changed methods
    val changedMappings = mappingContext.mappings.filter { it.isChanged }

    val methodChangeInfos: MutableList<MethodChangeCodeInfo> = ArrayList()

    changedMappings.forEach {
        val treeBefore = it.before?.node
        val treeAfter = it.after?.node
        val methodChangeData = MethodChangeCodeInfo(it.before?.id, it.after?.id)
        codeStorage.store(treeBefore, treeAfter, entry)
        methodChangeInfos.add(methodChangeData)
    }

    return FileChangeCodeInfo(entry.id, entry.authorName, entry.authorEmail, methodChangeInfos)
}

fun saveInfosToJson(filename: String, infos: List<FileChangeContextInfo>) {
    GsonBuilder().setPrettyPrinting().create().toJson(infos, FileWriter(filename))
}
