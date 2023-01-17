package com.example.carniceria.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Utilidades {
    public static ResponseEntity<Object> generarResponse(HttpStatus status, String mensaje){
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("fecha", new Date());
            map.put("status", status.value());
            map.put("mensaje", mensaje);
            return new ResponseEntity<Object>(map, status);
        }catch (Exception e){
            map.clear();
            map.put("fecha", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("mensaje", e.getStackTrace());
            return new ResponseEntity<Object>(map, status);
        }
    }
}
