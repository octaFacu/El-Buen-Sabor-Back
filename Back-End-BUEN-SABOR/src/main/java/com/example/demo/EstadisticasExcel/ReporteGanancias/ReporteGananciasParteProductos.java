package com.example.demo.EstadisticasExcel.ReporteGanancias;

import com.example.demo.Entidades.Proyecciones.ProyeccionInformeRentabilidad;
import com.example.demo.EstadisticasExcel.ReporteGanancias.Estilos.EstilosExcelParteParteProductos;
import com.example.demo.Repository.ProductoRepository;
import org.apache.poi.ss.usermodel.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ReporteGananciasParteProductos implements PosicionesExcelGananciasParteProductos{
    EstilosExcelParteParteProductos estilosExcel = new EstilosExcelParteParteProductos();
    public void crearCabeceraRentabilidadComida(Sheet hoja) throws Exception {
        Row fila = hoja.createRow(FILA_CABECERA_TITULOS);
        fila.createCell(COLUMNA_CABECERA_RENTABILIDAD).setCellValue("Rentabilidad Historica");
        fila.createCell(COLUMNA_CABECERA_RENTABILIDAD_3_MESES).setCellValue("Rentabilidad ultimos 3 meses");
        fila.createCell(COLUMNA_MEJOR_PEOR_HISTORICO).setCellValue("Mejores y peores comidas Historicas");
        fila.createCell(COLUMNA_MEJOR_PEOR_3_MESES).setCellValue("Mejores y peores comidas ultimos 3 meses");

        estilosExcel.unionCeldasCabecera(hoja);

        Row fila1 = hoja.createRow(FILA_CABECERA_MEJOR);
        Row fila6 = hoja.createRow(FILA_CABECERA_PEOR);
        Workbook workbook = hoja.getWorkbook();
        CellStyle estilo = estilosExcel.aplicarBordes(workbook);
        estilo.setAlignment(HorizontalAlignment.CENTER);

        for (int i = 0; i <= 10; i += 8) {
            Cell celda = fila1.createCell(i);
            celda.setCellValue("Denominacion");
            celda.setCellStyle(estilo);
            Cell celda2 = fila1.createCell(i+1);
            celda2.setCellValue("Ganancia");
            celda2.setCellStyle(estilo);
        }
        for(int i = 3; i <=11; i+= 8){
            Cell celda = fila1.createCell(i);
            celda.setCellValue("Mejores");
            celda.setCellStyle(estilo);

            Cell celda2 = fila6.createCell(i);
            celda2.setCellValue("Peores");
            celda2.setCellStyle(estilo);
        }
    }

    public void llenarDatosRentabilidadProductos(Sheet hoja, ProductoRepository repository){

        Date diaAc = new Date();
        Date haceTresMeses = Date.from(LocalDate.now().minusMonths(3).withDayOfMonth(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<ProyeccionInformeRentabilidad> productos = repository.graficoRentabilidad(null, null);
        List<ProyeccionInformeRentabilidad> productosPorTresMeses = repository.graficoRentabilidad(haceTresMeses, diaAc);

        rellenaDatos(productos, hoja, COLUMNA_COMIENZO_DENOMINACION_HISTORICO, COLUMNA_COMIENZO_GANANCIA_HISTORICO);
        rellenaDatos(productosPorTresMeses, hoja, COLUMNA_COMIENZO_DENOMINACION_3_MESES, COLUMNA_COMIENZO_GANANCIA_3_MESES);
        genereraMejoresYPeoresgananciasProductos(hoja,obtenerMejoresGanancias(productos, 3), obtenerPeoresGanancias(productos,3), 1);
        genereraMejoresYPeoresgananciasProductos(hoja,obtenerMejoresGanancias(productosPorTresMeses, 3), obtenerPeoresGanancias(productosPorTresMeses,3), 2);
    }

    public void rellenaDatos(List<ProyeccionInformeRentabilidad> rentabilidad, Sheet hoja, int celdaDenominacion, int celdaGanancia){
        int contador = FILA_COMIENZO_DATOS;
        Row fila;

        CellStyle estilo = estilosExcel.ponerFormatoPeso(hoja.getWorkbook());
        CellStyle estiloDenominacion = estilosExcel.centrado(hoja);

        for(ProyeccionInformeRentabilidad porTresMeses : rentabilidad){
            fila = obtenerOCrearFila(hoja, contador);
            fila.createCell(celdaDenominacion).setCellValue(porTresMeses.getDenominacion());
            fila.getCell(celdaDenominacion).setCellStyle(estiloDenominacion);

            Cell celda = fila.createCell(celdaGanancia);
            celda.setCellValue(porTresMeses.getGanancia());
            celda.setCellStyle(estilo);

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

    public void genereraMejoresYPeoresgananciasProductos
            (Sheet hoja, List<ProyeccionInformeRentabilidad> mejoresGanancias, List<ProyeccionInformeRentabilidad> peoresGanancias, int opcion) {

        CellStyle estilo = estilosExcel.ponerFormatoPeso(hoja.getWorkbook());
        CellStyle estiloDenominacion = estilosExcel.centrado(hoja);

        int contador = FILA_COMIENZO_MEJORES_COMIDAS;
        for (ProyeccionInformeRentabilidad historica : mejoresGanancias) {
            Row fila = hoja.getRow(contador);
            fila.createCell((opcion == 1)
                    ? COLUMNA_MEJORES_PEORES_DENOMINACION_HISTORICO : COLUMNA_MEJORES_PEORES_DENOMINACION_3_MESES).setCellValue(historica.getDenominacion());
            fila.getCell((opcion == 1)
                    ? COLUMNA_MEJORES_PEORES_DENOMINACION_HISTORICO : COLUMNA_MEJORES_PEORES_DENOMINACION_3_MESES).setCellStyle(estiloDenominacion);

            Cell celda =  fila.createCell((opcion == 1)
                    ? COLUMNA_MEJORES_PEORES_GANANCIA_HISTORICO : COLUMNA_MEJORES_PEORES_GANANCIA_3_MESES);
            celda.setCellValue(historica.getGanancia());
            celda.setCellStyle(estilo);
            contador++;
        }

        contador = FILA_COMIENZO_PEORES_COMIDAS;
        for (ProyeccionInformeRentabilidad historica : peoresGanancias) {
            Row fila = hoja.getRow(contador);
            fila.createCell((opcion == 1)
                    ? COLUMNA_MEJORES_PEORES_DENOMINACION_HISTORICO : COLUMNA_MEJORES_PEORES_DENOMINACION_3_MESES).setCellValue(historica.getDenominacion());
            fila.getCell((opcion == 1)
                    ? COLUMNA_MEJORES_PEORES_DENOMINACION_HISTORICO : COLUMNA_MEJORES_PEORES_DENOMINACION_3_MESES).setCellStyle(estiloDenominacion);

            Cell celda =  fila.createCell((opcion == 1)
                    ? COLUMNA_MEJORES_PEORES_GANANCIA_HISTORICO : COLUMNA_MEJORES_PEORES_GANANCIA_3_MESES);
            celda.setCellValue(historica.getGanancia());
            celda.setCellStyle(estilo);

            contador++;
        }
    }

    public List<ProyeccionInformeRentabilidad> obtenerMejoresGanancias(List<ProyeccionInformeRentabilidad> ganancias, int cantidad) {
        List<ProyeccionInformeRentabilidad> copiaGanancias = new ArrayList<>(ganancias);
        Collections.sort(copiaGanancias, Comparator.comparingInt(ProyeccionInformeRentabilidad::getGanancia).reversed());
        return copiaGanancias.subList(0, Math.min(cantidad, copiaGanancias.size()));
    }

    public  List<ProyeccionInformeRentabilidad> obtenerPeoresGanancias(List<ProyeccionInformeRentabilidad> ganancias, int cantidad) {
        List<ProyeccionInformeRentabilidad> copiaGanancias = new ArrayList<>(ganancias);
        Collections.sort(copiaGanancias, Comparator.comparingInt(ProyeccionInformeRentabilidad::getGanancia));
        return copiaGanancias.subList(0, Math.min(cantidad, ganancias.size()));
    }
}
