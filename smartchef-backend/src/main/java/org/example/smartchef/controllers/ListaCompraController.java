package org.example.smartchef.controllers;

import lombok.AllArgsConstructor;
import org.example.smartchef.dto.CarritoCompraDTO;
import org.example.smartchef.dto.GenerarListaCompraDTO;
import org.example.smartchef.models.CarritoCompra;
import org.example.smartchef.services.CarritoCompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/listas-compra")
@AllArgsConstructor
public class ListaCompraController {
    private CarritoCompraService service;

    @PostMapping
    public ResponseEntity<CarritoCompraDTO> generarListaCompra(@RequestBody GenerarListaCompraDTO dto){
        CarritoCompraDTO carritoCompra = service.crearCarritoCompra(dto);

        return ResponseEntity.ok(carritoCompra);
    }
}
