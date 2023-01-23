package com.example.carniceria.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.carniceria.model.Detalle;
import com.example.carniceria.service.ICompraService;
import com.example.carniceria.service.IDetalleService;
import com.example.carniceria.util.ComprasPdf;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/reportes")
public class ReportesController {
    @Autowired
    IDetalleService detalleService;
    @Autowired
    ICompraService compraService;
    @GetMapping("/pdf")
    public void exportarListaComprasPDF(HttpServletResponse response) throws IOException, ParseException {
        response.setContentType("application/pdf");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaActual = dateFormat.format(new Date());

        String cabecera = "Content-Disposition";
        String valor  = "attachment; filename=Compras_"+fechaActual+".pdf";
        response.setHeader(cabecera, valor);
        List<Detalle> detalle = detalleService.findAllDetalle();
//        DetalleCompraProductos detalleCompraProducto = new DetalleCompraProductos();
//        DetalleList detalleList = new DetalleList();
//        List<Compra> compras = new ArrayList<>();
//        List<Producto> productos = new ArrayList<>();
//        List<DetalleCompraProductos> detalleCompraProductos = new ArrayList<>();
        log.info("detalle: "+detalle);
        if (!detalle.isEmpty()) {
            ComprasPdf comprasPdf = new ComprasPdf(detalle);
            comprasPdf.exportar(response);
        }
    }

	}
	
	
	

