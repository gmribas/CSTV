package com.gmribas.cstv.core.extensions

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.text.SimpleDateFormat
import java.util.Date
import com.gmribas.cstv.R

fun String.toFormattedDateLabel(context: Context): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        try {
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
    } else {
        // Fallback for API < 26
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEE, h a", Locale.getDefault())
            val date = inputFormat.parse(this)
            date?.let { outputFormat.format(it) } ?: context.getString(R.string.date_tbd)
        } catch (e: Exception) {
            context.getString(R.string.date_tbd)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toIsoDateString(): String {
    return this.format(DateTimeFormatter.ISO_LOCAL_DATE)
}

fun getTodayAsIsoString(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDate.now().toIsoDateString()
    } else {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateFormat.format(Date())
    }
}
