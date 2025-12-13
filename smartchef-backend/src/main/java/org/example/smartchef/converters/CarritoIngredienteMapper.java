package org.example.smartchef.converters;

import org.example.smartchef.dto.CarritoIngredienteDTO;
import org.example.smartchef.models.CarritoCompra;
import org.example.smartchef.models.CarritoIngrediente;
import org.example.smartchef.models.Ingrediente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CarritoIngredienteMapper {

    @Mapping(source = "id_carrito.id", target = "id_carrito")
    @Mapping(source = "id_ingrediente.id", target = "id_ingrediente")
    CarritoIngredienteDTO convertirADTO(CarritoIngrediente entity);

    @Mapping(source = "id_carrito", target = "id_carrito", qualifiedByName = "mapIdToCarritoCompra")
    @Mapping(source = "id_ingrediente", target = "id_ingrediente", qualifiedByName = "mapIdToIngrediente")
    CarritoIngrediente convertirAEntity(CarritoIngredienteDTO dto);



    @Named("mapIdToCarritoCompra")
    default CarritoCompra mapIdToCarritoCompra(Integer id) {
        if (id == null) return null;
        CarritoCompra carrito = new CarritoCompra();
        carrito.setId(id);
        return carrito;
    }

    @Named("mapIdToIngrediente")
    default Ingrediente mapIdToIngrediente(Integer id) {
        if (id == null) return null;
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setId(id);
        return ingrediente;
    }


}
