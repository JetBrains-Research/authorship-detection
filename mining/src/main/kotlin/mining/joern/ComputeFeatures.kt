package mining.joern

import astminer.common.Node
import java.io.File

fun computeFeatures(root: JoernNode, file: File): MutableMap<String, Float> {
    val text = file.readText()
    val features = mutableMapOf<String, Float>()
    computeCppTF(text, features)
    computeCTF(text, features)
    computeLayoutFeatures(text, features)
    computeLexicalFeatures(root, text, features)
    return features
}

val cppKeywords = arrayOf("alignas", "alignof", "and", "and_eq", "asm", "auto", "bitand", "bitor", "bool",
        "break", "case", "catch", "char", "char16_t", "char32_t", "class", "compl", "const", "constexpr", "const_cast",
        "continue", "decltype", "default", "delete", "do", "double", "dynamic_cast", "else", "enum", "explicit",
        "export", "extern", "FALSE", "float", "for", "friend", "goto", "if", "inline", "int", "long", "mutable",
        "namespace", "new", "noexcept", "not", "not_eq", "nullptr", "operator", "or", "or_eq", "private",
        "protected", "public", "register", "reinterpret_cast", "return", "short", "signed", "sizeof",
        "static", "static_assert", "static_cast", "struct", "switch", "template", "this", "thread_local", "throw",
        "TRUE", "try", "typedef", "typeid", "typename", "union", "unsigned", "using", "virtual", "void", "volatile",
        "wchar_t", "while", "xor", "xor_eq", "override", "final")

fun computeCppTF(text: String, features: MutableMap<String, Float>) {
    for (keyword in cppKeywords) {
        features["cppKeywords:$keyword"] = text.count(keyword)
    }
}

val cKeywords = arrayOf("auto", "break", "case", "char", "const", "continue", "default", "do", "double",
        "else", "enum", "extern", "float", "for", "goto", "if", "inline", "int", "long", "register", "restrict",
        "return", "short", "signed", "sizeof", "static", "struct", "switch", "typedef", "union", "unsigned", "void",
        "volatile", "while", "_Alignas", "_Alignof", "_Atomic", "_Bool", "_Complex", "_Generic", "_Imaginary",
        "_Noreturn", "_Static_assert", "_Thread_local")

fun computeCTF(text: String, features: MutableMap<String, Float>) {
    for (keyword in cKeywords) {
        features["cppKeywords:$keyword"] = text.count(keyword)
    }
}

fun computeLayoutFeatures(text: String, features: MutableMap<String, Float>) {
    val length = text.length.toFloat()
    features["fileLength"] = length
    features["numTabsRatio"] = text.count("\t") / length
    features["numSpacesRatio"] = text.count(" ") / length
    features["emptyLinesRatio"] = text.count("\n\n") / length
    features["whitespaceRatio"] = text.count { it.toInt() <= 32 } / length
    features["newLineBeforeOpenBrace"] = (text.count("\n{") + text.count("\t{")) / text.count("{")
    features["spaceBeforeOpenBrace"] = text.count(" {") / text.count("{")
    features["braceBeforeOpenBrace"] = text.count("){") / text.count("{")
    features["tabsBeginLines"] = text.count("\n\t") / text.count("\n")
    features["spacesBeginLines"] = text.count("\n ") / text.count("\n")
}

fun computeLexicalFeatures(root: JoernNode, text: String, features: MutableMap<String, Float>) {
    val length = text.length.toFloat()
    val numLines = text.count("\n") + 1
    val numFunctions = root.count("Function")
    features["numMacrosRatio"] = text.count("#define") / length
    features["numFunctionsRatio"] = numFunctions / length
    features["numLiteralsRatio"] = root.count("PrimaryExpression") / length
    features["numCommentsRatio"] = (text.count("//") + text.count("/*")) / length
    features["numTernaryRatio"] = text.count("?") / length
    features["avgLineLength"] = length / numLines
    features["avgParams"] = root.count("Parameter").toFloat() / numFunctions
}

fun String.count(other: String): Float = (this.split(other).size - 1).toFloat()

fun Node.count(nodeType: String): Int {
    var cnt = 0
    if (this.getTypeLabel() == nodeType) {
        cnt = 1
    }
    for (child in this.getChildren()) {
        cnt += child.count(nodeType)
    }
    return cnt
}
