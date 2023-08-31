package com.example.demo.EstadisticasExcel.ReporteUsuario.Estilos;

import com.example.demo.EstadisticasExcel.ReporteUsuario.PosicionesCeldasExcel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EstilosRankingClientes implements PosicionesCeldasExcel {

    public void aplicarEstilosHojaPrincipal(XSSFSheet hoja) {
        XSSFWorkbook workbook = hoja.getWorkbook();
        CellStyle estiloComun = crearEstiloComun(workbook);
        CellStyle estiloCabecera = crearEstiloCabecera(workbook);

        aplicarEstiloComun(hoja, estiloComun, estiloCabecera);
    }
    // aplica los estilos comunes
    private void aplicarEstiloComun(XSSFSheet hoja, CellStyle estiloComun, CellStyle estiloCabecera) {
        for (int rowIndex = PosicionesCeldasExcel.ID_CLIENTE; rowIndex <= hoja.getLastRowNum(); rowIndex++) {
            XSSFRow dataRow = hoja.getRow(rowIndex);
            if (dataRow != null) {
                for (int columnIndex = PosicionesCeldasExcel.ID_CLIENTE; columnIndex <= PosicionesCeldasExcel.VER_HISTORIAL; columnIndex++) {
                    XSSFCell cell = dataRow.getCell(columnIndex);
                    if (rowIndex == 0) {
                        cell.setCellStyle(estiloCabecera);
                    } else {
                        if (columnIndex == PosicionesCeldasExcel.IMPORTE_TOTAL) {
                            CellStyle estiloConFormato = hoja.getWorkbook().createCellStyle(); // Crear nuevo estilo
                            estiloConFormato.cloneStyleFrom(estiloComun); // Copiar el estilo común
                            estiloConFormato.setDataFormat(cell.getSheet().getWorkbook().createDataFormat().getFormat("$#,##0.00"));
                            cell.setCellStyle(estiloConFormato); // Aplicar el estilo con formato solo a la columna "Importe Total"
                        } else {
                            cell.setCellStyle(estiloComun);
                        }
                    }
                    hoja.autoSizeColumn(columnIndex);
                }
            }
        }
    }

    // un estilo comun para centrar los items
    private CellStyle crearEstiloComun(XSSFWorkbook workbook) {
        CellStyle estilo = workbook.createCellStyle();
        estilo.setAlignment(HorizontalAlignment.CENTER);
        aplicarBordes(estilo);
        return estilo;
    }

    private CellStyle crearEstiloCabecera(XSSFWorkbook workbook) {
        CellStyle estilo = workbook.createCellStyle();
        estilo.setAlignment(HorizontalAlignment.CENTER);
        estilo.setFillForegroundColor(IndexedColors.SEA_GREEN.getIndex());
        estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font fuente = workbook.createFont();
        fuente.setColor(IndexedColors.WHITE.getIndex());
        estilo.setFont(fuente);

        aplicarBordes(estilo);

        return estilo;
    }

    // se encarga de agregar los estilos a nuestros historiales
    public void aplicarEstilosHojaHistorial(XSSFSheet hojaHistorialPedidos) {
        XSSFWorkbook workbook = hojaHistorialPedidos.getWorkbook();
        CellStyle estiloComun = crearEstiloComun(workbook);
        CellStyle estiloCabecera = crearEstiloCabecera(workbook);

        for (int rowIndex = PosicionesCeldasExcel.ID_CLIENTE; rowIndex <= hojaHistorialPedidos.getLastRowNum(); rowIndex++) {
            XSSFRow dataRow = hojaHistorialPedidos.getRow(rowIndex);
            if (dataRow != null) {
                for (int columnIndex = PosicionesCeldasExcel.ID_CLIENTE; columnIndex <= PosicionesCeldasExcel.DENOMINACION; columnIndex++) {
                    XSSFCell cell = dataRow.getCell(columnIndex);
                    if (cell != null) {
                        if (rowIndex == 0) {
                            cell.setCellStyle(estiloCabecera);
                        } else {
                            if (columnIndex == PosicionesCeldasExcel.PRECIO_TOTAL) {
                                estiloComun.setDataFormat(cell.getSheet().getWorkbook().createDataFormat().getFormat("$#,##0.00"));
                                cell.setCellStyle(estiloComun);
                            } else {
                                cell.setCellStyle(estiloComun);
                            }
                        }
                    }
                }
            }
        }
        hojaHistorialPedidos.autoSizeColumn(3);
    }

    private void aplicarBordes(CellStyle estilo) { // agrega bordes
        estilo.setBorderTop(BorderStyle.THIN);
        estilo.setBorderBottom(BorderStyle.THIN);
        estilo.setBorderLeft(BorderStyle.THIN);
        estilo.setBorderRight(BorderStyle.THIN);
    }
}
