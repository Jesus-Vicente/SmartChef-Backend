package org.example.smartchef.services;


import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.example.smartchef.dto.*;
import org.example.smartchef.models.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

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

    private Integer idReceta;

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
        ingredienteTest1.setUnidad_medida("kg");
        entityManager.persist(ingredienteTest1);
        idIngrediente1 = ingredienteTest1.getId();

        Ingrediente ingredienteTest2 = new Ingrediente();
        ingredienteTest2.setNombre("Cebolla");
        ingredienteTest2.setUnidad_medida("kg");
        entityManager.persist(ingredienteTest2);
        idIngrediente2 = ingredienteTest2.getId();

        Receta receta = new Receta();
        receta.setNombre("Receta Test");
        receta.setUsuario_creador_id(usuarioTest);
        receta.setInstrucciones("Instrucciones de prueba");
        receta.setTiempo_preparacion(10);
        receta.setFecha_creacion(java.time.LocalDate.now());
        receta.setDificultad(Dificultad.MEDIA);

        entityManager.persist(receta);
        idReceta = receta.getId();

        RecetaIngrediente recetaIngrediente = new RecetaIngrediente();
        recetaIngrediente.setId_receta(receta);
        recetaIngrediente.setId_ingrediente(ingredienteTest1);
        recetaIngrediente.setCantidad(1.0);
        recetaIngrediente.setUnidad(ingredienteTest1.getUnidad_medida());

        RecetaIngrediente recetaIngrediente2 = new RecetaIngrediente();
        recetaIngrediente2.setId_receta(receta);
        recetaIngrediente2.setId_ingrediente(ingredienteTest2);
        recetaIngrediente2.setCantidad(3.0);
        recetaIngrediente2.setUnidad(ingredienteTest2.getUnidad_medida());

        entityManager.persist(recetaIngrediente);
        entityManager.persist(recetaIngrediente2);



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
        CrearRecetaFiltrosDTO filtros = new CrearRecetaFiltrosDTO();
        filtros.setIngredientes(List.of(idIngrediente1, idIngrediente2));
        filtros.setIdPreferencia(null);

        //When
        List<RecetaFiltrosDTO> recetasConFiltros = service.buscarRecetasConFiltros(filtros);

        //Then
        assertFalse(recetasConFiltros.isEmpty(), "La lista de resultados no debería estar vacía");
        assertEquals("Receta Test", recetasConFiltros.get(0).getNombre(), "El nombre de la receta no coincide");

    }

    @Test
    @DisplayName("Servicio 3 -> Caso Negativo")
    public void obtenerRecetaConIngredienteInexistenteTest() {
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

    @Test
    @DisplayName("Servicio 4 -> Caso Positivo")
    public void obtenerDetallesRecetaTest(){
        //Given
        RecetaDTO dto = service.obtenerDetallesReceta(idReceta);

        //When
        service.findById(idReceta);
        System.out.println(dto.toString());

        //Then
        assertNotNull(dto);
        assertEquals("Receta Test", dto.getNombre());


    }

    @Test
    @DisplayName("Servicio 4 -> Caso Negativo")
    public void obtenerDetallesRecetaInexistenteTest(){
        //Given
        Integer idRecetaInexistente = 10;

        //When
        RecetaDTO dto = service.obtenerDetallesReceta(idRecetaInexistente);

        //Then
        assertNull(dto, "Devuelve NULL cuando la receta no existe");

    }


    @Test
    @DisplayName("Servicio 9 -> Caso Positivo")
    public void obtenerTop5IngredientesTest(){
        // When
        List<IngredienteEstadisticasDTO> estadisticas = service.obtenerTop5Ingredientes();

        // Then
        assertNotNull(estadisticas, "La lista no debe ser nula");
        assertFalse(estadisticas.isEmpty(), "Debería haber datos en las estadísticas");

        // Extraemos nombres para verificar presencia sin importar el orden del empate
        List<String> nombres = estadisticas.stream()
                .map(IngredienteEstadisticasDTO::getNombreIngrediente)
                .toList();

        assertTrue(nombres.contains("Tomate"), "Debe contener Tomate");
        assertTrue(nombres.contains("Cebolla"), "Debe contener Cebolla");

        System.out.println(estadisticas);
    }

    @Test
    @DisplayName("Servicio 9 -> Caso Negativo")
    public void obtenerTop5IngredientesTestNegativo(){
        //Given
        entityManager.createQuery("DELETE FROM RecetaIngrediente").executeUpdate();
        entityManager.createQuery("DELETE FROM Receta").executeUpdate();
        entityManager.createQuery("DELETE FROM Ingrediente").executeUpdate();
        entityManager.flush();
        entityManager.clear();

        //When
        List<IngredienteEstadisticasDTO> estadisticas = service.obtenerTop5Ingredientes();


        //Then
        assertNotNull(estadisticas, "La respuesta no debe ser null");
        assertTrue(estadisticas.isEmpty(), "Debe retornar una lista vacía si no hay recetas");

    }

    @Test
    @DisplayName("Servicio 10 -> Caso Positivo")
    public void obtenerUsuarioPopularTest() {
        // Given
        Usuario usuarioVotante = entityManager.find(Usuario.class, idUsuario);
        Receta recetaFavorita = entityManager.find(Receta.class, idReceta);

        Favorito favorito = new Favorito();
        favorito.setUsuario(usuarioVotante);
        favorito.setReceta(recetaFavorita);
        favorito.setFecha_guardado(java.time.LocalDate.now());

        entityManager.persist(favorito);

        // When
        Optional<UsuarioPopularDTO> popular = service.obtenerUsuarioConRecetaMasFavorita();

        // Then
        assertTrue(popular.isPresent(), "Debería existir un usuario popular");
        assertEquals("Usuario Test Receta", popular.get().getNombreUsuario(), "El nombre del usuario no coincide");
        assertEquals(1, popular.get().getCantidadFavoritos(), "La cantidad de favoritos no coincide");

        System.out.println("Usuario Popular: " + popular.get().getNombreUsuario());
        System.out.println("Receta Popular: " + popular.get().getNombreRecetaPopular());

    }

    @Test
    @DisplayName("Servicio 10 -> Caso Negativo")
    public void obtenerUsuarioPopularEmpateTest() {
        // Given

        Receta receta2 = new Receta();
        receta2.setNombre("Receta Empate");
        receta2.setUsuario_creador_id(entityManager.find(Usuario.class, idUsuario));
        receta2.setDificultad(Dificultad.ALTA);
        entityManager.persist(receta2);

        Favorito f1 = new Favorito();
        f1.setUsuario(entityManager.find(Usuario.class, idUsuario));
        f1.setReceta(entityManager.find(Receta.class, idReceta));
        entityManager.persist(f1);

        Favorito f2 = new Favorito();
        f2.setUsuario(entityManager.find(Usuario.class, idUsuario));
        f2.setReceta(receta2);
        entityManager.persist(f2);

        // When
        Optional<UsuarioPopularDTO> resultado = service.obtenerUsuarioConRecetaMasFavorita();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isPresent(), "El sistema debe retornar una de las recetas empatadas");
        assertEquals(1L, resultado.get().getCantidadFavoritos(), "El conteo de favoritos debe ser correcto");

        System.out.println("Usuario Popular en caso de empate: " + resultado.get().getNombreUsuario());
        System.out.println("Receta Popular en caso de empate: " + resultado.get().getNombreRecetaPopular());

    }

}
