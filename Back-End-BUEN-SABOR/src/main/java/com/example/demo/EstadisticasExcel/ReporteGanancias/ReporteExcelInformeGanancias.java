package com.example.demo.EstadisticasExcel.ReporteGanancias;


import com.example.demo.Repository.ProductoRepository;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.OutputStream;


@Service
public class ReporteExcelInformeGanancias {

    @Autowired
    private ProductoRepository repository;

    private ReporteGananciasParteProductos productos = new ReporteGananciasParteProductos();
    private ReporteGananciasParteDiasMesesAños gananciasDiasMesesAños = new ReporteGananciasParteDiasMesesAños();

    public XSSFWorkbook crearLibro() throws Exception {
        try {
            XSSFWorkbook libro = new XSSFWorkbook();
            Sheet hoja = libro.createSheet("Ganancias Restaurante");
            Sheet hoja2 = libro.createSheet("Rentabilidad de Comidas");

            gananciasDiasMesesAños.CrearCabeceraHojaGanancias(hoja);
            gananciasDiasMesesAños.llenarDatosGananciaRestaurante(hoja, repository);

            productos.crearCabeceraRentabilidadComida(hoja2);
            productos.llenarDatosRentabilidadProductos(hoja2, repository);

            return libro;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
