package org.sebas.anotaciones.ejemplo;

import org.sebas.anotaciones.ejemplo.models.Producto;
import org.sebas.anotaciones.ejemplo.procesador.JsonSerializador;

import java.time.LocalDate;


public class EjemploAnotacion {
    public static void main(String[] args) {

        Producto p = new Producto();
        p.setFecha(LocalDate.now());
        p.setNombre("mesa centro roble");
        p.setPrecio(1000L);

        Producto p2 = new Producto();
        p2.setFecha(LocalDate.now());
        p2.setNombre("apple card");
        p2.setPrecio(5000L);

        System.out.println("Convertir a Json = " + JsonSerializador.convertirJson(p));
        System.out.println("Convertir a Json = " + JsonSerializador.convertirJson(p2));

    }
}
