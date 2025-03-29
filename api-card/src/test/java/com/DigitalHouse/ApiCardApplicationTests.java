package com.DigitalHouse;

import com.DigitalHouse.DTO.CardDTO;
import com.DigitalHouse.Entity.Card;
import com.DigitalHouse.Entity.Cuenta;
import com.DigitalHouse.Repository.CuentaRepository;
import com.DigitalHouse.Service.CardServiceImp;
import com.DigitalHouse.Repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApiCardApplicationTests {

	@Mock
	private CardRepository cardRepository;

	@Mock
	private CuentaRepository cuentaRepository; // Simulamos el repositorio de Cuenta

	@InjectMocks
	private CardServiceImp cardService;

	private CardDTO cardDTO;
	private Card card;
	private Cuenta cuenta; // Objeto Cuenta

	@BeforeEach
	void setUp() {
		// Inicializar los mocks
		MockitoAnnotations.openMocks(this);

		// Crear un objeto Cuenta que se usará en la conversión
		cuenta = new Cuenta(new BigDecimal("1000"), 1L);

		// Crear un objeto CardDTO para las pruebas
		cardDTO = new CardDTO(1L, "1234567890", "Credito", 1L);

		// Convertir CardDTO a Card (ahora necesitamos obtener la Cuenta a partir de cuentaId)
		card = new Card(cardDTO.getId(), cardDTO.getNumero(), cardDTO.getTipo(), cuenta);
	}

	@Test
	@Order(1)
	void testCrearTarjeta() {
		// Simulamos el comportamiento del repositorio para guardar la tarjeta
		when(cardRepository.save(any(Card.class))).thenReturn(card);

		// Llamar al servicio para crear una tarjeta
		Card tarjetaCreada = cardService.crearTarjeta(card);

		// Verificar que la tarjeta fue creada correctamente
		assertNotNull(tarjetaCreada);
		assertEquals(card.getNumero(), tarjetaCreada.getNumero());
		assertEquals(card.getTipo(), tarjetaCreada.getTipo());
	}

	@Test
	@Order(2)
	void testObtenerTarjetaPorId() {
		// Simulamos el comportamiento del repositorio para obtener una tarjeta
		when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

		// Llamar al servicio para obtener la tarjeta por ID
		Optional<Card> tarjeta = cardService.obtenerTarjetaPorId(1L);

		// Verificar que la tarjeta fue obtenida correctamente
		assertTrue(tarjeta.isPresent());
		assertEquals(card.getId(), tarjeta.get().getId());
	}

	@Test
	@Order(3)
	void testActualizarTarjeta() {
		// Crear una tarjeta modificada
		Card tarjetaModificada = new Card(1L, "0987654321", "Debito", cuenta);

		// Simulamos el comportamiento del repositorio para actualizar la tarjeta
		when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
		when(cardRepository.save(any(Card.class))).thenReturn(tarjetaModificada);

		// Llamar al servicio para actualizar la tarjeta
		Optional<Card> tarjetaActualizada = cardService.actualizarTarjeta(1L, tarjetaModificada);

		// Verificar que la tarjeta fue actualizada correctamente
		assertTrue(tarjetaActualizada.isPresent());
		assertEquals("0987654321", tarjetaActualizada.get().getNumero());
		assertEquals("Debito", tarjetaActualizada.get().getTipo());
	}

	@Test
	@Order(4)
	void testEliminarTarjeta() {
		// Simulamos el comportamiento del repositorio para verificar si existe la tarjeta
		when(cardRepository.existsById(1L)).thenReturn(true);

		// Llamar al servicio para eliminar la tarjeta
		boolean eliminada = cardService.eliminarTarjeta(1L);

		// Verificar que la tarjeta fue eliminada
		assertTrue(eliminada);

		// Verificar que el repositorio fue llamado para eliminar la tarjeta
		verify(cardRepository, times(1)).deleteById(1L);
	}

	@Test
	@Order(5)
	void testEliminarTarjetaNoExistente() {
		// Simulamos el comportamiento del repositorio para que no exista la tarjeta
		when(cardRepository.existsById(1L)).thenReturn(false);

		// Llamar al servicio para intentar eliminar una tarjeta que no existe
		boolean eliminada = cardService.eliminarTarjeta(1L);

		// Verificar que la tarjeta no fue eliminada
		assertFalse(eliminada);

		// Verificar que el repositorio no intentó eliminar la tarjeta
		verify(cardRepository, never()).deleteById(1L);
	}
}
