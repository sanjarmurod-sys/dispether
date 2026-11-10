package com.water.dispatcher.excel

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import org.apache.poi.ss.usermodel.WorkbookFactory

class ExcelManager(private val filePath: String) {

    // KAMK faylidagi piketlarning ustun tartib indeksi
    private val picketColumnMap = mapOf(
        "PK 66" to 2,   // Col C: H, Col D: Q
        "PK 287" to 4,  // Col E: H, Col F: Q
        "PK 390" to 6,  // Col G: H
        "PK 446" to 7,  // Col H: H
        "PK 559" to 8,  // Col I: H
        "PK 583" to 9,  // Col J: H, Col K: Q
        "PK 636" to 11, // Col L: H
        "PK 670" to 12, // Col M: H
        "PK 697" to 13, // Col N: H
        "PK 814" to 16, // Col Q: H
        "PK 834" to 19  // Col T: H, Col U: Q
    )

    fun writeReading(sheetName: String, hour: Int, picketCode: String, hVal: Double?, qVal: Double?): Boolean {
        val colIndex = picketColumnMap[picketCode] ?: return false
        val file = File(filePath)
        if (!file.exists()) return false

        try {
            val inputStream = FileInputStream(file)
            val workbook = WorkbookFactory.create(inputStream)
            inputStream.close()

            var sheet = workbook.getSheet(sheetName)
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName)
            }

            // 1..24 soatlik qatorlar 3-qatordan boshlanadi (Row Index = 2 + hour)
            val rowIndex = 2 + hour
            var row = sheet.getRow(rowIndex)
            if (row == null) {
                row = sheet.createRow(rowIndex)
            }

            // H reykani katakka yozish
            if (hVal != null) {
                val cellH = row.getCell(colIndex) ?: row.createCell(colIndex)
                cellH.setCellValue(hVal)
            }

            // Q sarf (kub) katakka yozish
            if (qVal != null) {
                val cellQ = row.getCell(colIndex + 1) ?: row.createCell(colIndex + 1)
                cellQ.setCellValue(qVal)
            }

            val outputStream = FileOutputStream(file)
            workbook.write(outputStream)
            outputStream.close()
            workbook.close()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}
