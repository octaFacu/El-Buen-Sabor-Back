package com.example.demo.Controller;

import com.example.demo.EstadisticasExcel.ReporteExcelServiceImpl;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/admin/estadisticas")
public class EstadisticasController {

    @Autowired
    ReporteExcelServiceImpl service;

    @GetMapping("/generar-informeClientes")
    public ResponseEntity<byte[]> generarInformeExcel() throws Exception {

        XSSFWorkbook workbook = service.generateExcelReport();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        byte[] bytes = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "informe.xlsx"); // Nombre de archivo sugerido

        return new ResponseEntity<>(bytes, headers, org.springframework.http.HttpStatus.OK);
    }

}
