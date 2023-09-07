package com.example.demo.Controller;

// SDK de Mercado Pago
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.Wrapper.ProductoParaPedido;
import com.example.demo.Entidades.Wrapper.RequestDataMP;
import com.example.demo.Entidades.Wrapper.UserAuth0;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/mp")
public class MercadoPagoController {

    // Configura tu token de acceso de Mercado Pago en application.properties o application.yml
    @Value("${MP_ACCESS_TOKEN}")
    private String accessToken;

    //Credencial de testeo
    //@Value("${MP_TEST_ACCESS_TOKEN}")
    //private String accessToken;

    @PostMapping("/checkout")
    public ResponseEntity<?> crearCheckout(@RequestBody RequestDataMP requestData) {
        System.out.println("Entro al controlador");
        try{

            // Agrega credenciales
            MercadoPagoConfig.setAccessToken(accessToken);

            //Datos del RequestBody
            UserAuth0 usuario = requestData.getUsuario();
            List<ProductoParaPedido> productos = requestData.getProductos();

            //System.out.println("USUARIO: " + usuario.getNombre());

            //Lista de items cargados acá
            List<PreferenceItemRequest> items = new ArrayList<>();

            System.out.println("Entro al bucle");
            for (int i = 0; i < productos.size(); i++){

                System.out.println("Ejecuto: " + i);

                Producto prod = productos.get(i).getProducto();

                System.out.println(prod.getDenominacion());

                PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                        .id(prod.getId().toString())
                        .title(prod.getDenominacion())
                        .description(prod.getDescripcion())
                        .pictureUrl(prod.getImagen())
                        .categoryId(prod.getCategoriaProducto().getId().toString())
                        .quantity(productos.get(i).getCantidad())
                        .currencyId("ARS")
                        .unitPrice(new BigDecimal(prod.getPrecioTotal().toString()))
                        .build();

                //Agrego el item ya creado a la lista final
                items.add(itemRequest);
            }

            //Datos de la persona que esta pagando
            PreferencePayerRequest payer = PreferencePayerRequest.builder()
                    .name(usuario.getNombre())
                    .surname(usuario.getApellido())
                    .email(usuario.getEmail())
                    .build();

            //Paganias a las que se redireccionará segun que caso
            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:5173/")
                    .pending("http://localhost:5173/carrito")
                    .failure("http://localhost:5173/usuarios/")
                    .build();

            //Objeto con toda la informacion del pago
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .binaryMode(true)
                    .payer(payer)
                    .backUrls(backUrls)
                    .autoReturn("approved")
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            //return preference.getId();
            System.out.println("PreferenceId: " + preference.getId());
            //return ResponseEntity.status(HttpStatus.OK).body(preference.getId());
            return ResponseEntity.status(HttpStatus.OK).body("{\"preferenceId\":\"" + preference.getId() + "\"}");

        }catch(MPException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }catch(MPApiException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
