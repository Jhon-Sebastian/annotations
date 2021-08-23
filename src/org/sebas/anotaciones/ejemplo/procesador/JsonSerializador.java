package org.sebas.anotaciones.ejemplo.procesador;

import org.sebas.anotaciones.ejemplo.Init;
import org.sebas.anotaciones.ejemplo.JsonAtributo;
import org.sebas.anotaciones.ejemplo.procesador.exception.JsonSerializadorException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class JsonSerializador {


    /*
        Hace lo mismo de que tenemos en el metodo de abajo, solo que en metodo
        aparte y ser mas entendible
     */
    public static void inicializarObjeto(Object object){
        if(Objects.isNull(object)){
            throw new JsonSerializadorException("El objeto a serializar no puede ser nulo");
        }

        Method[] metodos = object.getClass().getDeclaredMethods();
        Arrays.stream(metodos)
                .filter(m -> m.isAnnotationPresent(Init.class))
                //Invocamos el metodo con ese metodo
                .forEach(method -> {
                    try {
                        method.setAccessible(true);
                        method.invoke(object);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new JsonSerializadorException("Error al invocar el metodo = " + e.getMessage());
                    }
                });
    }

    public static String convertirJson(Object object){
        if(Objects.isNull(object)){
            throw new JsonSerializadorException("El objeto a serializar no puede ser nulo");
        }
        //Obtenemos los atributos de la clase y los guardamos como un campo de Field
        //El Object Field nos ayuda a poder obtener y comparar las anotaciones y sus atributos
        inicializarObjeto(object);
        Field[] atributos = object.getClass().getDeclaredFields();
        //El objetivo es filtrar los atributos que tengan la anotacion @JsonAtributo
        // y regresar un string con los atributos y valores
        return Arrays.stream(atributos)
                .filter(f -> f.isAnnotationPresent(JsonAtributo.class))
                //Preguntamos si en la anotacion @JsonAtributo(nombre = ?) el atributo nombre es vacio
                .map(f -> {
                    //Se tiene que hacer eso ya que los atributos son privados y manda error
                    f.setAccessible(true);
                    String nombre = f.getAnnotation(JsonAtributo.class).nombre().equals("")
                            ? f.getName() //Nombre del atributo
                            : f.getAnnotation(JsonAtributo.class).nombre(); // Nombre atributo JsonAtributo
                    try {
                        Object valor = f.get(object);
                        if(f.getAnnotation(JsonAtributo.class).capitalizar() && valor instanceof String){
                            String nuevoValor = (String) valor;
                            nuevoValor = Arrays.stream(nuevoValor.split(" "))
                            .map(palabra -> palabra.substring(0, 1).toUpperCase()
                                    + palabra.substring(1).toLowerCase()
                                    //Juntas todos los elementos en 1 solo con Collectos.joining
                                    //Como es de tipo String se concatena con " ", demiliter
                            ).collect(Collectors.joining(" "));

                            /* TODO: Regresa un String con la cadena modificada
                            String nuevoValor = (String) valor;
                            nuevoValor = Arrays.stream(nuevoValor.split(" "))
                            .map(palabra -> palabra.substring(0, 1).toUpperCase()
                                    + palabra.substring(1).toLowerCase()
                                    //Juntas todos los elementos en 1 solo con Collectos.joining
                                    //Como es de tipo String se concatena con " ", demiliter
                            ).collect(Collectors.joining(" "));
                             */

                                /*
                                    TODO: Para sola la primera palabra
                                    String nuevoValor = (String) valor;
                                        nuevoValor = String.valueOf(nuevoValor.charAt(0)).toUpperCase()
                                        + nuevoValor.substring(1).toLowerCase();
                                 */
                                //Modificamos el valor actual con el nuevo
                                f.set(object, nuevoValor);
                        }
                        return "\"" + nombre + "\":\"" + f.get(object) + "\"";
                    } catch (IllegalAccessException e) {
                        throw new JsonSerializadorException("Error al serializar a json: " + e.getMessage());
                    }
                }).reduce("{", (a, b) -> {
                    if (a.equals("{")) {
                        return a + b;
                    } else {
                        return a + ", " + b;
                    }
                }).concat("}");
    }

}
