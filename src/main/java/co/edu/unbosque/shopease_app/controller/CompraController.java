package co.edu.unbosque.shopease_app.controller;
import co.edu.unbosque.shopease_app.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = { "http://localhost:8090", "http://localhost:8080", "*" })
@RequestMapping("/compras")
public class CompraController {

 @Autowired
    private EmailService emailService;

    @PostMapping("/enviar-confirmacion")
    @Operation(summary = "Enviar Confirmación de Compra", description = "Envía un correo de confirmación de compra con los productos comprados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Correo de confirmación enviado con éxito"),
        @ApiResponse(responseCode = "500", description = "Error al enviar el correo de confirmación")
    })
    public ResponseEntity<String> enviarConfirmacionCompra(@RequestBody Map<String, Object> request) {
      
            // Obtener el email y la lista de productos del JSON
            String emailCliente = (String) request.get("emailCliente");
            List<Map<String, Object>> productosComprados = (List<Map<String, Object>>) request.get("productosComprados");

            // Construir el contenido HTML para el correo
            StringBuilder contenidoHtml = new StringBuilder();
            contenidoHtml.append("<html><body style='font-family: Arial, sans-serif; color: #333;'>");

            // Encabezado
            contenidoHtml.append("<div style='text-align: center;'>");
            contenidoHtml.append("<h1 style='color: #1E88E5;'>¡Gracias por tu compra en ShopEase!</h1>");
            contenidoHtml.append("<p style='font-size: 1.1em;'>Hola, gracias por confiar en nosotros. A continuación, encontrarás el resumen de tu compra:</p>");
            contenidoHtml.append("</div>");

            // Tabla de productos
            contenidoHtml.append("<table style='width: 100%; border-collapse: collapse; margin: 20px 0;'>");
            contenidoHtml.append("<tr style='background-color: #E3F2FD;'>");
            contenidoHtml.append("<th style='border: 1px solid #ddd; padding: 10px; color: #1565C0;'>Producto</th>");
            contenidoHtml.append("<th style='border: 1px solid #ddd; padding: 10px; color: #1565C0;'>Cantidad</th>");
            contenidoHtml.append("<th style='border: 1px solid #ddd; padding: 10px; color: #1565C0;'>Precio Unitario</th>");
            contenidoHtml.append("<th style='border: 1px solid #ddd; padding: 10px; color: #1565C0;'>Total</th>");
            contenidoHtml.append("</tr>");

            double totalCompra = 0.0;
            // Recorrer los productos comprados y llenar la tabla
        for (Map<String, Object> producto : productosComprados) {
              String nombre = (String) producto.get("nombre");
            int cantidad = (int) producto.get("cantidad");
            String precioAux = (String) producto.get("precio");
            double precio = Double.parseDouble(precioAux);
            double totalProducto = precio * cantidad;
            totalCompra += totalProducto;

                contenidoHtml.append("<tr style='text-align: center;'>");
                contenidoHtml.append("<td style='border: 1px solid #ddd; padding: 8px;'>" + nombre + "</td>");
                contenidoHtml.append("<td style='border: 1px solid #ddd; padding: 8px;'>" + cantidad + "</td>");
                contenidoHtml.append("<td style='border: 1px solid #ddd; padding: 8px;'>$" + precio + "</td>");
                contenidoHtml.append("<td style='border: 1px solid #ddd; padding: 8px;'>$" + totalProducto + "</td>");
                contenidoHtml.append("</tr>");
            }

            contenidoHtml.append("</table>");
            contenidoHtml.append("<h3 style='text-align: right; color: #333;'>Total: <span style='color: #1E88E5;'>$" + totalCompra + "</span></h3>");

            contenidoHtml.append("<p style='font-size: 1.1em;'>¡Gracias por tu compra! Esperamos verte pronto.</p>");
            contenidoHtml.append("<div style='text-align: center;'><img src='https://i.pinimg.com/originals/ea/17/93/ea1793c74a64341275ede17a21a297ad.jpg' alt='Logo ShopEase' style='width: 120px; margin-top: 20px;'/></div>");

            contenidoHtml.append("</body></html>");

            // Enviar el correo con el contenido generado
            emailService.enviarCorreoHtml(emailCliente, "Confirmación de Compra - ShopEase", contenidoHtml.toString());

            return ResponseEntity.ok("Correo de confirmación enviado con éxito");

        
    }
    
}
