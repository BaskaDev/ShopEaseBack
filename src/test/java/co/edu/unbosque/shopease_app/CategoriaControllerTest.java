package co.edu.unbosque.shopease_app;



import co.edu.unbosque.shopease_app.controller.CategoriaController;
import co.edu.unbosque.shopease_app.model.CategoriaModel;
import co.edu.unbosque.shopease_app.service.CategoriaService;


import org.junit.jupiter.api.Test;


import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;


import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @Test
    public void testCrearCategoria() throws Exception {
        CategoriaModel categoria = new CategoriaModel();
        categoria.setNombre("Categoria 1");

        Mockito.when(categoriaService.saveCategoria(Mockito.any())).thenReturn(categoria);

        mockMvc.perform(post("/categoria/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Categoria 1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Se insertó la categoría"));
    }

    @Test
    public void testListarTodasCategorias_CategoriasEncontradas() throws Exception {
        // Crear categorías simuladas
        CategoriaModel categoria1 = new CategoriaModel();
        categoria1.setId_categoria(1);
        categoria1.setNombre("Categoria 1");

        CategoriaModel categoria2 = new CategoriaModel();
        categoria2.setId_categoria(2);
        categoria2.setNombre("Categoria 2");

        // Simula el comportamiento del servicio
        Mockito.when(categoriaService.findAll()).thenReturn(Arrays.asList(categoria1, categoria2));

        // Realiza la petición y verifica el resultado
        mockMvc.perform(get("/categoria/listar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Verifica que el estado sea 200 OK
                .andExpect(jsonPath("$", hasSize(2)));

    }
}