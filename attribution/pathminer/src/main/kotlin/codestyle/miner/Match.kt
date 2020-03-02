package codestyle.miner

import com.github.gumtreediff.matchers.Matchers
import com.github.gumtreediff.tree.TreeContext

data class MappingContext(val treeContextBefore: TreeContext?, val treeContextAfter: TreeContext?, val mappings: Collection<MethodMapping>)


class MethodMatcher(val repoName: String) {
    fun getMappingContext(treeBefore: TreeContext?, treeAfter: TreeContext?): MappingContext {
        if (treeBefore == null && treeAfter == null) {
            return MappingContext(null, null, emptyList())
        }

        if (treeBefore == null) {
            return MappingContext(null, treeAfter, getMethodInfos(treeAfter!!).map { MethodMapping(null, it) })
        }

        if (treeAfter == null) {
            return MappingContext(treeBefore, null, getMethodInfos(treeBefore).map { MethodMapping(it, null) })
        }

        val infosBefore = getMethodInfos(treeBefore)
        val infosAfter = getMethodInfos(treeAfter)

        val matcher = Matchers.getInstance().getMatcher(treeBefore.root, treeAfter.root)
        matcher.match()
        val gtMappings = matcher.mappings

        val mappings: MutableSet<MethodMapping> = HashSet()

        infosBefore.forEach {
            val dst = gtMappings.getDst(it.node)
            if (dst == null) {
                mappings.add(MethodMapping(it, null))
                return@forEach
            }
            val dstInfo = infosAfter.firstOrNull { info -> info.node == dst }
            if (dstInfo == null) {
                println("Method node $it mapped to unknown node $dst by GumTree")
                return@forEach
            }

            mappings.add(MethodMapping(it, dstInfo))
        }

        infosAfter.forEach {
            val src = gtMappings.getSrc(it.node)
            if (src == null) {
                mappings.add(MethodMapping(null, it))
            }
        }

        return MappingContext(treeBefore, treeAfter, mappings)
    }

    fun getMappingContext(filenameBefore: String, filenameAfter: String): MappingContext {
        return getMappingContext(parse(filenameBefore), parse(filenameAfter))
    }

    fun getMappingContext(blobIdBefore: BlobId?, blobIdAfter: BlobId?): MappingContext {
        return try {
            getMappingContext(readAndParseBlob(blobIdBefore, repoName), readAndParseBlob(blobIdAfter, repoName))
        } catch (e: TooLongException) {
            println("Skipping ${e.message}")
            getMappingContext(blobIdBefore = null, blobIdAfter = null)
        }
    }
}