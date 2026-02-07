package org.example.smartchef.services;

import org.example.smartchef.converters.RecetaMapper;
import org.example.smartchef.converters.UsuarioMapper;
import org.example.smartchef.dto.*;
import org.example.smartchef.models.Ingrediente;
import org.example.smartchef.models.Receta;
import org.example.smartchef.models.Usuario;
import org.example.smartchef.repositories.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RecetaServiceIntegrationTest {

    @InjectMocks
    private RecetaService service;

    @Mock
    private IRecetaRepository repository;
    @Mock
    private IUsuarioRepository usuarioRepository;
    @Mock
    private IIngredientesRepository ingredienteRepository;
    @Mock
    private IPreferenciaRepository preferenciaRepository;
    @Mock
    private IRecetaPreferenciaRepository recetaPreferenciaRepository;
    @Mock
    private IRecetaIngredienteRepository recetaIngredienteRepository;
    @Mock
    private IFotoRepository fotoRepository;
    @Mock
    private RecetaMapper mapper;
    @Mock
    private UsuarioMapper usuarioMapper;

    @Test
    @DisplayName("Servicio 2 -> Crear Receta con Ingredientes")
    public void crearRecetaConIngredientesIntegracionTest(){
        // GIVEN
        CrearRecetaDTO dto = new CrearRecetaDTO();
        dto.setNombre("Paella");
        dto.setIdUsuarioCreador(1);

        IngredienteRecetaDTO ingDto = new IngredienteRecetaDTO();
        ingDto.setNombre("tomate");
        dto.setIngredientesConDetalle(List.of(ingDto));

        Usuario usuario = new Usuario();
        Ingrediente tomate = new Ingrediente();
        tomate.setNombre("tomate");

        Receta recetaFinal = new Receta();

        Mockito.when(this.usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        Mockito.when(this.ingredienteRepository.findByNombreIn(ArgumentMatchers.anyCollection()))
                .thenReturn(List.of(tomate));
        Mockito.when(this.mapper.convertirAEntityCrearRecetaConIngredientes(dto))
                .thenReturn(recetaFinal);

        Mockito.when(this.repository.save(ArgumentMatchers.any(Receta.class))).thenReturn(recetaFinal);

        // WHEN
        this.service.crearReceta(dto);

        // THEN
        Mockito.verify(this.usuarioRepository).findById(1);
        Mockito.verify(this.ingredienteRepository).findByNombreIn(ArgumentMatchers.anyCollection());
        Mockito.verify(this.repository).save(recetaFinal);

        Mockito.verify(this.recetaIngredienteRepository).saveAll(ArgumentMatchers.anyList());
    }

    @Test
    @DisplayName("Servicio 3 -> Buscar Recetas con Filtros (Caso con Ingredientes)")
    public void buscarRecetasConFiltrosIntegrationTest() {
        // GIVEN
        CrearRecetaFiltrosDTO filtrosDto = new CrearRecetaFiltrosDTO();
        filtrosDto.setIdPreferencia(1);
        filtrosDto.setIngredientes(List.of(10, 11));

        Receta recetaEntidad = new Receta();
        recetaEntidad.setNombre("Paella Vegana");
        List<Receta> listaEntidades = List.of(recetaEntidad);

        RecetaFiltrosDTO recetaResumenDto = new RecetaFiltrosDTO();
        recetaResumenDto.setNombre("Paella Vegana");
        List<RecetaFiltrosDTO> listaResultadosEsperados = List.of(recetaResumenDto);


        Mockito.when(this.repository.buscarConFiltros(1, List.of(10, 11)))
                .thenReturn(listaEntidades);

        Mockito.when(this.mapper.convertirARecetaFiltrosDTO(listaEntidades))
                .thenReturn(listaResultadosEsperados);

        // WHEN
        List<RecetaFiltrosDTO> resultadoFinal = this.service.buscarRecetasConFiltros(filtrosDto);

        // THEN
        Mockito.verify(this.repository).buscarConFiltros(1, List.of(10, 11));

        Mockito.verify(this.repository, Mockito.never()).buscarSinFiltroIngredientes(ArgumentMatchers.anyInt());

        Mockito.verify(this.mapper).convertirARecetaFiltrosDTO(listaEntidades);

        assert(resultadoFinal != null);
        assert(resultadoFinal.size() == 1);
        assert(resultadoFinal.get(0).getNombre().equals("Paella Vegana"));
    }


    @Test
    @DisplayName("Servicio 4 -> Ver detalle de receta con ingredientes y pasos")
    public void obtenerDetalleRecetaIntegrationTest() {
        // GIVEN
        Integer idReceta = 5;
        Receta receta = new Receta();
        receta.setId(idReceta);
        receta.setNombre("Lasaña");
        receta.setInstrucciones("Pasos detallados de la lasaña test...");

        RecetaDTO recetaDTO = new RecetaDTO();
        recetaDTO.setNombre(receta.getNombre());
        recetaDTO.setInstrucciones(receta.getInstrucciones());

        IngredienteRecetaDTO ing1 = new IngredienteRecetaDTO();
        ing1.setNombre("pasta");
        ing1.setCantidad(12.0);
        ing1.setUnidad("unidades");

        recetaDTO.setIngredientesConDetalle(List.of(ing1));


        Mockito.when(this.repository.findById(idReceta))
                .thenReturn(Optional.of(receta));

        Mockito.when(this.mapper.convertirADTO(receta))
                .thenReturn(recetaDTO);

        // WHENr
        RecetaDTO resultado = this.service.obtenerDetallesReceta(idReceta);

        // THEN
        Mockito.verify(this.repository).findById(idReceta);
        Mockito.verify(this.mapper).convertirADTO(receta);

        assert(resultado != null);
        assert(resultado.getInstrucciones() != null);
        assert(!resultado.getIngredientesConDetalle().isEmpty());
        assert(resultado.getIngredientesConDetalle().get(0).getNombre().equals("pasta"));

        System.out.println(resultado);
    }

    @Test
    @DisplayName("Servicio 9 -> Obtener Top 5 ingredientes más utilizados")
    public void obtenerTop5IngredientesIntegrationTest() {
        // GIVEN
        IngredienteEstadisticasDTO ingredienteEstadisticasDTO = Mockito.mock(IngredienteEstadisticasDTO.class);
        IngredienteEstadisticasDTO ingredienteEstadisticasDTO2 = Mockito.mock(IngredienteEstadisticasDTO.class);

        Mockito.when(ingredienteEstadisticasDTO.getNombreIngrediente()).thenReturn("Tomate");

        List<IngredienteEstadisticasDTO> listaIngredientesDTO = List.of(ingredienteEstadisticasDTO, ingredienteEstadisticasDTO2);

        Mockito.when(this.repository.findTop5IngredientesMasUtilizados())
                .thenReturn(listaIngredientesDTO);

        // WHEN
        List<IngredienteEstadisticasDTO> listaIngredientesTop5 = this.service.obtenerTop5Ingredientes();

        // THEN
        assertNotNull(listaIngredientesTop5);
        assertEquals(2, listaIngredientesTop5.size());
        assertEquals("Tomate", listaIngredientesTop5.get(0).getNombreIngrediente());

        Mockito.verify(this.repository).findTop5IngredientesMasUtilizados();
    }

    @Test
    @DisplayName("Servicio 10 -> Usuario con la receta más veces guardada como favorita")
    public void obtenerUsuarioConRecetaMasFavoritaIntegrationTest() {
        // GIVEN
        UsuarioPopularDTO usuarioPopularDTO = new UsuarioPopularDTO(1, "Usuario Test Receta", "Receta Test", 5L);
        List<UsuarioPopularDTO> listaUsuarioPopular = List.of(usuarioPopularDTO);

        // STUBBING: Tu repositorio usa findTop1UsuarioPopular()
        Mockito.when(this.repository.findTop1UsuarioPopular())
                .thenReturn(listaUsuarioPopular);

        // WHEN
        Optional<UsuarioPopularDTO> resultado = this.service.obtenerUsuarioConRecetaMasFavorita();

        // THEN
        assertTrue(resultado.isPresent());
        assertEquals("Usuario Test Receta", resultado.get().getNombreUsuario());
        assertEquals(5L, resultado.get().getCantidadFavoritos());

        Mockito.verify(this.repository).findTop1UsuarioPopular();
    }


}
