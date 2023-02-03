package com.example.carniceria.util;

import com.example.carniceria.model.Detalle;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ComprasPdf {
    private List<Detalle> comprasList;
    public ComprasPdf(List<Detalle> listCompras){
        super();
    this.comprasList = listCompras;
    }
    //CREANDO CABECERA DE LA TABLA
    private void escribirCabeceraDeLaTabla(PdfPTable table){
        PdfPCell celda = new PdfPCell();

        celda.setBackgroundColor(Color.BLUE);
        celda.setPadding(5);

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);
        celda.setPhrase(new Phrase("ID", fuente));
        table.addCell(celda);

        celda.setPhrase(new Phrase("Codigo compra", fuente));
        table.addCell(celda);

        celda.setPhrase(new Phrase("fecha", fuente));
        table.addCell(celda);

        celda.setPhrase(new Phrase("producto", fuente));
        table.addCell(celda);

        celda.setPhrase(new Phrase("precio", fuente));
        table.addCell(celda);

        celda.setPhrase(new Phrase("cantidad", fuente));
        table.addCell(celda);

        celda.setPhrase(new Phrase("total compra", fuente));
        table.addCell(celda);

    }
    // LLENAR LA TABLA
    private void escribirDatosTabla(PdfPTable table) throws ParseException {
        //fecha recibida desde la base
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int count = 1;
        double total = 0.00;
        for (Detalle detalle: comprasList) {
            table.addCell(String.valueOf(count++));
            table.addCell(detalle.getCompra().getId_compra());
            //convirtiendo la fecha recibida a date
            Date date = format.parse(detalle.getCompra().getFecha().toString());
            //agregando formato a mostrar en el pdf
            table.addCell(new SimpleDateFormat("dd-MM-yyyy").format(date));
            table.addCell(detalle.getProducto().getNombre());
            table.addCell(String.valueOf(detalle.getProducto().getPrecio()));
            table.addCell(String.valueOf(detalle.getCantidad()));
            table.addCell(String.valueOf(detalle.getTotal()));
            total += detalle.getTotal();
            if (comprasList.lastIndexOf(detalle)+1 == comprasList.size()){
                table.addCell("");
                table.addCell("");
                table.addCell("");
                table.addCell("");
                table.addCell("");
                table.addCell("");
                table.addCell(String.valueOf(total));
            }
        }
    }
    // METODO PARA EXPORTAR PDF
    public void  exportar(HttpServletResponse response) throws IOException, ParseException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuente.setColor(Color.BLUE);
        fuente.setSize(18);

        Paragraph titulo = new Paragraph("Reporte de compras", fuente);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(titulo);

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);
        //agregando las dimensiones de cada celda de la tabla
        table.setWidths(new float[]{1f, 2.3f, 3f, 4f, 1.5f, 1.5f, 1.5f});
        table.setWidthPercentage(110);

        escribirCabeceraDeLaTabla(table);
        escribirDatosTabla(table);

        document.add(table);
        document.close();
    }
}
