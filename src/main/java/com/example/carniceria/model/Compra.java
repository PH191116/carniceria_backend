package com.example.carniceria.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "compra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Compra {
    @Id
    private String id_compra;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    @Override
    public String toString() {
        return "Compra{" +
                "id_compra='" + id_compra + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
