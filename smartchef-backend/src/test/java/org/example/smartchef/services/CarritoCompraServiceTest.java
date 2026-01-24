package org.example.smartchef.services;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.A;
import org.example.smartchef.dto.CarritoCompraDTO;
import org.example.smartchef.dto.GenerarListaCompraDTO;
import org.example.smartchef.models.Ingrediente;
import org.example.smartchef.models.Receta;
import org.example.smartchef.models.RecetaIngrediente;
import org.example.smartchef.models.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CarritoCompraServiceTest {

    @Autowired
    private CarritoCompraService service;

    @Autowired
    private EntityManager entityManager;

    private Integer idUsuario;
    private Integer idReceta;

    @BeforeEach
    void cargarDatos(){

        Usuario usuarioCarrito = new Usuario();
        usuarioCarrito.setNombre("Usuario Test Carrito");
        usuarioCarrito.setEmail("carritoTest@gmail.com");
        entityManager.persist(usuarioCarrito);
        idUsuario = usuarioCarrito.getId();


        Receta recetaCarrito = new Receta();
        recetaCarrito.setNombre("Receta Test Carrito");
        recetaCarrito.setUsuario_creador_id(usuarioCarrito);
        recetaCarrito.setInstrucciones("Instrucciones de prueba");
        recetaCarrito.setFecha_creacion(java.time.LocalDate.now());
        entityManager.persist(recetaCarrito);
        idReceta = recetaCarrito.getId();


        Ingrediente ingredienteCarrito = new Ingrediente();
        ingredienteCarrito.setNombre("Espaguetis");
        ingredienteCarrito.setUnidad_medida("gramos");
        entityManager.persist(ingredienteCarrito);

        RecetaIngrediente relacionIngrediente = new RecetaIngrediente();
        relacionIngrediente.setId_receta(recetaCarrito);
        relacionIngrediente.setId_ingrediente(ingredienteCarrito);
        relacionIngrediente.setCantidad(1.0);
        relacionIngrediente.setUnidad(ingredienteCarrito.getUnidad_medida());
        entityManager.persist(relacionIngrediente);



    }

    @Test
    @DisplayName("Servicio 6 -> Caso Positivo")
    public void generarListaCompraTest() {

        //Given
        GenerarListaCompraDTO dto = new GenerarListaCompraDTO();
        dto.setUsuarioId(idUsuario);
        dto.setRecetaId(idReceta);

        //When
        CarritoCompraDTO resultado = service.crearCarritoCompra(dto);

        //Then
        assertNotNull(dto);
        assertFalse(resultado.getCarritoIngrediente().isEmpty(), "La lista debe tener ingredientes");


    }

    @Test
    @DisplayName("Servicio 6 -> Caso Negativo")
    public void generarListaCompraTestNegativo() {
        // Given: Creamos la primera vez
        GenerarListaCompraDTO dto = new GenerarListaCompraDTO();
        dto.setUsuarioId(idUsuario);
        dto.setRecetaId(idReceta);
        service.crearCarritoCompra(dto);

        // When

        // Then
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            service.crearCarritoCompra(dto);
        });

        assertEquals("Ya existe una lista de compra para esta receta", ex.getMessage());


    }
}
