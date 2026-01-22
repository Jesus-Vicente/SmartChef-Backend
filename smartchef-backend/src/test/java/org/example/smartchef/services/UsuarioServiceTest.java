package org.example.smartchef.services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Before;
import org.example.smartchef.dto.CrearUsuarioDTO;
import org.example.smartchef.dto.UsuarioDTO;
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
@AutoConfigureTestDatabase
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService service;

    @Autowired
    private EntityManager entityManager;


    @BeforeAll
    void cargarDatos(){

//        Preferencia preferencia1 = new Preferencia();
//        preferencia1.setId(1);
//        preferencia1.setNombrePreferencia("Vegetariano");
//
//        Preferencia preferencia2 = new Preferencia();
//        preferencia2.setId(2);
//        preferencia2.setNombrePreferencia("Sin Gluten");

        Usuario usuarioTest = new Usuario();
        usuarioTest.setId(1);
        usuarioTest.setNombre("Usuario Test");
        usuarioTest.setEmail("test@gmail.com");
        usuarioTest.setPassword("");
        usuarioTest.setDireccion("Calle 123");
        usuarioTest.setCiudad("Madrid");
        usuarioTest.setPais("España");
        usuarioTest.setPreferencias(new HashSet<>());

        entityManager.persist(usuarioTest);
//        entityManager.persist(preferencia1);
//        entityManager.persist(preferencia2);

    }

    @Test
    @DisplayName("Servicio 1 -> Caso Positivo")
    public void registrarUsuarioTest() {

        //Given

        //Then
        UsuarioDTO dto = service.buscarPorId(1);


        //When
        assertNotNull(dto, "El usuario que se ha intentado buscar no existe");
        assertEquals(dto.getNombre(), "Usuario Test", "El nombre del usuario no coincide con el esperado");

    }

    void buscarPorIdTest() {



    }
}
