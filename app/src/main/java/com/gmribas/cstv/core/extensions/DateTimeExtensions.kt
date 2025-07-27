package com.gmribas.cstv.core.extensions

import android.content.Context
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import com.gmribas.cstv.R

fun String.toFormattedDateLabel(context: Context): String {
    return try {
        val matchDateTime = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
        val today = LocalDate.now()
        val matchDate = matchDateTime.toLocalDate()
        
        when {
            matchDate.isEqual(today) -> {
                val timeFormatter = DateTimeFormatter.ofPattern("h a", Locale.getDefault())
                "${context.getString(R.string.date_today)}, ${matchDateTime.format(timeFormatter)}"
            }
            matchDate.isEqual(today.plusDays(1)) -> {
                val timeFormatter = DateTimeFormatter.ofPattern("h a", Locale.getDefault())
                "${context.getString(R.string.date_tomorrow)}, ${matchDateTime.format(timeFormatter)}"
            }
            else -> {
                val dayTimeFormatter = DateTimeFormatter.ofPattern("EEE, h a", Locale.getDefault())
                matchDateTime.format(dayTimeFormatter)
            }
        }
    } catch (e: Exception) {
        context.getString(R.string.date_tbd)
    }
}

fun LocalDate.toIsoDateString(): String {
    return this.format(DateTimeFormatter.ISO_LOCAL_DATE)
}

fun getTodayAsIsoString(): String {
    return LocalDate.now().toIsoDateString()
}
