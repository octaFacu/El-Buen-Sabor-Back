package com.example.demo.EstadisticasExcel.ReporteGanancias;

import com.example.demo.Entidades.Proyecciones.ProyeccionGananciaMes;
import com.example.demo.EstadisticasExcel.ReporteGanancias.Estilos.EstilosExcelParteDiaMesAnio;
import com.example.demo.Repository.ProductoRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.text.SimpleDateFormat;
import java.util.*;

public class ReporteGananciasParteDiasMesesAños implements PosicionesExcelGananciasParteDiaMesAnio {

    EstilosExcelParteDiaMesAnio estiloCabecera = new EstilosExcelParteDiaMesAnio();
    public void CrearCabeceraHojaGanancias( Sheet hoja) throws Exception {
        Row fila0 = hoja.createRow(FILA_CABECERA);
        fila0.createCell(COLUMNA_GANANCIA_POR_DIA).setCellValue("Ganancia por dia");
        fila0.createCell(COLUMNA_MEJOR_PEOR_DIA).setCellValue("Mejor y peor Dia");
        fila0.createCell(COLUMNA_GANANCIA_POR_MES).setCellValue("Ganancia por mes");
        fila0.createCell(COLUMNA_MEJOR_PEOR_MES).setCellValue("Mejor y peor Mes");
        fila0.createCell(COLUMNA_GANANCIA_POR_ANIO).setCellValue("Ganancia por año");
        fila0.createCell(COLUMNA_MEJOR_PEOR_ANIO).setCellValue("Mejor y peor año");

        cabeceraParteFechaGanancia(hoja);
        cabeceraParteMejorYPeor(hoja);
        estiloCabecera.unionCeldasCabecera(hoja);
    }

    public void cabeceraParteFechaGanancia(Sheet hoja) throws Exception {
        Row fila1 = hoja.createRow(FILA_FECHA_GANANCIA);
        for (int i = 0; i <= 14; i += 7) {
            fila1.createCell(i).setCellValue("Fecha");
            fila1.createCell(i + 1).setCellValue("Ganancia");
        }
    }

    public void cabeceraParteMejorYPeor(Sheet hoja) throws Exception{
        Row fila4 = hoja.getRow(FILA_FECHA_GANANCIA);
        Row fila5 = hoja.createRow(FILA_TITULO_PEOR);
        for (int i = 3; i<18; i+=7){
            fila4.createCell(i).setCellValue("Mejor");
            fila5.createCell(i).setCellValue("Peor");
        }

    }

    public void llenarDatosGananciaRestaurante(Sheet hoja,  ProductoRepository repository) throws Exception {

        List<ProyeccionGananciaMes> gananciasDia = repository.graficoGananciasDia();
        List<ProyeccionGananciaMes> gananciasMes = repository.graficoGanancias();
        List<ProyeccionGananciaMes> gananciasAnio = repository.graficoGananciasAnio();

        SimpleDateFormat formatoFechadia = new SimpleDateFormat("dd MMMM yyyy", new Locale("es", "ES"));
        SimpleDateFormat formatoFechaMes = new SimpleDateFormat("MMMM yyyy", new Locale("es", "ES"));
        SimpleDateFormat formatoFechaAnio = new SimpleDateFormat("yyyy", new Locale("es", "ES"));

        generarDatosGanancia(formatoFechadia, gananciasDia, hoja, CELDA_FECHAS_DIA, CELDA_GANANCIAS_DIA);
        generarDatosGanancia(formatoFechaMes, gananciasMes, hoja, CELDA_FECHAS_MES, CELDA_GANANCIAS_MES);
        generarDatosGanancia(formatoFechadia, gananciasAnio, hoja, CELDA_FECHAS_ANIO, CELDA_GANANCIAS_ANIO);

        List<ProyeccionGananciaMes> mejorYpeorDia = ObtenerMejorYpeorDia(gananciasDia);
        generarMejorYpeorRentabilidadPorFechas(hoja, mejorYpeorDia, 1, formatoFechadia);

        List<ProyeccionGananciaMes> mejorYpeorMes = ObtenerMejorYpeorDia(gananciasMes);
        generarMejorYpeorRentabilidadPorFechas(hoja, mejorYpeorMes, 2, formatoFechadia);

        List<ProyeccionGananciaMes> mejorYpeorAnio = ObtenerMejorYpeorDia(gananciasAnio);
        generarMejorYpeorRentabilidadPorFechas(hoja, mejorYpeorAnio, 3, formatoFechadia);
    }

