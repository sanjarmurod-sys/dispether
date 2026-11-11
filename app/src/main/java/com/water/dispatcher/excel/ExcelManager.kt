package com.water.dispatcher.excel

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import org.apache.poi.ss.usermodel.WorkbookFactory

class ExcelManager(private val filePath: String) {
    private val picketColumnMap = mapOf(
        "PK 66" to 2,
        "PK 287" to 4,
        "PK 390" to 6,
        "PK 446" to 7,
        "PK 559" to 8,
        "PK 583" to 9,
        "PK 636" to 11,
        "PK 670" to 12,
        "PK 697" to 13,
        "PK 814" to 16,
        "PK 834" to 19
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

            val rowIndex = 2 + hour
            var row = sheet.getRow(rowIndex)
            if (row == null) {
                row = sheet.createRow(rowIndex)
            }

            if (hVal != null) {
                val cellH = row.getCell(colIndex) ?: row.createCell(colIndex)
                cellH.setCellValue(hVal)
            }

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
