package mining.python

import astminer.common.Node

class PythonPipNode(private val typeLabel: String, private var token: String? = null) : Node {
    private val metadata: MutableMap<String, Any> = HashMap()
    private var parent: Node? = null
    private var children: MutableList<Node> = mutableListOf()

    internal fun addChild(node: PythonPipNode) {
        children.add(node)
        node.setParent(this)
    }

    internal fun putToken(addedToken: String) {
        if (token != null) {
            throw IllegalStateException("Trying to update token for the second time")
        }
        token = addedToken
    }

    override fun getTypeLabel(): String {
        return typeLabel
    }

    override fun getChildren(): List<Node> {
        return children
    }

    override fun getParent(): Node? {
        return parent
    }

    override fun getToken(): String {
        return token ?: "null"
    }

    override fun isLeaf(): Boolean {
        return children.isEmpty()
    }

    override fun getMetadata(key: String): Any? {
        return metadata[key]
    }

    override fun setMetadata(key: String, value: Any) {
        metadata[key] = value
    }

    private fun setParent(node: Node) {
        parent = node
    }
}