    public void generarDatosGanancia(SimpleDateFormat formatoFecha, List<ProyeccionGananciaMes> ganancias, Sheet hoja, int celdaFecha, int celdaGanancia) {
        int contador = FILA_COMIENZO_DATOS;

        CellStyle estiloMoneda = estiloCabecera.ponerFormatoPeso(hoja.getWorkbook()); // Obtén el estilo de moneda
        CellStyle estiloBordes = estiloCabecera.aplicarBordes(hoja.getWorkbook()); // Obtén el estilo de bordes

        for (ProyeccionGananciaMes ganancia : ganancias) {
            Row fila = obtenerOCrearFila(hoja, contador);
            Cell celdaFec = fila.createCell(celdaFecha);
            celdaFec.setCellValue(formatoFecha.format(ganancia.getFecha()));
            celdaFec.setCellStyle(estiloBordes);

            Cell celdaGan = fila.createCell(celdaGanancia);
            celdaGan.setCellValue(ganancia.getGanancia());
            celdaGan.setCellStyle(estiloMoneda);
            contador++;
        }
    }
    private Row obtenerOCrearFila(Sheet hoja, int numeroFila) {
        Row fila = hoja.getRow(numeroFila);
        if (fila == null) {
            fila = hoja.createRow(numeroFila);
        }
        return fila;
    }
    public void generarMejorYpeorRentabilidadPorFechas(Sheet hoja, List<ProyeccionGananciaMes> mejorYpeor, int opcion, SimpleDateFormat formatoFecha) {

        int contador = FILA_COMIENZO_MEJOR_PEOR;
        int columnaFecha;
        int columnaGanancia;

        switch (opcion) {
            case 1:
                columnaFecha = CELDA_MEJOR_PEOR_FECHA_DIA;
                columnaGanancia = CELDA_MEJOR_PEOR_GANANCIA_DIA;
                break;
            case 2:
                columnaFecha = CELDA_MEJOR_PEOR_FECHA_MES;
                columnaGanancia = CELDA_MEJOR_PEOR_GANANCIA_MES;
                break;
            case 3:
                columnaFecha = CELDA_MEJOR_PEOR_FECHA_ANIO;
                columnaGanancia = CELDA_MEJOR_PEOR_GANANCIA_ANIO;
                break;
            default:
                return;
        }
        CellStyle estiloMoneda = estiloCabecera.ponerFormatoPeso(hoja.getWorkbook()); // Obtén el estilo de moneda
        CellStyle estiloBordes = estiloCabecera.aplicarBordes(hoja.getWorkbook()); // Obtén el estilo de bordes



        for (ProyeccionGananciaMes mYp : mejorYpeor) {
            Row fila = hoja.getRow(contador);
            if (fila != null) { // aca nos aseguramos que exista la fila
                Cell celdaFecha = fila.createCell(columnaFecha);
                celdaFecha.setCellValue(formatoFecha.format(mYp.getFecha()));
                celdaFecha.setCellStyle(estiloBordes);

                Cell celdaGan = fila.createCell(columnaGanancia);
                celdaGan.setCellValue(mYp.getGanancia());
                celdaGan.setCellStyle(estiloMoneda);
            }
            contador++;
        }
    }
    public List<ProyeccionGananciaMes> ObtenerMejorYpeorDia( List<ProyeccionGananciaMes> ganancias){
        ProyeccionGananciaMes minGanancia = Collections.min(ganancias, Comparator.comparingInt(ProyeccionGananciaMes::getGanancia));
        ProyeccionGananciaMes maxGanancia = Collections.max(ganancias, Comparator.comparingInt(ProyeccionGananciaMes::getGanancia));
        List<ProyeccionGananciaMes> mejorYpeor = new ArrayList<>();
        mejorYpeor.add(maxGanancia);
        mejorYpeor.add(minGanancia);
        return mejorYpeor;
    }
}
