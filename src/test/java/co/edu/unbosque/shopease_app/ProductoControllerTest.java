package co.edu.unbosque.shopease_app;

import co.edu.unbosque.shopease_app.controller.ProductoController;
import co.edu.unbosque.shopease_app.model.ProductoModel;
import co.edu.unbosque.shopease_app.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Test
    public void testCrearProducto() throws Exception {
        ProductoModel producto = new ProductoModel();
        producto.setNombre("Jabon rey");

        // Simula el comportamiento del servicio
        Mockito.when(productoService.saveProducto(any())).thenReturn(producto);

        mockMvc.perform(post("/producto/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Producto 1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Se insertó el producto"));
    }

    @Test
    public void testListarTodosProductos_ProductosEncontrados() throws Exception {
        // Crear productos simulados
        ProductoModel producto1 = new ProductoModel();
        producto1.setId_producto(1);
        producto1.setNombre("Jabon rey");

        ProductoModel producto2 = new ProductoModel();
        producto2.setId_producto(2);
        producto2.setNombre("Papas de pollo");

        // Simula el comportamiento del servicio
        Mockito.when(productoService.findAll()).thenReturn(Arrays.asList(producto1, producto2));

        // Realiza la petición y verifica el resultado
        mockMvc.perform(get("/producto/listar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testListarTodosProductos_ProductosNoEncontrados() throws Exception {
        // Simula el comportamiento del servicio devolviendo una lista vacía
        Mockito.when(productoService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/producto/listar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testActualizarProducto_ProductoExistente() throws Exception {
        ProductoModel productoActualizado = new ProductoModel();
        productoActualizado.setId_producto(1);
        productoActualizado.setNombre("Producto Actualizado");

        // Simula el comportamiento del servicio
        Mockito.when(productoService.updateProducto(eq(1), any(ProductoModel.class)))
                .thenReturn(productoActualizado);

        mockMvc.perform(put("/producto/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Producto Actualizado\"}"))
                .andExpect(status().isOk());

    }

    @Test
    public void testActualizarProducto_ProductoNoExistente() throws Exception {
        // Simula que el producto no existe
        Mockito.when(productoService.updateProducto(eq(1), any(ProductoModel.class)))
                .thenReturn(null);

        mockMvc.perform(put("/producto/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Producto No Existente\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testEliminarProducto_ProductoExistente() throws Exception {
        // Simula el comportamiento del servicio
        Mockito.when(productoService.deleteProducto(1)).thenReturn(true);

        mockMvc.perform(delete("/producto/eliminar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Producto eliminada exitosamente."));
    }

    @Test
    public void testEliminarProducto_ProductoNoExistente() throws Exception {
        // Simula que el producto no existe
        Mockito.when(productoService.deleteProducto(1)).thenReturn(false);

        mockMvc.perform(delete("/producto/eliminar/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Producto no encontrada."));
    }
}