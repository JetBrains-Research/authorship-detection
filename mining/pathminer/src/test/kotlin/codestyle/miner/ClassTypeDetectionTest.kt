package codestyle.miner

import org.junit.Assert
import org.junit.Test

class ClassTypeDetectionTest {
    @Test
    fun testNormalClass() {
        val file = "testData/differ/classType/1/Class.java"

        val context = parse(file)
        val methodInfos = getMethodInfos(context)
        Assert.assertEquals(ClassType.TOP_LEVEL, methodInfos.first().id.enclosingClassType)
    }

    @Test
    fun testAnonymousClass() {
        val file = "testData/differ/classType/2/Class.java"

        val context = parse(file)
        val methodInfos = getMethodInfos(context)
        Assert.assertEquals(1, methodInfos.size)
        Assert.assertEquals(ClassType.ANONYMOUS, methodInfos.first().id.enclosingClassType)
    }

    @Test
    fun testInnerClass() {
        val file = "testData/differ/classType/3/Class.java"

        val context = parse(file)
        val methodInfos = getMethodInfos(context)
        Assert.assertEquals(1, methodInfos.size)
        Assert.assertEquals(ClassType.INNER, methodInfos.first().id.enclosingClassType)
    }


    @Test
    fun testStaticNestedClass() {
        val file = "testData/differ/classType/4/Class.java"

        val context = parse(file)
        val methodInfos = getMethodInfos(context)
        Assert.assertEquals(1, methodInfos.size)
        Assert.assertEquals(ClassType.STATIC_NESTED, methodInfos.first().id.enclosingClassType)
    }

    @Test
    fun testLocalClass() {
        val file = "testData/differ/classType/5/Class.java"

        val context = parse(file)
        val methodInfos = getMethodInfos(context)
        Assert.assertEquals(2, methodInfos.size)
        Assert.assertEquals(ClassType.TOP_LEVEL, methodInfos.first { it.id.methodName == "methodWithLocalClass" }.id.enclosingClassType)
        Assert.assertEquals(ClassType.LOCAL, methodInfos.first { it.id.methodName == "foo" }.id.enclosingClassType)
    }


    @Test
    fun testAllTypes() {
        val file = "testData/differ/classType/6/Class.java"

        val context = parse(file)
        val methodInfos = getMethodInfos(context)
        Assert.assertEquals(5, methodInfos.size)
        Assert.assertEquals(ClassType.TOP_LEVEL, methodInfos.first { it.id.methodName == "inOuterClass" }.id.enclosingClassType)
        Assert.assertEquals(ClassType.INNER, methodInfos.first { it.id.methodName == "inInnerClass" }.id.enclosingClassType)
        Assert.assertEquals(ClassType.STATIC_NESTED, methodInfos.first { it.id.methodName == "inNestedClass" }.id.enclosingClassType)
        Assert.assertEquals(ClassType.LOCAL, methodInfos.first { it.id.methodName == "inLocalClass" }.id.enclosingClassType)
        Assert.assertEquals(ClassType.ANONYMOUS, methodInfos.first { it.id.methodName == "inAnonymousClass" }.id.enclosingClassType)
    }


}