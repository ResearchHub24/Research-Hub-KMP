package com.atech.research.utils

fun String.markdownToPlainText(): String =
    this
        .replace(Regex("(?m)^#{1,6}\\s"), "") // Remove headers
        .replace(Regex("\\*\\*(.+?)\\*\\*"), "$1") // Remove bold
        .replace(Regex("\\*(.+?)\\*"), "$1") // Remove italic
        .replace(Regex("__(.+?)__"), "$1") // Remove underline
        .replace(Regex("~~(.+?)~~"), "$1") // Remove strikethrough
        .replace(Regex("\\[(.+?)]\\(.+?\\)"), "$1") // Replace links with just the text
        .replace(Regex("!\\[(.+?)]\\(.+?\\)"), "$1") // Replace images with alt text
        .replace(Regex("(?m)^>\\s(.+)$"), "$1") // Remove blockquotes
        .replace(Regex("(?m)^-\\s"), "") // Remove unordered list markers
        .replace(Regex("(?m)^\\d+\\.\\s"), "") // Remove ordered list markers
        .replace(Regex("(?m)^```[\\s\\S]*?```"), "") // Remove code blocks
        .replace(Regex("`(.+?)`"), "$1") // Remove inline code
        .replace(Regex("(?m)^\\s*[-*_]{3,}\\s*$"), "") // Remove horizontal rules
        .trim()


fun String.removeExtraSpacesPreserveLineBreaks(): String =
    this.split("\n").joinToString("\n") { line ->
        line.trim().replace("\\s+".toRegex(), " ")
    }
