package org.example.smartchef.services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.example.smartchef.dto.FavoritoDTO;
import org.example.smartchef.models.Dificultad;
import org.example.smartchef.models.Receta;
import org.example.smartchef.models.Usuario;
import org.example.smartchef.repositories.IFavoritoRepository;
import org.example.smartchef.repositories.IUsuarioRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FavoritoServiceTest {

    @Autowired
    private FavoritoService service;

    @Autowired
    private EntityManager entityManager;

    private Integer idUsuario;
    private Integer idReceta;

    @BeforeEach
    void cargarDatos() {

        Usuario usuarioTest = new Usuario();
        usuarioTest.setNombre("Usuario Test Favorito");
        usuarioTest.setEmail("testFavorito@gmail.com");
        entityManager.persist(usuarioTest);
        idUsuario = usuarioTest.getId();


        Receta recetaTest = new Receta();
        recetaTest.setNombre("Receta Test Favorito");
        recetaTest.setUsuario_creador_id(usuarioTest);
        recetaTest.setInstrucciones("Instrucciones de prueba");
        recetaTest.setDificultad(Dificultad.MEDIA);
        entityManager.persist(recetaTest);
        idReceta = recetaTest.getId();

    }

    @Test
    @DisplayName("Servicio 5 -> Caso Positivo")
    public void marcarRecetaFavoritoTest(){

        //Given

        FavoritoDTO dto = new FavoritoDTO();
        dto.setUsuario(idUsuario);
        dto.setReceta(idReceta);

        //When

        service.marcarComoFavorito(dto.getUsuario(), dto.getReceta());

        //Then
        assertNotNull(dto, "El DTO no debe ser nulo");
        assertEquals(idUsuario, dto.getUsuario(), "El usuario no coincide");
        assertEquals(idReceta, dto.getReceta(), "La receta no coincide");

    }

    @Test
    @DisplayName("Servicio 5 -> Caso Negativo")
    public void marcarRecetaFavoritoTestNegativo(){

        //Given
        service.marcarComoFavorito(idUsuario, idReceta);

        //When

        //Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.marcarComoFavorito(idUsuario, idReceta));

        assertEquals("La receta ya está marcada como favorita por este usuario.", exception.getMessage());
        System.out.println(exception.getMessage());
    }

}
