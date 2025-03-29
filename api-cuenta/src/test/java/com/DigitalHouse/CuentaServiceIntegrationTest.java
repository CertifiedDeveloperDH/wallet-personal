package com.DigitalHouse;

import com.DigitalHouse.DTO.CuentaDTO;
import com.DigitalHouse.Entity.Cuenta;
import com.DigitalHouse.Service.CuentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.name=application-test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CuentaServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private ObjectMapper objectMapper;

    // Crear una cuenta de prueba (CuentaDTO)
    private CuentaDTO crearCuentaDTOPrueba() {
        return new CuentaDTO(1L, "usuario123", new BigDecimal("5000"), 1L);
    }

    @BeforeEach
    void doBefore() {
        // Puedes inicializar el objeto CuentaDTO aquí si es necesario para las pruebas.
    }

    @Test
    @Order(1)
    public void testCrearCuenta() throws Exception {
        CuentaDTO cuentaDTO = crearCuentaDTOPrueba();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuentaDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated()) // Código 201 Created
                .andReturn();

        assertFalse(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    @Order(2)
    public void testObtenerCuentaPorId() throws Exception {
        // Guardar cuenta en la base de datos antes de buscarla
        CuentaDTO cuentaDTO = crearCuentaDTOPrueba();
        Cuenta cuentaGuardada = cuentaService.crearCuenta(cuentaDTO).getBody(); // Guarda la cuenta

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/cuentas/1", cuentaGuardada.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Código 200 OK
                .andReturn();

        assertFalse(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    @Order(3)
    public void testActualizarCuenta() throws Exception {
        CuentaDTO cuentaDTO = crearCuentaDTOPrueba();
        Cuenta cuentaGuardada = cuentaService.crearCuenta(cuentaDTO).getBody(); // Guarda la cuenta

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
        CuentaDTO cuentaDTO = crearCuentaDTOPrueba();
        Cuenta cuentaGuardada = cuentaService.crearCuenta(cuentaDTO).getBody(); // Guarda la cuenta

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cuentas/1", cuentaGuardada.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verificar que la cuenta ha sido eliminada
        assertNull(cuentaService.obtenerCuentaPorId(cuentaGuardada.getId()).getBody());
    }
}

