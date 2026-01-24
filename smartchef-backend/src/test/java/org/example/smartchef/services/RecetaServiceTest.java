package org.example.smartchef.services;


import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.example.smartchef.dto.*;
import org.example.smartchef.models.Dificultad;
import org.example.smartchef.models.Ingrediente;
import org.example.smartchef.models.Receta;
import org.example.smartchef.models.Usuario;
import org.example.smartchef.repositories.IRecetaRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.plugin.core.OrderAwarePluginRegistry;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RecetaServiceTest {

    @Autowired
    private RecetaService service;

    @Autowired
    private EntityManager entityManager;

    private Integer idUsuario;

    private Integer idIngrediente1;
    private Integer idIngrediente2;

    @BeforeEach
    void cargarDatos(){

        Usuario usuarioTest = new Usuario();
        usuarioTest.setNombre("Usuario Test Receta");
        usuarioTest.setEmail("test@gmail.com");

        entityManager.persist(usuarioTest);
        idUsuario = usuarioTest.getId();

        Ingrediente ingredienteTest1 = new Ingrediente();
        ingredienteTest1.setNombre("Tomate");
        entityManager.persist(ingredienteTest1);
        idIngrediente1 = ingredienteTest1.getId();

        Ingrediente ingredienteTest2 = new Ingrediente();
        ingredienteTest2.setNombre("Cebolla");
        entityManager.persist(ingredienteTest2);
        idIngrediente2 = ingredienteTest2.getId();





    }

    @Test
    @DisplayName("Servicio 2 -> Caso Positivo")
    public void crearRecetaConIngredienteTest(){
        //Given

        CrearRecetaDTO dto = new CrearRecetaDTO();
        dto.setNombre("Receta de prueba");
        dto.setInstrucciones("Instrucciones de prueba");
        dto.setIdUsuarioCreador(idUsuario);

        IngredienteRecetaDTO ingredienteRecetaDTO1 = new IngredienteRecetaDTO();
        ingredienteRecetaDTO1.setNombre("Tomate");

        IngredienteRecetaDTO ingredienteRecetaDTO2 = new IngredienteRecetaDTO();
        ingredienteRecetaDTO2.setNombre("Cebolla");

        dto.setIngredientesConDetalle(List.of(ingredienteRecetaDTO1, ingredienteRecetaDTO2));

        //When
        service.crearReceta(dto);

        //Then
        assertNotNull(dto);
        assertEquals(idUsuario, dto.getIdUsuarioCreador());
        assertEquals(2, dto.getIngredientesConDetalle().size(), "Debería tener 2 ingredientes asociados");

        System.out.println(dto);

    }

    @Test
    @DisplayName("Servicio 2 -> Caso Negativo")
    public void crearRecetaConIngredientesTestNegativo() {

        //Given

        CrearRecetaDTO dto = new CrearRecetaDTO();
        dto.setNombre("Receta de prueba 2");
        dto.setIdUsuarioCreador(3);

        //When

        //Then

        assertThrows(RuntimeException.class, () -> service.crearReceta(dto), "El usuario con id: " + dto.getIdUsuarioCreador() + " que intentas vincular a la receta no existe");

    }

    @Test
    @DisplayName("Servicio 3 -> Caso Positivo")
    public void buscarRecetaPorIdIngredienteTest() {

        //Given
        CrearRecetaDTO dto = new CrearRecetaDTO();
        dto.setIdUsuarioCreador(idUsuario);
        dto.setNombre("Receta de prueba 2");

        IngredienteRecetaDTO ingredienteRecetaDTOTest2 = new IngredienteRecetaDTO();    
        ingredienteRecetaDTOTest2.setNombre("Tomate");
        ingredienteRecetaDTOTest2.setCantidad(1.0);
        ingredienteRecetaDTOTest2.setUnidad("unidad");

        dto.setIngredientesConDetalle(List.of(ingredienteRecetaDTOTest2));

        service.crearReceta(dto);



        CrearRecetaFiltrosDTO filtros = new CrearRecetaFiltrosDTO();
        filtros.setIngredientes(new ArrayList<>(List.of(idIngrediente1)));
        filtros.setIdPreferencia(null);

        //When
        List<RecetaFiltrosDTO> recetasConFiltros = service.buscarRecetasConFiltros(filtros);

        //Then
        assertNotNull(recetasConFiltros);
        System.out.println("Recetas encontradas: " + recetasConFiltros.size());

        assertFalse(recetasConFiltros.isEmpty(), "Debería encontrar la receta con Tomate");
        assertEquals("Receta de prueba 2", recetasConFiltros.get(0).getNombre());

    }

    @Test
    @DisplayName("Servicio 3 -> Caso Negativo")
    public void obtenerDetalleRecetaInexistenteTest() {
        // Given
        CrearRecetaFiltrosDTO filtros = new CrearRecetaFiltrosDTO();
        filtros.setIngredientes(List.of(5));
        filtros.setIdPreferencia(null);

        // When
        List<RecetaFiltrosDTO> recetasConFiltros = service.buscarRecetasConFiltros(filtros);

        // Then
        assertNotNull(recetasConFiltros, "La lista de resultados no debería ser nula");

        assertTrue(recetasConFiltros.isEmpty(), "La lista debería estar vacía cuando el ID del ingrediente no existe");

        System.out.println("ID inexistente: " + recetasConFiltros.size());
    }

}
