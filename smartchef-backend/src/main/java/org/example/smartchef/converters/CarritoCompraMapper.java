package org.example.smartchef.converters;

import org.example.smartchef.dto.CarritoCompraDTO;
import org.example.smartchef.models.CarritoCompra;
import org.example.smartchef.models.CarritoIngrediente;
import org.example.smartchef.models.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CarritoIngredienteMapper.class})
public interface CarritoCompraMapper {

    @Mapping(source = "id_usuario.id", target = "usuarioId")
    @Mapping(source = "carritoIngrediente", target = "carritoIngrediente")
    CarritoCompraDTO convertirADTO(CarritoCompra entity);

    List<CarritoCompraDTO> convertirADTO(List<CarritoCompra> entidades);

    @Mapping(source = "usuarioId", target = "id_usuario.id")
    @Mapping(source = "carritoIngrediente", target = "carritoIngrediente")
    CarritoCompra convertirAEntity(CarritoCompraDTO dto);

    List<CarritoCompra> convertirAEntity(List<CarritoCompraDTO> dtos);

    default Usuario mapIdToUsuario(Integer id) {
        if (id == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        return usuario;
    }


    default CarritoIngrediente mapIdToCarritoIngrediente(Integer id) {
        if (id == null) return null;
        CarritoIngrediente carritoIngrediente = new CarritoIngrediente();
        carritoIngrediente.setId(id);
        return carritoIngrediente;
    }



}
