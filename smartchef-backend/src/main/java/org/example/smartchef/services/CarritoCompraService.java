package org.example.smartchef.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.smartchef.converters.CarritoCompraMapper;
import org.example.smartchef.dto.CarritoCompraDTO;
import org.example.smartchef.dto.GenerarListaCompraDTO;
import org.example.smartchef.models.*;
import org.example.smartchef.repositories.ICarritoCompraRepository;
import org.example.smartchef.repositories.IRecetaIngredienteRepository;
import org.example.smartchef.repositories.IRecetaRepository;
import org.example.smartchef.repositories.IUsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class CarritoCompraService {
    private ICarritoCompraRepository carritoCompraRepository;
    private IUsuarioRepository usuarioRepository;
    private IRecetaRepository recetaRepository;
    private IRecetaIngredienteRepository recetaIngredienteRepository;

    private CarritoCompraMapper carritoCompraMapper;

    @Transactional
    public CarritoCompraDTO crearCarritoCompra(GenerarListaCompraDTO dto){
        Integer recetaId = dto.getRecetaId();
        Integer usuarioId = dto.getUsuarioId();

        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();

        Receta receta = recetaRepository.findById(recetaId).orElseThrow();

        List<RecetaIngrediente> ingredientesReceta = recetaIngredienteRepository.findByRecetaId(recetaId);

        if (ingredientesReceta.isEmpty()) {
            throw new RuntimeException("No hay ingredientes para esta receta");
        }

        CarritoCompra nuevoCarrito = new CarritoCompra();
        nuevoCarrito.setId_usuario(usuario);
        nuevoCarrito.setFecha_creacion(java.time.LocalDate.now());
        nuevoCarrito.setEstado("");

        Set<CarritoIngrediente> itemsCarrito = new HashSet<>();

        for (RecetaIngrediente ri : ingredientesReceta) {
            CarritoIngrediente ci = new CarritoIngrediente();

            ci.setId_carrito(nuevoCarrito);
            ci.setId_ingrediente(ri.getId_ingrediente());

            ci.setCantidad(ri.getCantidad());

            Ingrediente ingrediente = ri.getId_ingrediente();
            if (ingrediente != null){
                ci.setUnidad(ingrediente.getUnidad_medida());
            } else {
                ci.setUnidad(null);
            }

            ci.setComprado(false);

            itemsCarrito.add(ci);
        }

        nuevoCarrito.setCarritoIngrediente(itemsCarrito);

        CarritoCompra carritoGuardado = carritoCompraRepository.save(nuevoCarrito);

        return carritoCompraMapper.convertirADTO(carritoGuardado);
    }

}
