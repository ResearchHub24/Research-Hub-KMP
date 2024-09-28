package com.atech.research.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

enum class DatePattern(val pattern: String) {
    DD_MM_YYYY("dd-MMM yy"),
    MMM_YY("MMM yy"),
}


fun Long.convertToDateFormat(format: DatePattern = DatePattern.DD_MM_YYYY): String = this.run {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val formatter = DateTimeFormatter.ofPattern(format.pattern)
    localDateTime.toJavaLocalDateTime().format(formatter)
}