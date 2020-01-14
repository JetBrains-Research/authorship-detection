package codestyle.miner

import org.junit.Assert
import org.junit.Test

class IdExtractionTest {

    @Test
    fun testIdExtraction1() {
        val tree = parse("testData/idExtraction/1.java")

        val methodInfos = getMethodInfos(tree)

        Assert.assertEquals(1, methodInfos.size)
        Assert.assertEquals(MethodId("SingleFunction", ClassType.TOP_LEVEL, "fun", listOf("String[]", "int")), methodInfos.first().id)
    }

    @Test
    fun testIdExtraction2() {
        val tree = parse("testData/idExtraction/2.java")

        val methodInfos = getMethodInfos(tree)

        Assert.assertEquals(1, methodInfos.size)
        Assert.assertEquals(MethodId("InnerClass", ClassType.INNER, "main", listOf("String[]")), methodInfos.first().id)
    }

    @Test
    fun testIdExtraction3() {
        val tree = parse("testData/idExtraction/3.java")

        val methodInfos = getMethodInfos(tree)

        Assert.assertEquals(2, methodInfos.size)
        Assert.assertTrue(methodInfos.map { it.id }.contains(MethodId("InnerClass", ClassType.INNER, "main", listOf("String[]"))))
        Assert.assertTrue(methodInfos.map { it.id }.contains(MethodId("SingleMethodInnerClass", ClassType.TOP_LEVEL, "fun", listOf("String[]", "int"))))
    }

    @Test
    fun testIdExtraction4() {
        val tree = parse("testData/idExtraction/4.java")

        val methodInfos = getMethodInfos(tree)

        Assert.assertEquals(1, methodInfos.size)
        Assert.assertEquals(MethodId("SingleFunction", ClassType.TOP_LEVEL, "fun", listOf("int", "int")), methodInfos.first().id)
    }
}