package org.example.smartchef.services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.apache.catalina.LifecycleState;
import org.aspectj.lang.annotation.Before;
import org.example.smartchef.dto.CrearUsuarioDTO;
import org.example.smartchef.dto.UsuarioDTO;
import org.example.smartchef.exception.ElementoNoEncontradoException;
import org.example.smartchef.exception.ValidacionDeNegocioException;
import org.example.smartchef.models.Preferencia;
import org.example.smartchef.models.Usuario;
import org.example.smartchef.repositories.IPreferenciaRepository;
import org.example.smartchef.repositories.IUsuarioRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase()
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService service;

    @Autowired
    private IUsuarioRepository repository;
    @Autowired
    private IPreferenciaRepository preferenciaRepository;

    @Autowired
    private EntityManager entityManager;

    private Integer idUsuarioGenerado;

    @BeforeEach
    void cargarDatos() {

        Preferencia p1 = new Preferencia();
        p1.setNombrePreferencia("Vegetariano");
        preferenciaRepository.save(p1);

        Preferencia p2 = new Preferencia();
        p2.setNombrePreferencia("Sin Gluten");
        preferenciaRepository.save(p2);

        Preferencia p3 = new Preferencia();
        p3.setNombrePreferencia("Comida Rápida");
        preferenciaRepository.save(p3);

        Preferencia p4 = new Preferencia();
        p4.setNombrePreferencia("Economica");
        preferenciaRepository.save(p4);


    }




    @Test
    @DisplayName("Servicio 1 -> Caso Positivo")
    public void crearUsuarioConPreferenciasTest() {
        //Given
        CrearUsuarioDTO usuarioTest = new CrearUsuarioDTO();
        usuarioTest.setNombre("Usuario Test");
        usuarioTest.setEmail("test@gmail.com");
        usuarioTest.setPassword("password123");
        usuarioTest.setDireccion("Calle 123");
        usuarioTest.setCiudad("Madrid");
        usuarioTest.setPais("España");

        Set<Integer> preferenciasID = preferenciaRepository.findAll().stream()
                .map(Preferencia::getId).collect(Collectors.toSet());

        usuarioTest.setPreferenciasID(preferenciasID);

        // When
        service.crearUsuarioConPreferencias(usuarioTest);


        // Then
        Usuario usuario = repository.findAll().stream()
                .filter(u -> "Usuario Test".equals(u.getNombre()))
                .findFirst().orElse(null);

        assertNotNull(usuario, "El usuario debería existir");
        assertEquals(4, usuario.getPreferencias().size(), "Debería tener las 4 preferencias asignadas");

        System.out.println(usuario);
    }

    @Test
    @DisplayName("Servicio 1 -> Caso Negativo")
    public void crearUsuarioConPreferenciasTestNegativo() {
        //Given
        CrearUsuarioDTO dtoError = new CrearUsuarioDTO();
        dtoError.setNombre("Usuario Error");
        dtoError.setPreferenciasID(Set.of(1,2,3,4,5));

        //Then

        //When
        assertThrows(ValidacionDeNegocioException.class, () -> service.crearUsuarioConPreferencias(dtoError));
    }

}
