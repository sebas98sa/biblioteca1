# Sistema de Gestión de Biblioteca con Spring Boot

## Descripción del Proyecto

El **Sistema de Gestión de Biblioteca** es una aplicación web desarrollada con **Spring Boot** que permite administrar el catálogo de una biblioteca. El sistema facilita la gestión de diferentes tipos de elementos bibliográficos: **libros**, **revistas** y **DVDs**. Con esta aplicación, los bibliotecarios pueden realizar operaciones básicas como agregar, editar, eliminar y buscar elementos en el catálogo.

## Tecnologías Utilizadas

* **Java**: Lenguaje de programación principal
* **Spring Boot**: Framework para el desarrollo de aplicaciones Java
* **Spring Data JPA**: Para la persistencia de datos
* **Thymeleaf**: Motor de plantillas para las vistas
* **MySQL**: Sistema gestor de base de datos
* **Bootstrap**: Framework CSS para el diseño responsive

## Estructura del Proyecto

El proyecto sigue una arquitectura **MVC** (Modelo-Vista-Controlador):

### 1. Capa de Modelo

Contiene las entidades JPA que representan los objetos del dominio:

* `ElementoBiblioteca`: Clase base abstracta para todos los elementos del catálogo
* `Libro`, `Revista`, `DVD`: Clases específicas para cada tipo de elemento

### 2. Capa de Repositorio

Interfaces que extienden de JpaRepository para el acceso a datos:

* `LibroRepository`, `RevistaRepository`, `DVDRepository`

### 3. Capa de Servicio

Clases que implementan la lógica de negocio:

* `LibroService`, `RevistaService`, `DVDService`

### 4. Capa de Controlador

Controladores que manejan las peticiones HTTP:

* `MainController`: Controlador para la página principal
* `LibroController`, `RevistaController`, `DVDController`: Controladores específicos

### 5. Capa de Vista

Plantillas Thymeleaf para la interfaz de usuario:

* `index.html`: Página principal
* Carpetas `libros`, `revistas`, `dvds` con sus respectivas vistas

## Configuración de la Base de Datos

### Paso 1: Crear la Base de Datos

Ejecuta el script SQL proporcionado en el archivo `biblioteca.sql` para crear la estructura de la base de datos y datos de prueba:

1. Abre tu gestor de MySQL (MySQL Workbench, HeidiSQL, phpMyAdmin, etc.)
2. Ejecuta el script SQL proporcionado

### Paso 2: Configurar la Conexión

Edita el archivo `src/main/resources/application.properties` con tus credenciales de MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

## Ejecución de la Aplicación

Para ejecutar la aplicación:

1. Asegúrate de tener instalado JDK 17 o superior
2. Configura la conexión a la base de datos en `application.properties`
3. Ejecuta la clase `BibliotecaSpringApplication.java`
4. Abre un navegador y accede a `http://localhost:8080`

## Funcionalidades Principales

* **Gestión de Libros**: Agregar, editar, eliminar y buscar libros por título o autor
* **Gestión de Revistas**: Agregar, editar, eliminar y buscar revistas por categoría o editorial
* **Gestión de DVDs**: Agregar, editar, eliminar y buscar DVDs por género o director
* **Interfaz Responsive**: Diseño adaptable a diferentes dispositivos

## Requisitos del Sistema

* Java JDK 17 o superior
* MySQL 8.0 o superior
* Maven 3.6 o superior
* Navegador web moderno
