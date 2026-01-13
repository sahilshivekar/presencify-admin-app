package edu.watumull.presencify.core.presentation.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

object DateTimeUtils {
    fun getCurrentDate(): LocalDate = getCurrentLocalDateTime().date
    fun getCurrentTime(): LocalTime = getCurrentLocalDateTime().time

    @OptIn(ExperimentalTime::class)
    fun getCurrentLocalDateTime(): LocalDateTime {
        // 1. Get UTC String: "2026-01-13T10:57:30.123Z"
        val utcString = Clock.System.now().toString().removeSuffix("Z")

        // 2. Parse Raw Components
        val parts = utcString.split("T")
        val dateParts = parts[0].split("-")
        val timeParts = parts[1].split(":")

        var year = dateParts[0].toInt()
        var month = dateParts[1].toInt()
        var day = dateParts[2].toInt()

        var hour = timeParts[0].toInt()
        var minute = timeParts[1].toInt()

        // Handle seconds and nanoseconds
        val secParts = timeParts[2].split(".")
        val second = secParts[0].toInt()
        val nanosecond = if (secParts.size > 1) {
            secParts[1].padEnd(9, '0').take(9).toInt()
        } else 0

        // ==========================================
        // 3. APPLY OFFSET (IST = +5 Hours, +30 Mins)
        // ==========================================

        // Add Minutes
        minute += 30
        if (minute >= 60) {
            minute -= 60
            hour += 1
        }

        // Add Hours
        hour += 5
        if (hour >= 24) {
            hour -= 24
            day += 1
        }

        // Handle Day Rollover (The tricky part)
        // Check if the new day exceeds the number of days in the current month
        val daysInCurrentMonth = getDaysInMonth(month, year)

        if (day > daysInCurrentMonth) {
            day -= daysInCurrentMonth // e.g., Jan 32 -> Feb 1
            month += 1

            // Handle Month/Year Rollover
            if (month > 12) {
                month = 1
                year += 1
            }
        }

        // 4. Return the adjusted Local Time
        return LocalDateTime(year, month, day, hour, minute, second, nanosecond)
    }

    /**
     * Helper to get accurate days in month, including Leap Years.
     */
    private fun getDaysInMonth(month: Int, year: Int): Int {
        return when (month) {
            4, 6, 9, 11 -> 30 // April, June, Sept, Nov
            2 -> if (isLeapYear(year)) 29 else 28 // February
            else -> 31 // Jan, Mar, May, July, Aug, Oct, Dec
        }
    }

    /**
     * Standard Leap Year Logic
     */
    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }
}


// 08:01 PM
fun LocalTime.toReadableString(): String {
    val amPm = if (this.hour < 12) "AM" else "PM"

    val hour12 = when {
        this.hour == 0 -> 12
        this.hour > 12 -> this.hour - 12
        else -> this.hour
    }

    val timePart = LocalTime(hour12, this.minute).format(LocalTime.Format {
        hour(Padding.ZERO)
        char(':')
        minute(Padding.ZERO)
    })

    return "$timePart $amPm"
}

// day/month/year
fun LocalDate.toReadableString(): String {
    return this.format(LocalDate.Format {
        dayOfMonth(Padding.ZERO)
        char('/')
        monthNumber(Padding.ZERO)
        char('/')
        year()
    })
}