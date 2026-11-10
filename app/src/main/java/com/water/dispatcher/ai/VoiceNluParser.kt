package com.water.dispatcher.ai

import java.util.regex.Pattern

data class WaterReadingIntent(
    val hour: Int?,
    val picketCode: String?,
    val hLevel: Double?,
    val qVolume: Double?,
    val rawText: String
)

class VoiceNluParser {

    fun parseSpeech(text: String): WaterReadingIntent {
        val lowerText = text.lowercase()

        val hourRegex = Pattern.compile("(?:soat\\s*(\\d{1,2})|(\\d{1,2})\\s*soat)")
        val hourMatcher = hourRegex.matcher(lowerText)
        var hour: Int? = null
        if (hourMatcher.find()) {
            hour = (hourMatcher.group(1) ?: hourMatcher.group(2))?.toIntOrNull()
        }

        val picketRegex = Pattern.compile("(?:pk|piket)\\s*(\\d{2,3})")
        val picketMatcher = picketRegex.matcher(lowerText)
        var picketCode: String? = null
        if (picketMatcher.find()) {
            picketCode = "PK " + picketMatcher.group(1)
        }

        val hRegex = Pattern.compile("(?:h|reyka)\\s*(\\d+(?:[.,]\\d+)?)")
        val hMatcher = hRegex.matcher(lowerText)
        var hLevel: Double? = null
        if (hMatcher.find()) {
            hLevel = hMatcher.group(1)?.replace(',', '.')?.toDoubleOrNull()
        }

        val qRegex = Pattern.compile("(?:q|kub|sarf)\\s*(\\d+(?:[.,]\\d+)?)")
        val qMatcher = qRegex.matcher(lowerText)
        var qVolume: Double? = null
        if (qMatcher.find()) {
            qVolume = qMatcher.group(1)?.replace(',', '.')?.toDoubleOrNull()
        }

        return WaterReadingIntent(hour, picketCode, hLevel, qVolume, text)
    }
}
