package org.example.smartchef.services;

import org.example.smartchef.converters.UsuarioMapper;
import org.example.smartchef.dto.CrearUsuarioDTO;
import org.example.smartchef.models.Preferencia;
import org.example.smartchef.models.Usuario;
import org.example.smartchef.repositories.IPreferenciaRepository;
import org.example.smartchef.repositories.IUsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceIntegrationTest {

    @InjectMocks
    private UsuarioService service;

    @Mock
    private IUsuarioRepository repository;

    @Mock
    private IPreferenciaRepository preferenciaRepository;

    @Mock
    private UsuarioMapper mapper;

    @Test
    @DisplayName("Servicio 1 -> Crear Usuario con Preferencias")
    public void crearUsuarioConPrefenciasTest() {

        //Given
        CrearUsuarioDTO dto = new CrearUsuarioDTO();
        dto.setNombre("Usuario Test");
        dto.setPreferenciasID(Set.of(1));

        Preferencia preferencia = new Preferencia();
        preferencia.setId(1);
        preferencia.setNombrePreferencia("Vegetariano");

        Usuario usuario = new Usuario();

        Mockito.when(this.mapper.convertirAEntityCrearUsuario(dto)).thenReturn(usuario);

        Mockito.when(this.preferenciaRepository.findById(1)).thenReturn(java.util.Optional.of(preferencia));

        //When
        this.service.crearUsuarioConPreferencias(dto);

        //Then
        Mockito.verify(this.mapper).convertirAEntityCrearUsuario(dto);
        Mockito.verify(this.preferenciaRepository).findById(1);
        Mockito.verify(this.repository).save(usuario);

    }

}
