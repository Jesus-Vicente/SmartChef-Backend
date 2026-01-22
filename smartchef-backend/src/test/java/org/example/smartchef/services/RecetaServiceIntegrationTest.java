package org.example.smartchef.services;

import org.example.smartchef.models.Receta;
import org.example.smartchef.repositories.IRecetaRepository;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class RecetaServiceIntegrationTest {

    @InjectMocks
    private RecetaService service;

    @Mock
    private IRecetaRepository repository;

    public void buscarPorIdIntegracionTest(){

        //GIVEN

        Mockito.when(this.repository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Receta()));

        //THEN

        this.service.findById(1);

        //WHEN
        Mockito.verify(this.repository.findById(1));

    }


}
