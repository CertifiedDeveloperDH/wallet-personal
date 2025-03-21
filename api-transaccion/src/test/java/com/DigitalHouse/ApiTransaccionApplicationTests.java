package com.DigitalHouse;

import com.DigitalHouse.DTO.CuentaDTO;
import com.DigitalHouse.DTO.TransaccionDTO;
import com.DigitalHouse.Entity.Cuenta;
import com.DigitalHouse.Service.CuentaService;
import com.DigitalHouse.entity.Transaccion;
import com.DigitalHouse.exception.ResourceNotFoundException;
import com.DigitalHouse.service.TransaccionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ApiTransaccionApplicationTests {
	TransaccionService transaccionService;
	@Autowired
	public ApiTransaccionApplicationTests(TransaccionService transaccionService){
		this.transaccionService = transaccionService;
	}
	Cuenta cuenta;
	CuentaDTO cuentaDTO;
	private ObjectMapper objectMapper;
	@BeforeEach
	void doBefore(){
		BigDecimal monto = new BigDecimal("1000");
		BigDecimal balance = new BigDecimal("10000");
		ObjectMapper objectMapper = new ObjectMapper(); // Inicializar ObjectMapper antes de cada prueba
		cuenta = new Cuenta(1L, "mar", balance, 1L);
		cuentaDTO = objectMapper.convertValue(cuenta, CuentaDTO.class);
		TransaccionDTO transaccionDTO = new TransaccionDTO(1L, cuenta,"Egreso" , monto);
	}

	@Test
	@Order(1)
	void eRegistrar() {
		BigDecimal monto = new BigDecimal("1000");
		transaccionService.registrarTransaccion(cuentaDTO.getId(),"Ingreso", monto);

		Transaccion transaccionDTOB = transaccionService.obtenerTransaccionPorTipo(1L, "Ingreso").orElse(null);

		assertNotNull(transaccionDTOB);
	}

	@Test
	@Order(2)
	void cBuscarPorTipo() {
		Transaccion transaccionDTOB = transaccionService.obtenerTransaccionPorTipo(1L, "Ingreso").orElse(null);
		assertNotNull(transaccionDTOB);
	}

}
