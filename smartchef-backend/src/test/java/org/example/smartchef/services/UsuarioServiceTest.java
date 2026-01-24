package org.example.smartchef.services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Before;
import org.example.smartchef.dto.CrearUsuarioDTO;
import org.example.smartchef.dto.UsuarioDTO;
import org.example.smartchef.exception.ElementoNoEncontradoException;
import org.example.smartchef.models.Preferencia;
import org.example.smartchef.models.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase()
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService service;

    @Autowired
    private EntityManager entityManager;

    private Integer idUsuarioGenerado;

    @BeforeEach
    void cargarDatos() {
        Usuario usuarioTest = new Usuario();
        usuarioTest.setNombre("Usuario Test");
        usuarioTest.setEmail("test@gmail.com");
        usuarioTest.setPassword("password123");
        usuarioTest.setDireccion("Calle 123");
        usuarioTest.setCiudad("Madrid");
        usuarioTest.setPais("España");
        usuarioTest.setPreferencias(new HashSet<>());
        usuarioTest.setFavoritos(new HashSet<>());
        usuarioTest.setFoto(null);

        entityManager.persist(usuarioTest);

        idUsuarioGenerado = usuarioTest.getId();

    }




    @Test
    @DisplayName("Servicio 1 -> Caso Positivo")
    public void buscarPorIdTest() {
        //Given
        //Previos

        // Then
        UsuarioDTO dto = service.buscarPorId(1);


        // When
        // Comprobaciones
        assertNotNull(dto, "El usuario con ID " + idUsuarioGenerado + " debería existir");
        assertEquals(dto.getNombre(), "Usuario Test", "El nombre del usuario no coincide");
        assertEquals(dto.getEmail(),"test@gmail.com", "El email del usuario no coincide");

        System.out.println(dto);
    }

    @Test
    @DisplayName("Servicio 1 -> Caso Negativo")
    public void buscarPorIdTestNegativo() {
        //Given

        //Then

        //When
        assertThrows(ElementoNoEncontradoException.class, () -> service.buscarPorId(3));
    }

}
