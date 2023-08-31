package com.example.demo.EstadisticasExcel.ReporteGanancias.Estilos;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class EstilosExcelParteDiaMesAnio {

    public void unionCeldasCabecera(Sheet hoja){
        hoja.addMergedRegion(CellRangeAddress.valueOf("A1:B1"));
        hoja.addMergedRegion(CellRangeAddress.valueOf("D1:F1"));
        hoja.addMergedRegion(CellRangeAddress.valueOf("H1:I1"));
        hoja.addMergedRegion(CellRangeAddress.valueOf("K1:M1"));
        hoja.addMergedRegion(CellRangeAddress.valueOf("O1:P1"));
        hoja.addMergedRegion(CellRangeAddress.valueOf("R1:T1"));
        centradoCabecera(hoja);
        autoAjusteCeldas(hoja);
        aplicarBordesFechaGanancia(hoja);
        aplicarBordesPeorMejor(hoja);
    }

    public void centradoCabecera(Sheet hoja){
        Workbook workbook = hoja.getWorkbook();
        CellStyle estilo = workbook.createCellStyle();
        CellStyle estiloCabeceraFila0 = crearEstiloCabecera(workbook);

        estilo.setAlignment(HorizontalAlignment.CENTER);
        estiloCabeceraFila0.setAlignment(HorizontalAlignment.CENTER);

        int[] FILAS_CABECERA_CENTRADO = {0, 3, 7, 10, 14, 17};

        Row fila = hoja.getRow(0);
        for (int numero : FILAS_CABECERA_CENTRADO) {
            Cell celda = fila.getCell(numero);
            celda.setCellStyle(estiloCabeceraFila0);
        }

    }

    public void autoAjusteCeldas(Sheet hoja){
        int[] COLUMNAS_FECHAS = {0,4,7,11,14,18};
        int[] COLUMNAS_GANANCIAS = {1,5,8,12,15,19};

        for (int numeros : COLUMNAS_FECHAS){
            hoja.setColumnWidth(numeros, 19 * 256);
        }
        for (int numeros : COLUMNAS_GANANCIAS){
            hoja.setColumnWidth(numeros, 13 * 256);
        }
    }

    public CellStyle ponerFormatoPeso(Workbook workbook) {
        CellStyle estiloBordes = aplicarBordes(workbook);
        DataFormat formatoMoneda = workbook.createDataFormat();
        estiloBordes.setDataFormat(formatoMoneda.getFormat("$#,##0.00"));
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

    public CellStyle aplicarBordes(Workbook workbook) { // agrega bordes
        CellStyle estilo = workbook.createCellStyle();
        estilo.setBorderTop(BorderStyle.THIN);
        estilo.setBorderBottom(BorderStyle.THIN);
        estilo.setBorderLeft(BorderStyle.THIN);
        estilo.setBorderRight(BorderStyle.THIN);
        estilo.setAlignment(HorizontalAlignment.CENTER);
        return estilo;
    }

    public void aplicarBordesFechaGanancia(Sheet hoja){
        Row fila2 = hoja.getRow(1);
        Workbook workbook = hoja.getWorkbook();
        CellStyle estilo = aplicarBordes(workbook);

        for (int i=0; i<=14; i+=7){
            Cell celda = fila2.getCell(i);
            Cell celda2 = fila2.getCell(i+1);
            celda.setCellStyle(estilo);
            celda2.setCellStyle(estilo);
        }
    }

    public void aplicarBordesPeorMejor(Sheet hoja){
        Row fila2 = hoja.getRow(1);
        Row fila3 = hoja.getRow(2);
        Workbook workbook = hoja.getWorkbook();
        CellStyle estilo = aplicarBordes(workbook);

        for (int i=3; i<=17; i+=7){
            Cell celda = fila2.getCell(i);
            Cell celda2 = fila3.getCell(i);
            celda.setCellStyle(estilo);
            celda2.setCellStyle(estilo);
        }
    }

}
