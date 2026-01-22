package org.example.smartchef.services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.example.smartchef.models.Dificultad;
import org.example.smartchef.models.Receta;
import org.example.smartchef.models.Usuario;
import org.example.smartchef.repositories.IFavoritoRepository;
import org.example.smartchef.repositories.IUsuarioRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
    private FavoritoService favoritoService;

    @Autowired
    private IFavoritoRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UsuarioService usuarioService;


    private Integer idUsuario;
    private Integer idReceta;

    @BeforeAll
    void cargarDatos(){

        Usuario usuarioTestFavorito = new Usuario();
        usuarioTestFavorito.setNombre("Usuario Test Favoritos");
        usuarioTestFavorito.setEmail("usuario@gmail.com");
        usuarioTestFavorito.setPreferencias(new HashSet<>());
        usuarioTestFavorito.setFavoritos(new HashSet<>());
        usuarioTestFavorito.setId(idUsuario);

        Receta recetaTestFavorito = new Receta();
        recetaTestFavorito.setNombre("Tortilla Test");
        recetaTestFavorito.setDescripcion("Receta Test");
        recetaTestFavorito.setDificultad(Dificultad.ALTA);
        recetaTestFavorito.setId(idReceta);

        entityManager.persist(usuarioTestFavorito);
        entityManager.persist(recetaTestFavorito);

    }

    @Test
    @DisplayName("Servicio 5 -> Caso Positivo")
    public void marcarComoFavoritoTest(){

        //Given

        //Then
        favoritoService.marcarComoFavorito(idUsuario, idReceta);

        //When
        Usuario usuarioMarcado = entityManager.find(Usuario.class, idUsuario);

        boolean esFavorita = usuarioMarcado.getFavoritos().stream()
                .anyMatch(recetaFavorita -> recetaFavorita.getReceta().getId().equals(idReceta));

        assertTrue(esFavorita, "La receta con id: " + idReceta + " ha sido agregada como favorita al usuario con id: " + idUsuario);

    }

//    @Test
//    @DisplayName("Servicio 5 -> Caso Negativo")
//    public void marcarComoFavoritoTestNegativo(){
//        service.marcarComoFavorito(idUsuario, idReceta);
//    }

}
