package codestyle.miner

import com.github.gumtreediff.gen.jdt.JdtTreeGenerator
import com.github.gumtreediff.tree.ITree
import com.github.gumtreediff.tree.TreeContext
import java.io.File

class TooLongException(file: String) : Exception(file)

fun readAndParseBlob(blobId: BlobId?, repoName: String): TreeContext? {
    if (blobId == null) return null
    val file = "../gitminer/data/exploded/$repoName/blobs/${blobId.id}"
    val length = File(file).readLines().size
    if (length > 3000) {
        throw TooLongException(file)
    }
    return parse(file)
}

fun parse(file: String): TreeContext {
    return JdtTreeGenerator().generateFromFile(file)
}

fun getEnclosingClassName(methodNode: ITree, context: TreeContext): String {
    val classDeclarationNode = methodNode.parents.firstOrNull { context.getTypeLabel(it.type) == "TypeDeclaration" }
            ?: return ""
    val nameNode = classDeclarationNode.children.firstOrNull { context.getTypeLabel(it.type) == "SimpleName" }
    return nameNode?.label ?: ""
}

fun getEnclosingClassType(methodNode: ITree, context: TreeContext): ClassType {
    val parentNode = methodNode.parents.firstOrNull { context.getTypeLabel(it.type) in setOf("TypeDeclaration", "AnonymousClassDeclaration") }
            ?: return ClassType.TOP_LEVEL

    val parentNodeType = context.getTypeLabel(parentNode)
    if (parentNodeType == "AnonymousClassDeclaration") return ClassType.ANONYMOUS

    val grandParentNode = parentNode.parents.firstOrNull { context.getTypeLabel(it.type) in setOf("TypeDeclaration", "MethodDeclaration") }
            ?: return ClassType.TOP_LEVEL

    if (context.getTypeLabel(grandParentNode) == "MethodDeclaration") return ClassType.LOCAL
    parentNode.children.firstOrNull { context.getTypeLabel(it) == "Modifier" && it.label == "static" }
            ?: return ClassType.INNER

    return ClassType.STATIC_NESTED
}

fun getMethodName(methodNode: ITree, context: TreeContext): String {
    val nameNode = methodNode.children.firstOrNull { context.getTypeLabel(it.type) == "SimpleName" }
    return nameNode?.label ?: ""
}

fun getParameterTypes(methodNode: ITree, context: TreeContext): List<String> {
    val result: MutableList<String> = ArrayList()
    val argDeclarationNodes = methodNode.children
            .filter { context.getTypeLabel(it.type) == "SingleVariableDeclaration" }
    argDeclarationNodes.forEach {
        val typeNode = it.children.filter { c -> context.getTypeLabel(c.type).endsWith("Type") }.firstOrNull()
        if (typeNode != null) result.add(typeNode.label)
    }
    return result
}

fun getMethodId(methodNode: ITree, context: TreeContext): MethodId {
    return MethodId(
            getEnclosingClassName(methodNode, context),
            getEnclosingClassType(methodNode, context),
            getMethodName(methodNode, context),
            getParameterTypes(methodNode, context)
    )
}

fun getMethodInfo(methodNode: ITree, context: TreeContext): MethodInfo {
    return MethodInfo(methodNode, getMethodId(methodNode, context))
}

fun getMethodInfos(treeContext: TreeContext): Collection<MethodInfo> {
    val methodNodes = treeContext.root.descendants
            .filter { treeContext.getTypeLabel(it.type) == "MethodDeclaration" }
    return methodNodes.map { getMethodInfo(it, treeContext) }
}

