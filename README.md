# Java-lab
Dinosaur Park Simulation (Java Lab)

Simulación de un parque turístico de dinosaurios desarrollada en Java con arquitectura modular basada en dominios. El sistema simula la operación del parque mediante ejecución por pasos, eventos aleatorios y monitoreo operativo, generando métricas y registros para análisis.

Tecnologías utilizadas
Java 17
Maven
JUnit 5
Mockito
JaCoCo (cobertura de pruebas)
H2 Database (embebida)
Liquibase (migraciones)

Requisitos previos
JDK 17
Maven 3.8 o superior

Verificar versiones

```bash
java -version
mvn -version
```

Instalación y preparación

1. Clonar o descargar el repositorio
2. Entrar al directorio del proyecto
3. Compilar el proyecto

```bash
mvn clean install
```

Ejecución de pruebas y cobertura
Ejecutar pruebas unitarias

```bash
mvn test
```

Generar reporte de cobertura con JaCoCo

```bash
mvn jacoco:report
```

El reporte se encuentra en:
target/site/jacoco/index.html

Ejecución de la simulación

```bash
mvn exec:java
```

Salida del sistema

1. Consola
   Durante la ejecución se muestra el estado del parque por cada ciclo de simulación, incluyendo:

* Paso de simulación
* Turistas activos
* Dinosaurios en encierro
* Energía del parque
* Eventos aleatorios
* Vehículos disponibles
* Ingresos y gastos

2. Persistencia local (H2)
   La base de datos embebida se genera en:
   data/parkdb.mv.db

Tablas principales:

* revenues
* expenses
* events

3. Configuración
   Los parámetros del sistema se encuentran en:
   src/main/resources/park.properties

Estructura del proyecto
src/main/java/com/javalab/dinosaurpark

* config
* model
* zone
* event
* persistence
* simulation
* monitoring
* Main.java

Comandos rápidos

```bash
mvn clean install
mvn test
mvn jacoco:report
mvn exec:java
```
