package com.example.carniceria.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        List<Detalle> detalle = detalleService.findDetalleByCompraFecha(finalDate);
        log.info("detalle: "+detalle);
        if (!detalle.isEmpty()) {
            ComprasPdf comprasPdf = new ComprasPdf(detalle);
            comprasPdf.exportar(response);
        }
    }

	}
	
	
	

