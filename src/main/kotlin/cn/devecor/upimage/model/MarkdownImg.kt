package cn.devecor.upimage.model

class MarkdownImg(
    name: String,
    url: String
) {
    val link = "![$name]($url)"
}