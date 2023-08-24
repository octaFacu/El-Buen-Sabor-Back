package com.example.demo.EstadisticasExcel.Estilos;

import com.example.demo.EstadisticasExcel.PosicionesCeldasExcelRankingProductos;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class EstilosRankingProductos implements PosicionesCeldasExcelRankingProductos {



    public XSSFCellStyle crearEstiloCabecera(XSSFWorkbook workbook) {
        XSSFCellStyle estilo = workbook.createCellStyle();

        estilo.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
        estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont fuente = workbook.createFont();
        fuente.setColor(IndexedColors.WHITE.getIndex());
        fuente.setBold(true);
        estilo.setFont(fuente);

        estilo.setAlignment(HorizontalAlignment.CENTER);
        estilo.setVerticalAlignment(VerticalAlignment.CENTER);

        estilo.setBorderTop(BorderStyle.THIN);
        estilo.setBorderBottom(BorderStyle.THIN);
        estilo.setBorderLeft(BorderStyle.THIN);
        estilo.setBorderRight(BorderStyle.THIN);

        return estilo;
    }

    public XSSFCellStyle crearEstiloDatos(XSSFWorkbook workbook) {
        XSSFCellStyle estilo = workbook.createCellStyle();

        estilo.setAlignment(HorizontalAlignment.CENTER);
        estilo.setVerticalAlignment(VerticalAlignment.CENTER);

        estilo.setBorderTop(BorderStyle.THIN);
        estilo.setBorderBottom(BorderStyle.THIN);
        estilo.setBorderLeft(BorderStyle.THIN);
        estilo.setBorderRight(BorderStyle.THIN);

        return estilo;
    }


}
