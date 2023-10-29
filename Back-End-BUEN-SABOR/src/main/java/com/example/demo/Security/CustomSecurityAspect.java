package com.example.demo.Security;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.nio.file.AccessDeniedException;

@Aspect
@Component
public class CustomSecurityAspect {

    @Value("${rolCliente}")
    private String cliente;

    @Value("${rolDelivery}")
    private String delivery;

    @Value("${rolCocinero}")
    private String cocinero;

    @Value("${rolAdmin}")
    private String admin;

    @Value("${rolCajero}")
    private String cajero;

    @Before("@annotation(publicEndpoint)")
    public void checkPublicAccess(JoinPoint joinPoint, PublicEndpoint publicEndpoint) {

    }

    @Before("@annotation(adminOnly)")
    public void checkAdminAccess(JoinPoint joinPoint, AdminOnly adminOnly) throws AccessDeniedException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String role = request.getHeader("X-Role");

        if (!admin.equals(role)) {
            throw new AccessDeniedException("Acceso denegado.");
        }
    }

    @Before("@annotation(cocineroOnly)")
    public void checkCocineroAccess(JoinPoint joinPoint, CocineroOnly cocineroOnly) throws AccessDeniedException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String role = request.getHeader("X-Role");

        if (!cocinero.equals(role) && !admin.equals(role)) {
            throw new AccessDeniedException("Acceso denegado.");
        }
    }

    @Before("@annotation(deliveryOnly)")
    public void checkDeliveryAccess(JoinPoint joinPoint, DeliveryOnly deliveryOnly) throws AccessDeniedException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String role = request.getHeader("X-Role");

        if (!delivery.equals(role) && !admin.equals(role)) {
            throw new AccessDeniedException("Acceso denegado.");
        }
    }

    @Before("@annotation(cajeroOnly)")
    public void checkCajeroAccess(JoinPoint joinPoint, CajeroOnly cajeroOnly) throws AccessDeniedException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String role = request.getHeader("X-Role");

        if (!cajero.equals(role) && !admin.equals(role)) {
            throw new AccessDeniedException("Acceso denegado.");
        }
    }

    @Before("@annotation(clienteOnly)")
    public void checkClienteAccess(JoinPoint joinPoint, ClienteOnly clienteOnly) throws AccessDeniedException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String role = request.getHeader("X-Role");

        if (!cliente.equals(role) && !admin.equals(role)) {
            throw new AccessDeniedException("Acceso denegado.");
        }
    }
}
