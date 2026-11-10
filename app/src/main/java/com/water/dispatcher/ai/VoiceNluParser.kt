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

    /**
     * Dispetcherning nutqidan Soat, Piket, H (reyka) va Q (kub) qiymatlarini ajratib oladi.
     * Masalan: "Soat 9 ga, PK 66 ga reyka 204 kub 45 deb yoz"
     */
    fun parseSpeech(text: String): WaterReadingIntent {
        val lowerText = text.lowercase()

        // 1. Soatni aniqlash
        val hourRegex = Pattern.compile("(?:soat\\s*(\\d{1,2})|(\\d{1,2})\\s*soat)")
        val hourMatcher = hourRegex.matcher(lowerText)
        var hour: Int? = null
        if (hourMatcher.find()) {
            hour = (hourMatcher.group(1) ?: hourMatcher.group(2))?.toIntOrNull()
        }

        // 2. Piket kodi (PK 66, PK 287, PK 583 va h.k.)
        val picketRegex = Pattern.compile("(?:pk|piket)\\s*(\\d{2,3})")
        val picketMatcher = picketRegex.matcher(lowerText)
        var picketCode: String? = null
        if (picketMatcher.find()) {
            picketCode = "PK " + picketMatcher.group(1)
        }

        // 3. H reyka darajasi (sm)
        val hRegex = Pattern.compile("(?:h|reyka)\\s*(\\d+(?:[.,]\\d+)?)")
        val hMatcher = hRegex.matcher(lowerText)
        var hLevel: Double? = null
        if (hMatcher.find()) {
            hLevel = hMatcher.group(1)?.replace(',', '.')?.toDoubleOrNull()
        }

        // 4. Q suv sarfi (m3/s)
        val qRegex = Pattern.compile("(?:q|kub|sarf)\\s*(\\d+(?:[.,]\\d+)?)")
        val qMatcher = qRegex.matcher(lowerText)
        var qVolume: Double? = null
        if (qMatcher.find()) {
            qVolume = qMatcher.group(1)?.replace(',', '.')?.toDoubleOrNull()
        }

        return WaterReadingIntent(hour, picketCode, hLevel, qVolume, text)
    }
}
