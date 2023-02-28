package com.example.carniceria.controller;

import com.example.carniceria.model.Categoria;
import com.example.carniceria.model.Detalle;
import com.example.carniceria.model.Producto;
import com.example.carniceria.repository.CategoriaRepository;
import com.example.carniceria.service.ICategoriaService;
import com.example.carniceria.util.Utilidades;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    ICategoriaService categoriaService;
    @GetMapping("")
    public ResponseEntity<Object> getCategorias(){
        List<Categoria> categorias = categoriaService.findAllCategorias();
        log.info("categorias: "+categorias);
            if (!categorias.isEmpty())
                return ResponseEntity.ok(categorias);
            else
                return ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity getCategoriaById(@PathVariable("id") Integer id){
        Optional<Categoria> categoria = categoriaService.findCategoriaById(id);
        if (categoria.isPresent())
            return ResponseEntity.ok(categoria.get());
        else
            return ResponseEntity.notFound().build();
    }
    @PostMapping("")
    public ResponseEntity<Object> createCategoria(@RequestBody Categoria categoria){
        if (categoria!=null) {
            //guardarDetalle
            categoriaService.saveCategoria(categoria);
            return Utilidades.generarResponse(HttpStatus.ACCEPTED, "Categoría creada con exitó");
        }else{
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "Categoría no creada, intente mas tárde.");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategoria(@PathVariable("id") Integer id, @RequestBody Categoria categoria){
        if (categoria != null){
            Optional<Categoria> categoria1 = categoriaService.findCategoriaById(id);
            if (categoria1.isPresent()) {
                Categoria categoria2 = categoria1.get();
                categoria2.setId_categoria(categoria.getId_categoria());
                categoria2.setNombre(categoria.getNombre());
                categoriaService.saveCategoria(categoria2);
                return Utilidades.generarResponse(HttpStatus.OK, "Categoría actualizada con exito");
            }else
                return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "Categoría no existe");
        }else
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "Categoría no se actualizó, intente más tarde");
    }
}
