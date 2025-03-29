package com.DigitalHouse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.DigitalHouse.DTO.CardDTO;
import com.DigitalHouse.Entity.Card;
import com.DigitalHouse.Entity.Cuenta;
import com.DigitalHouse.Repository.CardRepository;
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
public class CardServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // Crear una cuenta de prueba (Cuenta debe existir antes de crear una tarjeta)
    private Cuenta crearCuentaPrueba() {
        Cuenta cuenta = new Cuenta(new BigDecimal("1000"), 1L);
        return cuentaRepository.save(cuenta);  // Guardar la cuenta antes de usarla
    }

    // Crear una tarjeta de prueba usando CardDTO
    private CardDTO crearCardDTO(Long cuentaId) {
        return new CardDTO(null, "1234567890", "Credito", cuentaId);
    }

    @Test
    @Order(1)
    public void testCrearCard() throws Exception {
        // Primero, crear una cuenta de prueba para asociarla con la tarjeta
        Cuenta cuenta = crearCuentaPrueba();

        // Crear un CardDTO que representa los datos de la tarjeta
        CardDTO cardDTO = crearCardDTO(cuenta.getId());

        // Llamar al API para crear la tarjeta
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())  // Código 201 Created
                .andReturn();

        // Asegurarnos de que el cuerpo de la respuesta no está vacío
        assertFalse(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    @Order(2)
    public void testObtenerCardPorId() throws Exception {
        // Primero, crear una cuenta de prueba y una tarjeta asociada
        Cuenta cuenta = crearCuentaPrueba();
        CardDTO cardDTO = crearCardDTO(cuenta.getId());
        Card cardGuardada = cardRepository.save(new Card(cardDTO.getId(), cardDTO.getNumero(), cardDTO.getTipo(), cuenta));

        // Llamar al API para obtener la tarjeta por su ID
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/cards/{id}", cardGuardada.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())  // Código 200 OK
                .andReturn();

        // Asegurarnos de que el cuerpo de la respuesta no esté vacío
        assertFalse(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    @Order(3)
    public void testActualizarCard() throws Exception {
        // Crear una cuenta de prueba y guardar la tarjeta
        Cuenta cuenta = crearCuentaPrueba();
        CardDTO cardDTO = crearCardDTO(cuenta.getId());
        Card cardGuardada = cardRepository.save(new Card(cardDTO.getId(), cardDTO.getNumero(), cardDTO.getTipo(), cuenta));

        // Modificar los datos de la tarjeta
        cardGuardada.setNumero("0987654321");
        cardGuardada.setTipo("Debito");

        // Llamar al API para actualizar la tarjeta
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/cards/{id}", cardGuardada.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardGuardada)))
                .andExpect(MockMvcResultMatchers.status().isOk())  // Código 200 OK
                .andReturn();

        // Asegurarnos de que el cuerpo de la respuesta no esté vacío
        assertFalse(result.getResponse().getContentAsString().isEmpty());
    }

    @Test
    @Order(4)
    public void testEliminarCard() throws Exception {
        // Crear una cuenta de prueba y guardar la tarjeta
        Cuenta cuenta = crearCuentaPrueba();
        CardDTO cardDTO = crearCardDTO(cuenta.getId());
        Card cardGuardada = cardRepository.save(new Card(cardDTO.getId(), cardDTO.getNumero(), cardDTO.getTipo(), cuenta));

        // Llamar al API para eliminar la tarjeta
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cards/{id}", cardGuardada.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Verificar que la tarjeta ha sido eliminada
        Optional<Card> tarjetaEliminada = cardRepository.findById(cardGuardada.getId());
        assertTrue(tarjetaEliminada.isEmpty());
    }
}
