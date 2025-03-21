package com.DigitalHouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.DigitalHouse.Entity.Cuenta;
import com.DigitalHouse.Repository.CuentaRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.name=application-test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CuentaServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // Crear una cuenta de prueba
    private Cuenta crearCuentaPrueba() {
        return new Cuenta(null, "usuario123", new BigDecimal("5000"), 1L);
    }

    @Test
    @Order(1)
    public void testCrearCuenta() throws Exception {
        Cuenta cuenta = crearCuentaPrueba();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuenta)))
                .andExpect(MockMvcResultMatchers.status().isCreated()) // Código 201 Created
                .andReturn();

        assertFalse(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    @Order(2)
    public void testObtenerCuentaPorId() throws Exception {
        // Guardar cuenta en la base de datos antes de buscarla
        Cuenta cuentaGuardada = cuentaRepository.save(crearCuentaPrueba());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/cuentas/1", cuentaGuardada.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Código 200 OK
                .andReturn();

        assertFalse(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    @Order(3)
    public void testActualizarCuenta() throws Exception {
        Cuenta cuentaGuardada = cuentaRepository.save(crearCuentaPrueba());

        // Modificar datos
        cuentaGuardada.setAlias("nuevoAlias");
        cuentaGuardada.setBalance(new BigDecimal("7000"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/cuentas/1", cuentaGuardada.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuentaGuardada)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertFalse(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    @Order(4)
    public void testEliminarCuenta() throws Exception {
        Cuenta cuentaGuardada = cuentaRepository.save(crearCuentaPrueba());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cuentas/1", cuentaGuardada.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verificar que la cuenta ha sido eliminada
        Optional<Cuenta> cuentaEliminada = cuentaRepository.findById(cuentaGuardada.getId());
        assertTrue(cuentaEliminada.isEmpty());
    }
}
