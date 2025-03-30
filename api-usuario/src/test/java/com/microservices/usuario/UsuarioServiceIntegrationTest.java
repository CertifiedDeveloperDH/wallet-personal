package com.microservices.usuario;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.usuario.dto.UsuarioDTO;
import com.microservices.usuario.entity.Usuario;
import com.microservices.usuario.service.UsuarioService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioService usuarioService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Usuario crearUsuarioTest() {
        // Crear UsuarioDTO para probar la conversión
        UsuarioDTO usuarioDTO = new UsuarioDTO(null, "Juan", "Perez", "juan.perez@test.com", "123456");

        // Convertir UsuarioDTO a Usuario
        Usuario usuario = objectMapper.convertValue(usuarioDTO, Usuario.class);

        // Llamar al servicio para guardar el usuario
        return usuarioService.crearUsuario(usuario);
    }

    @Test
    @Order(1)
    public void testCrearUsuario() throws Exception {
        // Crear un UsuarioDTO para crear un usuario
        UsuarioDTO usuarioDTO = new UsuarioDTO(null, "Juan", "Perez", "juan.perez@test.com", "123456");

        // Convertir UsuarioDTO a Usuario
        Usuario usuario = objectMapper.convertValue(usuarioDTO, Usuario.class);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        // Verificar que la respuesta contiene los datos del usuario creado
        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    @Order(2)
    public void testObtenerUsuarioPorId() throws Exception {
        // Crear un usuario de prueba
        Usuario usuarioCreado = crearUsuarioTest();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/usuarios/1", usuarioCreado.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Verificar que la respuesta contiene los detalles del usuario
        assertNotNull(result.getResponse().getContentAsString());

        // Verificar que el nombre del usuario en la respuesta es correcto
        assertEquals("Juan", objectMapper.readTree(result.getResponse().getContentAsString()).get("nombre").asText());
    }

    @Test
    @Order(3)
    public void testActualizarUsuario() throws Exception {
        // Crear un usuario
        Usuario usuarioCreado = crearUsuarioTest();

        // Modificar usuario
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioCreado.getId(), "Juan Carlos", "Perez Actualizado", "juan.carlos@test.com", "123456");

        // Convertir UsuarioDTO a Usuario
        Usuario usuarioActualizado = objectMapper.convertValue(usuarioDTO, Usuario.class);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/usuarios/1", usuarioCreado.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Verificar que el nombre y apellido fueron actualizados
        assertEquals("Juan Carlos", objectMapper.readTree(result.getResponse().getContentAsString()).get("nombre").asText());
        assertEquals("Perez Actualizado", objectMapper.readTree(result.getResponse().getContentAsString()).get("apellido").asText());
    }

    @Test
    @Order(5)
    public void testEliminarUsuario() throws Exception {
        // Crear un usuario
        Usuario usuarioCreado = crearUsuarioTest();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/usuarios/1", usuarioCreado.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();

        // Verificar que el usuario fue eliminado
        Optional<Usuario> usuarioEliminado = usuarioService.obtenerUsuarioPorId(usuarioCreado.getId());
        assertEquals(Optional.empty(), usuarioEliminado);
    }

    @Test
    @Order(4)
    public void testListarUsuarios() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/usuarios")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Verificar que la lista de usuarios no esté vacía
        assertNotNull(result.getResponse().getContentAsString());
    }
}
