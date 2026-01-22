package org.example.smartchef.services;


import jakarta.transaction.Transactional;
import org.example.smartchef.dto.RecetaDTO;
import org.example.smartchef.models.Dificultad;
import org.example.smartchef.models.Receta;
import org.example.smartchef.repositories.IRecetaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RecetaServiceTest {

    @Autowired
    private RecetaService service;

    @Autowired
    private IRecetaRepository repository;

    @BeforeAll
    void cargarDatos(){
        Receta r = new Receta();
        r.setId(1);
        r.setNombre("Receta Prueba");
        r.setDificultad(Dificultad.ALTA);
        r.setTiempo_preparacion(10);
        r.setFecha_creacion(null);

        repository.save(r);

    }


    @Test
    public void buscarPorIdTest(){}
    //Given
    //PREVIOS

    //Then
    //EJECUCIÓN PRUEBA DEL MÉTODO
    RecetaDTO dto = service.obtenerDetallesReceta(1);

    //When
    //COMPROACIONES

    @Test
    @DisplayName("Servicio 1 -> Caso Positivo")
    public void obtenerRecetaPorIdTest(){
        assert dto != null;
    }

}
