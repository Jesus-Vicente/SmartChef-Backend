package org.example.smartchef.controllers;

import lombok.AllArgsConstructor;
import org.example.smartchef.dto.CarritoCompraDTO;
import org.example.smartchef.dto.GenerarListaCompraDTO;
import org.example.smartchef.models.CarritoCompra;
import org.example.smartchef.services.CarritoCompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listas-compra")
@AllArgsConstructor
@CrossOrigin(origins = "https://frontend-smartchef.onrender.com")
public class ListaCompraController {
    private CarritoCompraService service;

    @PostMapping
    public ResponseEntity<CarritoCompraDTO> generarListaCompra(@RequestBody GenerarListaCompraDTO dto){
        CarritoCompraDTO carritoCompra = service.crearCarritoCompra(dto);

        return ResponseEntity.ok(carritoCompra);
    }
}
