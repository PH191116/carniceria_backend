package com.example.carniceria.controller;

import com.example.carniceria.model.Compra;
import com.example.carniceria.model.Detalle;
import com.example.carniceria.service.ICompraService;
import com.example.carniceria.service.IDetalleService;
import com.example.carniceria.util.Utilidades;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/detalle")
public class DetalleController {
    @Autowired
    IDetalleService detalleService;
    @GetMapping("")
    public ResponseEntity<Object> getDetalle(){
        List<Detalle> detalle = detalleService.findAllDetalle();
        log.info("detalle: "+detalle);
        if (!detalle.isEmpty())
            return ResponseEntity.ok(detalle);
        else
            return ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity getDetalleById(@PathVariable("id") Integer id){
        Optional<Detalle> detalle = detalleService.findDetalleById(id);
        if (detalle.isPresent())
            return ResponseEntity.ok(detalle.get());
        else
            return ResponseEntity.notFound().build();
    }
    @PostMapping("")
    public ResponseEntity<Object> createDetalle(@RequestBody Detalle detalle){
        if (detalle!=null) {
            //guardarDetalle
            detalleService.saveDetalle(detalle);
            return Utilidades.generarResponse(HttpStatus.ACCEPTED, "Compra creada con exitó");
        }else{
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "Compra no creada, intente mas tárde.");
        }
    }
    /*@DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCompra(@PathVariable("id") String id){
        Optional<Compra> compra = detalleService.findCompraById(id);
        if (compra.isPresent()){
            detalleService.deleteCompraById(id);
            return Utilidades.generarResponse(HttpStatus.OK, "Compra eliminada con éxito");
        }else
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "Compra no eliminada, intente más tarde");
    }*/
}
