package com.example.carniceria.controller;

import com.example.carniceria.model.Categoria;
import com.example.carniceria.model.Compra;
import com.example.carniceria.model.Producto;
import com.example.carniceria.service.ICategoriaService;
import com.example.carniceria.service.ICompraService;
import com.example.carniceria.util.Utilidades;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/compras")
public class CompraController {
    @Autowired
    ICompraService compraService;
    @GetMapping("")
    public ResponseEntity<Object> getCompras(){
        List<Compra> compras = compraService.findAllCompras();
        log.info("compras: "+compras);
        if (!compras.isEmpty())
            return ResponseEntity.ok(compras);
        else
            return ResponseEntity.notFound().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity getCompraById(@PathVariable("id") String id){
        Optional<Compra> compra = compraService.findCompraById(id);
        if (compra.isPresent())
            return ResponseEntity.ok(compra.get());
        else
            return ResponseEntity.notFound().build();
    }
    @PostMapping("")
    public ResponseEntity<Object> createCompra(@RequestBody Compra compra) throws ParseException {
        String codigo="";
        if (compra!=null) {
            //formato de fecha recibida por el objeto Date
            SimpleDateFormat format = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                    Locale.ENGLISH);
            //Recibiendo fecha del objeto Date
            Date date = format.parse(new Date().toString());
            //Convertir fecha del objeto Date al formato deseado
            String formatFecha = new SimpleDateFormat("yyyy-MM-dd").format(date);
            //formato de fecha recibida de la conversion anterior
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            //Convertir la fecha recibida en el formate deseado
            Date finalDate = format2.parse(formatFecha);

            //crear un id aleatorio, si id existe se genera uno nuevo
            do {
                int aleatorio = (int) (Math.random()*(99999-1)) + 1;
                log.info("aleatorio: "+aleatorio);
                if (aleatorio>=1 && aleatorio <100){
                    codigo= "CM000"+aleatorio;
                } else if (aleatorio>=100 && aleatorio <10000) {
                    codigo= "CM0"+aleatorio;
                }else{
                    codigo= "CM"+aleatorio;
                }
                log.info("codigo: "+codigo);
                log.info("Existe codigo compra: "+compraService.existeCompraById(codigo));
            }while (compraService.existeCompraById(codigo));
            compra.setId_compra(codigo);
            compra.setFecha(finalDate);
            compraService.insertCompra(compra);
            return ResponseEntity.ok(compra);
        }else{
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "Intente mas tárde.");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCompra(@PathVariable("id") String id){
        Optional<Compra> compra = compraService.findCompraById(id);
        if (compra.isPresent()){
            compraService.deleteCompraById(id);
            return Utilidades.generarResponse(HttpStatus.OK, "Compra eliminada con éxito");
        }else
            return Utilidades.generarResponse(HttpStatus.BAD_REQUEST, "Compra no eliminada, intente más tarde");
    }
}
