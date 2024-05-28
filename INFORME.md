---
title: Laboratorio de Programación Orientada a Objetos
author: Pacheco, Paula; Desimone, Sofía; Lopez, Simón; Chrispeels, Julian
---

El enunciado del laboratorio se encuentra en [este link](https://docs.google.com/document/d/1wLhuEOjhdLwgZ4rlW0AftgKD4QIPPx37Dzs--P1gIU4/edit#heading=h.xe9t6iq9fo58).

# 1. Tareas
Pueden usar esta checklist para indicar el avance.

## Verificación de que pueden hacer las cosas.
- [x] Java 17 instalado. Deben poder compilar con `make` y correr con `make run` para obtener el mensaje de ayuda del programa.

## 1.1. Interfaz de usuario
- [x] Estructurar opciones
- [x] Construir el objeto de clase `Config`

## 1.2. FeedParser
- [x] `class Article`
    - [x] Atributos
    - [x] Constructor
    - [x] Método `print`
    - [x] _Accessors_
- [x] `parseXML`

## 1.3. Entidades nombradas
- [x] Pensar estructura y validarla con el docente
- [x] Implementarla
- [x] Extracción
    - [x] Implementación de heurísticas
- [x] Clasificación
    - [x] Por tópicos
    - [x] Por categorías
- Estadísticas
    - [x] Por tópicos
    - [x] Por categorías
    - [x] Impresión de estadísticas

## 1.4 Limpieza de código
- [x] Pasar un formateador de código
- [x] Revisar TODOs

# 2. Experiencia
Aprendimos a programar en un lenguaje orientado a objetos, no lo habíamos anteriormente. Distribuimos las tareas entre los integrantes del grupo para entregar el trabajo en tiempo y forma.

# 3. Preguntas
1. Explicar brevemente la estructura de datos elegida para las entidades nombradas.

Creamos una clase abstracta nameEntities con atributos label, category y topic, para que sea madre de entidades nombradas más específicas. Creamos como hijas de esta clase a las siguientes clases: PersonNE, LocationNE, EventNE, OrganizationNE, OtherNE, cada una con atributos y métodos particulares. Por ejemplo, PersonNE, además de tener los atributos de la madres, tiene name y age.
También creamos clases para representar las categorías y tópicos. 


2. Explicar brevemente cómo se implementaron las heurísticas de extracción.

-Heurística ya programada: capitalized. 
Esta heurística toma como candidato a palabras seguidas que empiecen con mayúsculas y sigan con minúsculas, pero no considera palabras cuyas letras sean todas mayúsculas ni palabras por separado cuando hay varias seguidas que empiezan con mayúscula. Estas dos cosas impiden detectar algunos candidatos válidos. En las heurísticas programadas intentamos resolver esto.

-oneCapitalized: Toma como candidatos palabras sueltas que empiecen con mayúsculas. 
Por ejemplo, si una oración empieza con “El FMI …” , la primera heurística toma como candidato “El FMI” y la segunda toma dos “El” y “FMI”. 
En el primer caso, el candidato se clasifica como other. En el segundo, uno se clasifica como other y el otro como organization.

-capitalizedOnePoint: esta heurística no considera las palabras con mayúsculas que dan comienzo a una oración. Esto permite no considerar candidatos que no son entidades, como los artículos. Además considera siglas, es decir, palabras con todas sus letras mayúsculas. 


# 4. Extras
Completar si hacen algo.