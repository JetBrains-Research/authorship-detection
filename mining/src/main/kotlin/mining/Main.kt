package mining

import mining.joern.parseJoernAst
import java.io.File

fun main(args: Array<String>) {
    val root = parseJoernAst(File("../parsed/datasets/cppSample/1.cpp/nodes.csv"),
            File("../parsed/datasets/cppSample/1.cpp/edges.csv"))
    root?.prettyPrint()
}
