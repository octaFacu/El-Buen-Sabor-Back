package com.example.demo.Controller;

// SDK de Mercado Pago
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.Wrapper.ProductoParaPedidoMP;
import com.example.demo.Entidades.Wrapper.RequestDataMP;
import com.example.demo.Entidades.Wrapper.UserAuth0MP;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.common.Identification;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.payment.PaymentPayer;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    private String redirectUrl = "http://localhost:5173";

    //Credencial de testeo
    //@Value("${MP_TEST_ACCESS_TOKEN}")
    //private String accessToken;

    @PostMapping("/checkout")
    public ResponseEntity<?> crearCheckout(@RequestBody RequestDataMP requestData) {
        //System.out.println("Entro al controlador");
        try{

            // Agrega credenciales
            MercadoPagoConfig.setAccessToken(accessToken);

            //Datos del RequestBody
            UserAuth0MP usuario = requestData.getUsuario();
            List<ProductoParaPedidoMP> productos = requestData.getProductos();

            //Lista de items cargados acá
            List<PreferenceItemRequest> items = new ArrayList<>();

            for (int i = 0; i < productos.size(); i++){

                Producto prod = productos.get(i).getProducto();

                if(!requestData.getEsEnvio()){
                    prod.setPrecioTotal(prod.getPrecioTotal() * 0.9);
                }

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
                    .success(redirectUrl + "/success")
                    .pending(redirectUrl + "/pending")
                    .failure(redirectUrl + "/failure")
                    .build();

            //Objeto con toda la informacion del pago
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .binaryMode(true)
                    .payer(payer)
                    .backUrls(backUrls)
                    .autoReturn("approved")
                    .marketplace("El Buen Sabor")
                    .notificationUrl("https://8437-2803-9800-9846-68b-a5d4-18c4-37a6-21bd.ngrok-free.app/mp/notificacionPago")
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);


            //return preference.getId();
            ///System.out.println("PreferenceId: " + preference.getId());
            return ResponseEntity.status(HttpStatus.OK).body("{\"preferenceId\":\"" + preference.getId() + "\"}");

        }catch(MPException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }catch(MPApiException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


    @PostMapping("/notificacionPago")
    public ResponseEntity<?> notificacionDelPago(@RequestBody String notificationData) {

        System.out.println("Notificaciooooooooooon");
        System.out.println(notificationData);


        try{
            // Agrega credenciales
            MercadoPagoConfig.setAccessToken(accessToken);

            //https://www.mercadopago.com.ar/developers/es/docs/checkout-pro/additional-content/your-integrations/notifications/webhooks#editor_2
            //https://github.com/mercadopago/sdk-java/blob/master/src/main/java/com/mercadopago/client/payment/PaymentClient.java
            //https://github.com/mercadopago/sdk-java/blob/fdb3724de22f63d6152671c7fa83449691c546a6/src/main/java/com/mercadopago/resources/payment/Payment.java
            PaymentClient pc = new PaymentClient();

            System.out.println(pc.get(64550388878l));




            return ResponseEntity.status(HttpStatus.OK).body("ok");
            //return ResponseEntity.status(HttpStatus.OK).body("{\"preferenceId\":\"" + "asdasdasd" + "\"}");

        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }

}
