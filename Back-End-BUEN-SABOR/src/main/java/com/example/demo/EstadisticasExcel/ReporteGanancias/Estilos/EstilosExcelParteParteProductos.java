package com.example.demo.EstadisticasExcel.ReporteGanancias.Estilos;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class EstilosExcelParteParteProductos {

    public void unionCeldasCabecera(Sheet hoja){
        hoja.addMergedRegion(CellRangeAddress.valueOf("A1:B1"));
        hoja.addMergedRegion(CellRangeAddress.valueOf("D1:F1"));
        hoja.addMergedRegion(CellRangeAddress.valueOf("I1:J1"));
        hoja.addMergedRegion(CellRangeAddress.valueOf("L1:N1"));
        autoAjustar(hoja);
        aplicaTitulos(hoja);
    }

    public void autoAjustar(Sheet hoja){
        int[] COLUMNAS_DENOMINACION = {0,4,8,12};
        int[] COLUMNAS_GANANCIA = {1,5,9,13};
        for (int numeros : COLUMNAS_DENOMINACION){
            hoja.setColumnWidth(numeros, 22 * 256);
        }
        for (int numeros : COLUMNAS_GANANCIA){
            hoja.setColumnWidth(numeros, 14 * 256);
        }
    }



    public CellStyle ponerFormatoPeso(Workbook workbook) {
        CellStyle estiloBordes = aplicarBordes(workbook);
        DataFormat formatoMoneda = workbook.createDataFormat();
        estiloBordes.setAlignment(HorizontalAlignment.CENTER);
        estiloBordes.setDataFormat(formatoMoneda.getFormat("$#,##0.00"));
        return estiloBordes;
    }

    public CellStyle aplicarBordes(Workbook workbook) { // agrega bordes
        CellStyle estilo = workbook.createCellStyle();
        estilo.setBorderTop(BorderStyle.THIN);
        estilo.setBorderBottom(BorderStyle.THIN);
        estilo.setBorderLeft(BorderStyle.THIN);
        estilo.setBorderRight(BorderStyle.THIN);
        estilo.setAlignment(HorizontalAlignment.CENTER);
        return estilo;
    }

    public void aplicaTitulos(Sheet hoja){
        Row fila2 = hoja.getRow(0);
        Workbook workbook = hoja.getWorkbook();
        CellStyle estilo = crearEstiloCabecera(workbook);
        estilo.setAlignment(HorizontalAlignment.CENTER);
        int[] TITULOS = {0,3,8,11};

        for (int numeros : TITULOS){
            Cell celda = fila2.getCell(numeros);
            celda.setCellStyle(estilo);
        }
    }

    public CellStyle centrado(Sheet hoja){
        CellStyle estiloBordes = aplicarBordes(hoja.getWorkbook());
        estiloBordes.setAlignment(HorizontalAlignment.CENTER);
        return estiloBordes;
    }

    public CellStyle crearEstiloCabecera(Workbook workbook) {
        CellStyle estiloCabeceras = workbook.createCellStyle();

        Font fuente = workbook.createFont();
        fuente.setColor(IndexedColors.WHITE.getIndex());
        estiloCabeceras.setFont(fuente);

        estiloCabeceras.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
        estiloCabeceras.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return estiloCabeceras;
    }

}
