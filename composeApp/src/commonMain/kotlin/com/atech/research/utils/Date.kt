package com.atech.research.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Date pattern
 * This enum class is used to represent the date pattern
 * @property pattern The pattern
 * @constructor Create empty Date pattern
 * @param pattern The pattern
 */
enum class DatePattern(val pattern: String) {
    DD_MM_YYYY("dd-MMM yy"),
    MMM_YY("MMM yy"),
}

/**
 * Convert to date format
 * This function is used to convert the long to date format
 * @param format [DatePattern]
 * @return [String]
 */
fun Long.convertToDateFormat(format: DatePattern = DatePattern.DD_MM_YYYY): String = this.run {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val formatter = DateTimeFormatter.ofPattern(format.pattern)
    localDateTime.toJavaLocalDateTime().format(formatter)
}