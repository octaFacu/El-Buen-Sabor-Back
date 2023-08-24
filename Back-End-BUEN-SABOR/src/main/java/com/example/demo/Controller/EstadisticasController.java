package com.example.demo.Controller;

import com.example.demo.EstadisticasExcel.ReporteExcelRankingProductos;
import com.example.demo.EstadisticasExcel.ReporteExcelServiceImpl;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/admin/estadisticas")
public class EstadisticasController {

    @Autowired
    ReporteExcelServiceImpl service;

    @Autowired
    ReporteExcelRankingProductos serviceProducto;

    @GetMapping("/generar-informeClientes")
    public ResponseEntity<byte[]> generarInformeExcel() throws Exception {

        XSSFWorkbook workbook = service.generarReporteDeExcel();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        byte[] bytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "informe.xlsx"); // Nombre de archivo sugerido

        return new ResponseEntity<>(bytes, headers, org.springframework.http.HttpStatus.OK);
    }

    @Autowired
    private ReporteExcelRankingProductos servicee;

    @GetMapping("/generar-informeProductos")
    public ResponseEntity<byte[]> generarInformeExcelProducto() throws Exception {

        XSSFWorkbook workbook = serviceProducto.generarReporteDeExcelRankingProductos();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        byte[] bytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "informeProducto.xlsx");

        return new ResponseEntity<>(bytes, headers, org.springframework.http.HttpStatus.OK);
    }

}
