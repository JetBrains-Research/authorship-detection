package codestyle.miner

import com.github.gumtreediff.tree.ITree
import org.junit.Assert
import org.junit.Test


fun ITree.getLeavesCount(): Int = this.preOrder().count { it.isLeaf }

class PathExtractionTest {
    @Test
    fun testPathsCount1() {
        val file = "testData/paths/1.java"
        val pathStorage = PathStorage()

        val context = parse(file)
        val leavesCount = context.root.getLeavesCount()

        val paths = retrievePaths(context, context.root, pathStorage)
        Assert.assertEquals((leavesCount * (leavesCount - 1) / 2), paths.size)
    }

    @Test
    fun testPathsCount2() {
        val file = "testData/paths/1.java"
        val pathStorage = PathStorage()

        val context = parse(file)
        val leavesCount = context.root.getLeavesCount()

        val paths = retrievePaths(context, context.root, pathStorage, Int.MAX_VALUE, 1)
        Assert.assertEquals(leavesCount - 1, paths.size)
    }

    @Test
    fun testPathsCount3() {
        val file = "testData/paths/1.java"
        val pathStorage = PathStorage()

        val context = parse(file)
        val leavesCount = context.root.getLeavesCount()

        val paths = retrievePaths(context, context.root, pathStorage, Int.MAX_VALUE, 2)
        Assert.assertEquals((leavesCount - 2) * 2 + 1, paths.size)
    }

    @Test
    fun testPathsCount4() {
        val file = "testData/paths/1.java"
        val pathStorage = PathStorage()

        val context = parse(file)
        val leavesCount = context.root.getLeavesCount()

        val paths = retrievePaths(context, context.root, pathStorage, Int.MAX_VALUE, 3)
        Assert.assertEquals((leavesCount - 3) * 3 + 2 + 1, paths.size)
    }

    @Test
    fun testManyFiles() {
        val files = listOf(
                "testData/paths/1.java",
                "testData/paths/2.java",
                "testData/paths/3.java"
        )
        files.forEach { file ->
            val pathStorage = PathStorage()

            val context = parse(file)
            val leavesCount = context.root.getLeavesCount()

            val allPaths = retrievePaths(context, context.root, pathStorage)
            Assert.assertEquals((leavesCount * (leavesCount - 1) / 2), allPaths.size)

            val width1 = retrievePaths(context, context.root, pathStorage, Int.MAX_VALUE, 1)
            Assert.assertEquals(leavesCount - 1, width1.size)

            val width2 = retrievePaths(context, context.root, pathStorage, Int.MAX_VALUE, 2)
            Assert.assertEquals((leavesCount - 2) * 2 + 1, width2.size)

            val width3 = retrievePaths(context, context.root, pathStorage, Int.MAX_VALUE, 3)
            Assert.assertEquals((leavesCount - 3) * 3 + 2 + 1, width3.size)
        }
    }

}