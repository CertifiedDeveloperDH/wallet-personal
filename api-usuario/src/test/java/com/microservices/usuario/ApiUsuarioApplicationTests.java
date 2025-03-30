package com.microservices.usuario;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.usuario.dto.ObjectMapperConfig;
import com.microservices.usuario.dto.UsuarioDTO;
import com.microservices.usuario.entity.Usuario;
import com.microservices.usuario.exception.ResourceNotFoundException;
import com.microservices.usuario.service.UsuarioService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiUsuarioApplicationTests {
	UsuarioService usuarioService;
	@Autowired
	public ApiUsuarioApplicationTests(UsuarioService usuarioService){
		this.usuarioService = usuarioService;
	}

	UsuarioDTO usuarioDTO;
	Usuario usuario;
	private ObjectMapper objectMapper;
	@BeforeEach
	void doBefore(){
		ObjectMapper objectMapper = new ObjectMapper(); // Inicializar ObjectMapper antes de cada prueba
		UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "Juan", "Pérez", "juan@example.com", "123456");
		Usuario usuario = objectMapper.convertValue(usuarioDTO, Usuario.class);
	}
	@Test
	@Order(2)
	void dBuscar() {
		Usuario usuarioB = usuarioService.obtenerUsuarioPorId(1L).orElse(null);
		assertNotNull(usuarioB);
	}

	@Test
	@Order(1)
	void eRegistrar() {
		usuarioService.crearUsuario(usuario);

		Usuario usuarioDTOB = usuarioService.obtenerUsuarioPorId(1L).orElse(null);

		assertNotNull(usuarioDTOB);
	}

	@Test
	@Order(5)
	void aEliminar() throws ResourceNotFoundException {
		usuarioService.eliminarUsuario(1L);
		assertNull(usuarioService.obtenerUsuarioPorId(1L).orElse(null));
	}

	@Test
	@Order(4)
	void bActualizar() throws ResourceNotFoundException {
		UsuarioDTO usuarioDTOB= new UsuarioDTO(1L, "Juan_cambiado", "Pérez_cambiado", "juan@example.com_cambiado", "123456");
		Usuario usuarioB = objectMapper.convertValue(usuarioDTOB, Usuario.class);

		assertNotNull(usuarioService.actualizarUsuario(1L, usuarioB));
	}

	@Test
	@Order(3)
	void cBuscarTodos() {
		assertFalse(usuarioService.listarUsuarios().isEmpty());
		assertNotNull(usuarioService.listarUsuarios());
	}

}
