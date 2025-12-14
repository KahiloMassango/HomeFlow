package org.example.homeflow.core.util

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun dateFormatter(epochMillis: Long?): String {
    if (epochMillis == null) return ""

    val now = Clock.System.now()
    val today = now.toLocalDateTime(TimeZone.currentSystemDefault()).date

    val targetDate = Instant
        .fromEpochMilliseconds(epochMillis)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

    return when (targetDate) {
        today ->
            "Today"
        today.plus(DatePeriod(days = 1)) ->
            "Tomorrow"
        today.minus(DatePeriod(days = 1)) ->
            "Yesterday"
        else -> formatMonthDay(targetDate)
    }
}

private fun formatMonthDay(date: LocalDate): String {
    val month = date.month.name
        .lowercase()
        .replaceFirstChar { it.uppercase() }

    return "$month ${date.day}"
}
