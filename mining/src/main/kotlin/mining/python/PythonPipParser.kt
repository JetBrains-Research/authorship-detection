package mining.python

import java.io.File
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

fun String.runCommand(workingDir: File): Boolean {
    try {
        val parts = this.split("\\s".toRegex())
        val proc = ProcessBuilder(*parts.toTypedArray())
                .directory(workingDir)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start()

        proc.waitFor(60, TimeUnit.MINUTES)
        return proc.inputStream.bufferedReader().readText().isEmpty()
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    }
}

fun parseFile(filename: String): PythonPipNode? {
    val tmpFile = "tmp_tree.yml"
    for (version in 2..3) {
        val parseCommand = "python$version process_file.py $filename $tmpFile"
        if (parseCommand.runCommand(File(System.getProperty("user.dir")))) {
            return parsePipAst(File(tmpFile))
        }
    }
    return null
}

/**
 * Parse AST dumped to YAML by python ast module
 * TODO: save information about field names
 */
fun parsePipAst(treeFile: File): PythonPipNode {
    val offsetLines = treeFile.readLines().map { OffsetLine.fromLine(it) }
    val stack = Stack<OffsetNode>()
    for (offsetLine in offsetLines) {
        val nodeName = getNodeName(offsetLine.line)
        if (nodeName != null) {
            trimStack(stack, offsetLine.offset)
            val newNode = PythonPipNode(nodeName)
            if (!stack.empty()) {
                stack.lastElement().node.addChild(newNode)
            }
            stack.push(OffsetNode(offsetLine.offset, newNode))
            continue
        }
        val literal = getLiteral(offsetLine.line)
        if (literal != null) {
            trimStack(stack, offsetLine.offset)
            stack.lastElement().node.putToken(literal)
        }
    }
    return stack.firstElement().node
}

data class OffsetLine(val offset: Int, val line: String) {
    companion object {
        fun fromLine(l: String) = OffsetLine(l.indexOfFirst { it != ' ' }, l.trimStart())
    }
}

data class OffsetNode(val offset: Int, val node: PythonPipNode)

private fun getNodeName(line: String): String? {
    val stringToFind = "!!python/object/apply:_ast."
    return if (line.contains(stringToFind)) {
        val indexFirst = line.indexOf(stringToFind) + stringToFind.length
        val closestSpace = line.indexOf(' ', indexFirst)
        val indexLast = if (closestSpace != -1) {
            closestSpace
        } else {
            line.length
        }
        line.substring(indexFirst, indexLast)
    } else {
        null
    }
}

private fun getLiteral(line: String): String? {
    val prefixes = listOf("s: ", "n: ", "id: ", "name: ")
    for (prefix in prefixes) {
        if (line.startsWith(prefix)) {
            return line.substring(prefix.length)
        }
    }
    return null
}

private fun trimStack(stack: Stack<OffsetNode>, offset: Int) {
    while (!stack.empty() && stack.lastElement().offset >= offset) {
        stack.pop()
    }
}
