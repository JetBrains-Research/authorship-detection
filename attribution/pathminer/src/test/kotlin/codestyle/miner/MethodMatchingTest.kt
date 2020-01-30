package codestyle.miner

import org.junit.Assert
import org.junit.Test

class MethodMatchingTest {
    val matcher = MethodMatcher("dummy")

    @Test
    fun testMatching1() {
        val fileBefore = "testData/differ/matching/1/Before.java"
        val fileAfter = "testData/differ/matching/1/After.java"

        val context = matcher.getMappingContext(fileBefore, fileAfter)

        Assert.assertEquals(1, context.mappings.size)
        Assert.assertNotNull(context.mappings.first().before)
        Assert.assertNotNull(context.mappings.first().after)
        Assert.assertEquals(context.mappings.first().before!!.id, MethodId("SingleFunction", ClassType.TOP_LEVEL, "main", listOf("String[]")))
        Assert.assertEquals(context.mappings.first().after!!.id, MethodId("SingleFunction", ClassType.TOP_LEVEL, "main", listOf("String[]")))
    }

    @Test
    fun testMatching2() {
        val fileBefore = "testData/differ/matching/2/Before.java"
        val fileAfter = "testData/differ/matching/2/After.java"

        val context = matcher.getMappingContext(fileBefore, fileAfter)

        Assert.assertEquals(1, context.mappings.size)

        Assert.assertEquals(true, context.mappings.first().isChanged)

        Assert.assertNotNull(context.mappings.first().before)
        Assert.assertNotNull(context.mappings.first().after)
        Assert.assertEquals(context.mappings.first().before!!.id, MethodId("SingleFunction", ClassType.TOP_LEVEL, "fun", listOf("String[]", "int")))
        Assert.assertEquals(context.mappings.first().after!!.id, MethodId("SingleFunction", ClassType.TOP_LEVEL, "main", listOf("String[]")))
    }


    //New method addition
    @Test
    fun testMatching3() {
        val fileBefore = "testData/differ/matching/3/Before.java"
        val fileAfter = "testData/differ/matching/3/After.java"

        val context = matcher.getMappingContext(fileBefore, fileAfter)

        Assert.assertEquals(2, context.mappings.size)

        val idPairs = context.mappings.map { Pair(it.before?.id, it.after?.id) }

        Assert.assertTrue(idPairs.contains(Pair(
                MethodId("SingleFunction", ClassType.TOP_LEVEL, "fun1", listOf("String[]", "int")),
                MethodId("TwoFunctions", ClassType.TOP_LEVEL, "fun1", listOf("String[]", "int"))
        )))

        Assert.assertTrue(idPairs.contains(Pair(
                null,
                MethodId("TwoFunctions", ClassType.TOP_LEVEL, "fun2", listOf("int"))
        )))

    }

    //Existing method removal
    @Test
    fun testMatching4() {
        val fileBefore = "testData/differ/matching/4/Before.java"
        val fileAfter = "testData/differ/matching/4/After.java"

        val context = matcher.getMappingContext(fileBefore, fileAfter)

        Assert.assertEquals(2, context.mappings.size)

        val idPairs = context.mappings.map { Pair(it.before?.id, it.after?.id) }

        Assert.assertTrue(idPairs.contains(Pair(
                MethodId("TwoFunctions", ClassType.TOP_LEVEL, "fun1", listOf("String[]", "int")),
                MethodId("SingleFunction", ClassType.TOP_LEVEL, "fun1", listOf("String[]", "int"))
        )))

        Assert.assertTrue(idPairs.contains(Pair(
                MethodId("TwoFunctions", ClassType.TOP_LEVEL, "fun2", listOf("int")),
                null
        )))
    }

    //Equal nodes
    @Test
    fun testMatching5() {
        val fileBefore = "testData/differ/matching/5/Before.java"
        val fileAfter = "testData/differ/matching/5/After.java"

        val context = matcher.getMappingContext(fileBefore, fileAfter)

        Assert.assertEquals(1, context.mappings.size)

        Assert.assertEquals(false, context.mappings.first().isChanged)
    }
}