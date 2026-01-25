package org.example.smartchef.services;

import org.example.smartchef.converters.CarritoCompraMapper;
import org.example.smartchef.dto.CarritoCompraDTO;
import org.example.smartchef.dto.CarritoIngredienteDTO;
import org.example.smartchef.dto.GenerarListaCompraDTO;
import org.example.smartchef.models.*;
import org.example.smartchef.repositories.ICarritoCompraRepository;
import org.example.smartchef.repositories.IRecetaRepository;
import org.example.smartchef.repositories.IUsuarioRepository;
import org.example.smartchef.repositories.IRecetaIngredienteRepository; // IMPORTANTE
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CarritoCompraServiceIntegrationTest {

    @InjectMocks
    private CarritoCompraService service;

    @Mock
    private IRecetaRepository recetaRepository;

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private ICarritoCompraRepository carritoRepository;

    @Mock
    private IRecetaIngredienteRepository recetaIngredienteRepository;

    @Mock
    private CarritoCompraMapper carritoMapper;

    @Test
    @DisplayName("Servicio 6 -> Generar lista de compras (Caso Positivo)")
    public void crearCarritoCompraIntegrationTest() {
        // GIVEN
        Integer idReceta = 10;
        Integer idUsuario = 1;

        GenerarListaCompraDTO generarListaCompraDTO = new GenerarListaCompraDTO();
        generarListaCompraDTO.setUsuarioId(idUsuario);
        generarListaCompraDTO.setRecetaId(idReceta);

        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);

        Receta receta = new Receta();
        receta.setId(idReceta);
        receta.setNombre("Ensalada César");

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre("Lechuga");
        ingrediente.setUnidad_medida("gramos");

        RecetaIngrediente recetaIngrediente = new RecetaIngrediente();
        recetaIngrediente.setId_ingrediente(ingrediente);
        recetaIngrediente.setCantidad(200.0);

        CarritoCompraDTO carritoCompraDTO = new CarritoCompraDTO();
        carritoCompraDTO.setUsuarioId(idUsuario);
        carritoCompraDTO.setCarritoIngrediente(List.of(new CarritoIngredienteDTO()));

        Mockito.when(this.usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuario));
        Mockito.when(this.recetaRepository.findById(idReceta)).thenReturn(Optional.of(receta));

        Mockito.when(this.recetaIngredienteRepository.findByRecetaId(idReceta))
                .thenReturn(List.of(recetaIngrediente));

        Mockito.when(this.carritoRepository.existeCarritoParaUsuarioYReceta(idUsuario, idReceta))
                .thenReturn(false);

        Mockito.when(this.carritoRepository.save(ArgumentMatchers.any(CarritoCompra.class)))
                .thenReturn(new CarritoCompra());

        Mockito.when(this.carritoMapper.convertirADTO(ArgumentMatchers.any(CarritoCompra.class)))
                .thenReturn(carritoCompraDTO);

        // WHEN
        CarritoCompraDTO resultado = this.service.crearCarritoCompra(generarListaCompraDTO);

        // THEN
        assertNotNull(resultado);
        assertEquals(idUsuario, resultado.getUsuarioId());
        assertFalse(resultado.getCarritoIngrediente().isEmpty(), "La lista no debe estar vacía");

        Mockito.verify(this.carritoRepository).save(ArgumentMatchers.any(CarritoCompra.class));
    }
}