package org.sebas.anotaciones.ejemplo;

import java.lang.annotation.*;

/*
    Tenemos que configurar esta anotacion, tiene ciertas configuraciones
    metadada, para indicar el uso que le vamos a dar, o en que contexto
    se va a ejecutar, o donde se va a aplicar esta anotacion
 */

@Documented //Si lo queremos aregar al API Doc de docuementacion
/*  TODO: Target
    Donde se va a aplicar esta anotacion,
    FIELD -> Atributo
    TYPE -> Sobre la clase
    ANNOTATION_TYPE -> Otra anotacion
    ...
 */
@Target(ElementType.FIELD)
/*  TODO: Retention
    Sobre que contexto se va a ejecutar
    RetentionPolicy.CLASS -> Tiempo de compilacion
    RetentionPolicy.Runtime -> Tiempo de ejecucion
    RetentionPolicy.SOURCE -> Ninguna de las 2
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonAtributo {

    /*
        TODO: ¿Que es una anotacion?
        Es parecido a una interfaz, que nos permite agregar
        metadata o configuration a nuestras clases,atributos,metodo,contructor
        ,para indicar que es una clase padre, o documentacion etc

        TODO: Atributos
        Puede tener atributos propios de la anotacion, se definen como metodos
        y tambien se le puede asignar un valor por defecto
     */

    String nombre() default "";

    //Si es true, esta habierto a que la primera letra de cada palabra se convierta a mayuscula
    boolean capitalizar() default false;



}
