package com.example.demo.Controller;

import com.example.demo.EstadisticasExcel.ReporteGanancias.ReporteExcelInformeGanancias;
import com.example.demo.EstadisticasExcel.ReporteProductos.ReporteExcelRankingProductos;
import com.example.demo.EstadisticasExcel.ReporteUsuario.ReporteExcelServiceImpl;
import com.example.demo.Security.AdminOnly;
import com.example.demo.Security.PublicEndpoint;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/admin/estadisticas")
public class EstadisticasController {

    @Autowired
    private ReporteExcelServiceImpl service;

    @Autowired
   private ReporteExcelRankingProductos serviceProducto;

    @Autowired
    private ReporteExcelInformeGanancias servicioPrueba;
    @PublicEndpoint
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

    @PublicEndpoint
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

    @PublicEndpoint
    @GetMapping("/generar-informeGanancias")
    public ResponseEntity<?> generarExcelInformeGanancias() throws Exception {

      try {
          XSSFWorkbook workbook = servicioPrueba.crearLibro();

          ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
          workbook.write(outputStream);

          byte[] bytes = outputStream.toByteArray();

          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
          headers.setContentDispositionFormData("attachment", "informeProducto.xlsx");

          return new ResponseEntity<>(bytes, headers, org.springframework.http.HttpStatus.OK);
      }catch (Exception e){
          throw new Exception(e.getMessage());
      }
    }

}
