package com.DigitalHouse;

import com.DigitalHouse.DTO.CardDTO;
import com.DigitalHouse.DTO.CuentaDTO;
import com.DigitalHouse.Entity.Card;
import com.DigitalHouse.Entity.Cuenta;
import com.DigitalHouse.Service.CuentaService;
import com.DigitalHouse.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApiCuentaApplicationTests {

	CuentaService cuentaService;
	@Autowired
	public ApiCuentaApplicationTests(CuentaService cuentaService){
		this.cuentaService = cuentaService;
	}


	CuentaDTO cuentaDTO;
	Cuenta cuenta;
	private ObjectMapper objectMapper;
	@BeforeEach
	void doBefore(){
		BigDecimal balance = new BigDecimal("1000");
		ObjectMapper objectMapper = new ObjectMapper(); // Inicializar ObjectMapper antes de cada prueba
		CuentaDTO cuentaDTO = new CuentaDTO(1L, "mar",balance , 1L);
		Cuenta cuenta = objectMapper.convertValue(cuentaDTO, Cuenta.class);
	}
	@Test
	@Order(2)
	@WithMockUser(username = "admin", roles = "USER")
	void dBuscar() {
		ResponseEntity<Cuenta> response = cuentaService.obtenerCuentaPorId(1L);
		Cuenta cuentaB = response.getBody();

		assertNotNull(cuentaB);
	}


	@Test
	@Order(1)
	@WithMockUser(username = "admin", roles = "USER")
	void eRegistrar() {

		// Llamar al servicio para crear la cuenta
		ResponseEntity<Cuenta> response = cuentaService.crearCuenta(cuentaDTO);
		Cuenta cuentaCreada = response.getBody();

		// Asegúrate de que la cuenta fue creada correctamente
		assertNotNull(cuentaCreada);
		assertEquals(cuenta.getAlias(), cuentaCreada.getAlias()); // Ahora deberías comparar el alias de Cuenta
	}



	@Test
	@Order(6)
	@WithMockUser(username = "admin", roles = "USER")
	void aEliminar() throws ResourceNotFoundException {
		ResponseEntity<Void> response = cuentaService.eliminarCuenta(1L);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); // Verifica el código de estado 204
		assertNull(cuentaService.obtenerCuentaPorId(1L).getBody());  // Verifica que la cuenta fue eliminada
	}



	@Test
	@Order(4)
	@WithMockUser(username = "admin", roles = "USER")
	void bActualizar() throws ResourceNotFoundException {
		BigDecimal balanceB = new BigDecimal("2000");
		CuentaDTO cuentaDTOB = new CuentaDTO(1L, "río", balanceB, 1L);

		ResponseEntity<Cuenta> response = cuentaService.actualizarCuenta(1L, cuentaDTOB);

		Cuenta cuentaActualizada = response.getBody();
		assertNotNull(cuentaActualizada);
		assertEquals("río", cuentaActualizada.getAlias());  // Asegúrate de verificar los cambios
	}

	@Test
	@Order(5)
	@WithMockUser(username = "admin", roles = "USER")
	void bActualizarAlias() {
		cuentaService.actualizarAlias(1L, "río");

		ResponseEntity<Cuenta> response = cuentaService.obtenerCuentaPorId(1L);
		Cuenta cuentaC = response.getBody();

		assertNotNull(cuentaC);
		assertEquals("río", cuentaC.getAlias()); // Verifica que el alias haya sido actualizado
	}

	@Test
	@Order(3)
	@WithMockUser(username = "admin", roles = "USER")
	void cBuscarTodos() {
		ResponseEntity<List<Cuenta>> response = cuentaService.listarCuentas();
		List<Cuenta> cuentas = response.getBody();
		assertNotNull(cuentas);
		assertFalse(cuentas.isEmpty());
	}
}
