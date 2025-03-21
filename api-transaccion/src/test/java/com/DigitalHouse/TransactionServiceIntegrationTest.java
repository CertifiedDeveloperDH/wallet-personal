package com.DigitalHouse;

import com.DigitalHouse.DTO.CuentaDTO;
import com.DigitalHouse.DTO.TransaccionDTO;
import com.DigitalHouse.Entity.Cuenta;
import com.DigitalHouse.service.TransaccionService;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.config.name=application-test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransactionServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TransaccionService transaccionService;
    @Autowired
    private ObjectMapper objectMapper;

    BigDecimal monto = new BigDecimal("1000");
    BigDecimal balance = new BigDecimal("10000");
    ObjectMapper objectMapperB = new ObjectMapper(); // Inicializar ObjectMapper antes de cada prueba
    Cuenta cuenta = new Cuenta(1L, "mar", balance, 1L);
    CuentaDTO cuentaDTO = objectMapperB.convertValue(cuenta, CuentaDTO .class);
    TransaccionDTO transaccionDTO = new TransaccionDTO(1L, cuenta,"Egreso" , monto);

    @Test
    @Order(1)
    public void testRegistrarTransaccion() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/transaccion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaccionDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertFalse(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    @Order(2)
    public void testObtenerTransaccionPorId() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/transaccion/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertFalse(result.getResponse().getContentAsString().isEmpty());
    }
}
