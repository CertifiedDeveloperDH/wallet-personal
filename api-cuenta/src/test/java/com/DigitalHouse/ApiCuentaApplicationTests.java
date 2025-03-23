package com.DigitalHouse;

import com.DigitalHouse.DTO.CuentaDTO;
import com.DigitalHouse.Entity.Cuenta;
import com.DigitalHouse.Service.CuentaService;
import com.DigitalHouse.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApiCuentaApplicationTests {

	CuentaService cuentaService;
	@Autowired
	public ApiCuentaApplicationTests(CuentaService usuarioService){
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
	void dBuscar() {
		Cuenta cuentaB = cuentaService.obtenerCuentaPorId(1L).orElse(null);
		assertNotNull(cuentaB);
	}

	@Test
	@Order(1)
	void eRegistrar() {
		cuentaService.crearCuenta(cuenta);

		Cuenta cuentaDTOB = cuentaService.obtenerCuentaPorId(1L).orElse(null);

		assertNotNull(cuentaDTOB);
	}
	@Test
	@Order(6)
	void aEliminar() throws ResourceNotFoundException {
		cuentaService.eliminarCuenta(1L);
		assertNull(cuentaService.obtenerCuentaPorId(1L).orElse(null));
	}

	@Test
	@Order(4)
	void bActualizar() throws ResourceNotFoundException {
		BigDecimal balanceB = new BigDecimal("2000");
		CuentaDTO cuentaDTOB= new CuentaDTO(1L, "río",balanceB , 1L);
		Cuenta cuentaB = objectMapper.convertValue(cuentaDTOB, Cuenta.class);

		assertNotNull(cuentaService.actualizarCuenta(1L, cuentaB));
	}

	@Test
	@Order(5)
	void bActualizarAlias() throws RuntimeException {
		cuentaService.actualizarAlias(1L, "río");
		Cuenta cuentaC = cuentaService.obtenerCuentaPorId(1L).orElse(null);
		assertNotNull(cuentaC);
	}

	@Test
	@Order(3)
	void cBuscarTodos() {
		assertFalse(cuentaService.listarCuentas().isEmpty());
		assertNotNull(cuentaService.listarCuentas());
	}

}
