package codestyle.miner

import com.github.gumtreediff.tree.ITree
import com.github.gumtreediff.tree.TreeContext

const val LEAF_INDEX_KEY = "leafIndex"
const val PATH_PIECES_KEY = "pathPieces"

fun ITree.setIntValue(key: String, value: Int) {
    this.setMetadata(key, value)
}

fun ITree.getIntValue(key: String): Int {
    return this.getMetadata(key) as Int
}

fun ITree.setPathPieces(pathPieces: Collection<List<ITree>>) {
    this.setMetadata(PATH_PIECES_KEY, pathPieces)
}

fun ITree.getPathPieces(): Collection<MutableList<ITree>> = this.getMetadata(PATH_PIECES_KEY) as Collection<MutableList<ITree>>

fun ITree.setLeafIndex(value: Int) = setIntValue(LEAF_INDEX_KEY, value)

fun ITree.getMinLeafIndex() = getIntValue(LEAF_INDEX_KEY)


fun getPathsForCurrentNode(pathPieces: Collection<PathPiece>,
                           maxLength: Int, maxWidth: Int,
                           treeContext: TreeContext,
                           pathStorage: PathStorage): Collection<PathContext> {
    val paths: MutableCollection<PathContext> = ArrayList()
    val sortedPieces = pathPieces.sortedBy { (it.nodes[0].getMinLeafIndex()) }
    sortedPieces.forEachIndexed { index, upPiece ->
        for (i in (index + 1 until sortedPieces.size)) {

            val downPiece = sortedPieces[i]
            if (upPiece.childIndex == downPiece.childIndex) continue

            val length = upPiece.nodes.size + downPiece.nodes.size - 1 // -1 as the top node is present in both pieces
            val width = downPiece.nodes[0].getMinLeafIndex() - upPiece.nodes[0].getMinLeafIndex()
            if (length <= maxLength && width <= maxWidth) {
                paths.add(pathStorage.store(upPiece.nodes, downPiece.nodes, treeContext))
            }
        }
    }
    return paths
}

fun retrievePaths(treeContext: TreeContext, startNode: ITree, pathStorage: PathStorage) = retrievePaths(treeContext, startNode, pathStorage, Int.MAX_VALUE, Int.MAX_VALUE)

data class PathPiece(val childIndex: Int, val nodes: List<ITree>)

fun retrievePaths(treeContext: TreeContext, startNode: ITree, pathStorage: PathStorage, maxLength: Int, maxWidth: Int): Collection<PathContext> {
    val iterator = startNode.postOrder()
    var currentLeafIndex = 0
    val paths: MutableCollection<PathContext> = ArrayList()
    iterator.forEach { currentNode ->
        if (currentNode.isLeaf) {
            val leafIndex = currentLeafIndex++
            currentNode.setLeafIndex(leafIndex)
            currentNode.setPathPieces(arrayListOf(arrayListOf(currentNode)))
        } else {

            val pathPiecesPerChild = currentNode.children
                    .map { it.getPathPieces() }

            val pathPieces: MutableList<PathPiece> = ArrayList()

            pathPiecesPerChild.forEachIndexed { childIndex, childPieces ->
                childPieces.forEach {
                    // -2 represent the current node and its possible immediate leaf child
                    if (it.size <= maxLength - 2) {
                        pathPieces.add(PathPiece(childIndex, it + currentNode))
                    }
                }
            }

            val currentNodePaths = getPathsForCurrentNode(pathPieces, maxLength, maxWidth, treeContext, pathStorage)
            paths.addAll(currentNodePaths)

            currentNode.setPathPieces(pathPieces.map { it.nodes })
        }
    }
    return paths
}