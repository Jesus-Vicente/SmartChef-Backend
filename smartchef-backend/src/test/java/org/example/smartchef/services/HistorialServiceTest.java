package org.example.smartchef.services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.apache.catalina.LifecycleState;
import org.example.smartchef.dto.HistorialDTO;
import org.example.smartchef.dto.RegistrarHistorialDTO;
import org.example.smartchef.models.Receta;
import org.example.smartchef.models.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HistorialServiceTest {

    @Autowired
    private HistorialService service;

    @Autowired
    private EntityManager entityManager;

    private Integer idUsuario;
    private Integer idReceta;

    @BeforeEach
    void cargarDatos() {

        Usuario usuarioHistorialTest = new Usuario();
        usuarioHistorialTest.setNombre("Usuario Test Historial");
        usuarioHistorialTest.setEmail("historial@gmail.com");
        entityManager.persist(usuarioHistorialTest);
        idUsuario = usuarioHistorialTest.getId();

        Receta recetaHistorialTest = new Receta();
        recetaHistorialTest.setNombre("Receta Test Historial");
        recetaHistorialTest.setUsuario_creador_id(usuarioHistorialTest);
        recetaHistorialTest.setInstrucciones("Instrucciones de prueba");
        recetaHistorialTest.setFecha_creacion(java.time.LocalDate.now());
        entityManager.persist(recetaHistorialTest);
        idReceta = recetaHistorialTest.getId();


    }

    @Test
    @DisplayName("Servicio 7 -> Caso Positivo")
    public void registrarHistorialTest() {

        //Given
        RegistrarHistorialDTO dto = new RegistrarHistorialDTO();
        dto.setIdUsuario(idUsuario);
        dto.setIdReceta(idReceta);
        dto.setFecha_realizacion(LocalDateTime.now());

        //When
        HistorialDTO resultado = service.registrarHistorial(dto);

        //Then

        assertNotNull(resultado);
        assertEquals(idUsuario, resultado.getIdUsuario());
        assertEquals(idReceta, resultado.getIdReceta());
        assertEquals("EN PROCESO", resultado.getEstado());


    }

    @Test
    @DisplayName("Servicio 7 -> Caso Negativo")
    public void registrarHistorialTestNegativo(){

        //Given
        RegistrarHistorialDTO dto = new RegistrarHistorialDTO();
        dto.setIdUsuario(idUsuario);
        dto.setIdReceta(idReceta);
        dto.setFecha_realizacion(LocalDateTime.now().plusDays(1));

        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.registrarHistorial(dto));

        //Then
        assertEquals("No se puede registrar un historial en el futuro", exception.getMessage());
        System.out.println(exception.getMessage());

    }

    @Test
    @DisplayName("Servicio 8 -> Caso Positivo")
    public void obtenerHistorialTest() {

        //Given
        RegistrarHistorialDTO dto = new RegistrarHistorialDTO();
        dto.setIdUsuario(idUsuario);
        dto.setIdReceta(idReceta);

        service.registrarHistorial(dto);

        //When
        List<HistorialDTO> listaHistorial = service.obtenerHistorialUsuario(idUsuario);

        //Then
        assertFalse(listaHistorial.isEmpty(), "La lista deberia contener al menos un registro");
        assertEquals(idUsuario, listaHistorial.get(0).getIdUsuario());

        System.out.println(listaHistorial);

    }


    @Test
    @DisplayName("Servicio 8 -> Caso Negativo")
    public void obtenerHistorialTestNegativo() {

        //Given
        Integer idUsuarioInexistente = 10;

        //When
        List<HistorialDTO> listaHistorial = service.obtenerHistorialUsuario(idUsuarioInexistente);

        //Then
        assertNotNull(listaHistorial, "");
        assertTrue(listaHistorial.isEmpty(), "La lista deberia estar vacia para un usuario inexistente");

    }

}
