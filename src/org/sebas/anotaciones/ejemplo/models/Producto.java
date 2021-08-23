package org.sebas.anotaciones.ejemplo.models;

import org.sebas.anotaciones.ejemplo.Init;
import org.sebas.anotaciones.ejemplo.JsonAtributo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Producto {

    /*
        Las anotaciones asi no hacen nada, toca usar la API
        de reflexion de java para agregar la funcionalidad
        de convertir el objeto de tipo producto a json
     */

    @JsonAtributo(nombre = "descripcion")
    private String nombre;

    @JsonAtributo(nombre = "costo")
    private Long precio;

    private LocalDate fecha;

    /*
        Divide las palabras del String en un arreglo y lo convierte
        de un Stream. Para convertir la primera letra de cada palabra
        en mayuscula y luego regresar un String con todas las palabras
     */
    @Init
    private void init(){
        this.nombre = Arrays.stream(nombre.split(" "))
                .map(palabra -> palabra.substring(0, 1).toUpperCase()
                                + palabra.substring(1).toLowerCase()
                        //Juntas todos los elementos en 1 solo con Collectos.joining
                        //Como es de tipo String se concatena con " ", demiliter
                ).collect(Collectors.joining(" "));
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
