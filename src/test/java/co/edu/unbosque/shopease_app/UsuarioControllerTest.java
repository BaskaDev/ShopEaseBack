package co.edu.unbosque.shopease_app;

import co.edu.unbosque.shopease_app.controller.ProductoController;
import co.edu.unbosque.shopease_app.controller.UsuarioController;
import co.edu.unbosque.shopease_app.controller.UsuarioLoginRequest;
import co.edu.unbosque.shopease_app.model.UsuarioModel;
import co.edu.unbosque.shopease_app.service.CodigoService;
import co.edu.unbosque.shopease_app.service.EmailService;
import co.edu.unbosque.shopease_app.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private EmailService emailService;

    @MockBean
    private CodigoService codigoService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    // Prueba para registrar un usuario
    @Test
    public void testRegistrarUsuario() throws Exception {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNombre("Juan");
        usuario.setEmail("santiagomelo3@icloud.com");
        usuario.setContraseña("prueba1234*");

        when(passwordEncoder.encode(anyString())).thenReturn("hashed_password");
        Mockito.doNothing().when(emailService).enviarCorreo(anyString(), anyString(), anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/usuario/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario guardado con éxito"));
    }


    // Prueba para iniciar sesión
    @Test
    public void testIniciarSesion() throws Exception {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setEmail("santiagomelo3@icloud.com");
        usuario.setContraseña("hashed_password");

        UsuarioLoginRequest loginRequest = new UsuarioLoginRequest();
        loginRequest.setEmail("santiagomelo3@icloud.com");
        loginRequest.setContraseña("prueba1234*");

        when(usuarioService.findByEmail("santiagomelo3@icloud.com")).thenReturn(usuario);
        when(passwordEncoder.matches("prueba1234*", "hashed_password")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/usuario/iniciarSesion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Inicio de sesión exitoso"));
    }


}