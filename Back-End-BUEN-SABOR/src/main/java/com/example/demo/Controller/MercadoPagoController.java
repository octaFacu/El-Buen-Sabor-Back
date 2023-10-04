package com.example.demo.Controller;

// SDK de Mercado Pago
import com.example.demo.Entidades.Producto;
import com.example.demo.Entidades.Wrapper.NotificacionMP;
import com.example.demo.Entidades.Wrapper.ProductoParaPedidoMP;
import com.example.demo.Entidades.Wrapper.RequestDataMP;
import com.example.demo.Entidades.Wrapper.UserAuth0MP;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mercadopago.resources.merchantorder.MerchantOrderPayment;
import com.mercadopago.resources.payment.Payment;
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
                    .notificationUrl("https://5085-2803-9800-9846-68b-659a-e7cc-5977-3ba5.ngrok-free.app/mp/notificacionPago")
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

    //HACER PERSISITENCIA DEL PEDIDO DESDE CONTROLADOR MERADO PAGO, PASANDO EL ID DEL PEDIDO POR LE PATH DE notificationUrl

    @PostMapping("/notificacionPago")
    public ResponseEntity<?> notificacionDelPago(@RequestBody String notificationData) {

        System.out.println("Notificaciooooooooooon");
        System.out.println(notificationData);

        try{
            // Agrega credenciales
            MercadoPagoConfig.setAccessToken(accessToken);

            NotificacionMP notification = null;
            ObjectMapper objectMapper = new ObjectMapper();
            String topic = null;
            Long idRequest = null;

            try {
                // Convierte la cadena JSON en un objeto Java
                notification = objectMapper.readValue(notificationData, NotificacionMP.class);
                topic = notification.getTopic();

                String[] partes = notification.getResource().split("/");
                idRequest = Long.parseLong(partes[partes.length - 1]);

                //System.out.println("Resource: " + notification.getResource());
                //System.out.println("Topic: " + notification.getTopic());
            } catch (UnrecognizedPropertyException e) {
                System.out.println("Ignoro el objeto que no tiene el formato deseado");
            } catch (JsonProcessingException e) {
                System.out.println("Ignoro el objeto que no tiene el formato deseado");
            } catch (Exception e) {
                e.printStackTrace();
            }

            MerchantOrderClient moc = null;
            MerchantOrder mo = null;

            if(topic != null){
                switch (topic){
                    case "merchant_order":
                        moc = new MerchantOrderClient();

                        mo = moc.get(idRequest);

                        System.out.println("merchant order");
                        System.out.println(mo.getOrderStatus());
                        break;
                    case "payment":
                        PaymentClient pc = new PaymentClient();
                        moc = new MerchantOrderClient();

                        Payment p = pc.get(idRequest);
                        System.out.println(p.getStatus());
                        mo = moc.get(p.getOrder().getId());

                        System.out.println("payment");
                        System.out.println(mo.getOrderStatus());
                        break;
                    default:

                        break;
                }

                BigDecimal paidAmount = new BigDecimal(0);

                //Traigo la lista de los pagos
                List<MerchantOrderPayment> mop = mo.getPayments();
                //Por cada pago lo agrego al pago total
                for (MerchantOrderPayment pago : mop) {
                    if(pago.getStatus().equals("approved")){
                        System.out.println("Es aprobado");
                        paidAmount = paidAmount.add(pago.getTransactionAmount());
                    }
                }

                //Si se pago el total del monto a pagar
                if(paidAmount.compareTo(mo.getTotalAmount()) == 0 || paidAmount.compareTo(mo.getTotalAmount()) == 1){
                    System.out.println("Total a pagar " + mo.getTotalAmount().toString());
                    System.out.println("Total PAGADO " + paidAmount.toString());

                }else{
                    System.out.println("EL PAGO NO SE COMPLETÓ");
                }

            }

            //https://www.mercadopago.com.ar/developers/es/docs/checkout-pro/additional-content/your-integrations/notifications/webhooks#editor_2
            //https://github.com/mercadopago/sdk-java/blob/master/src/main/java/com/mercadopago/client/payment/PaymentClient.java
            //https://github.com/mercadopago/sdk-java/blob/fdb3724de22f63d6152671c7fa83449691c546a6/src/main/java/com/mercadopago/resources/payment/Payment.java

            return ResponseEntity.status(HttpStatus.OK).body("ok");
            //return ResponseEntity.status(HttpStatus.OK).body("{\"preferenceId\":\"" + "asdasdasd" + "\"}");

        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }

}
