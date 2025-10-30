# Refactor Datalake

## Descripción
Este repositorio contiene el código base de la práctica **P8 — Evitando el *vendor lock-in*: diseño para la portabilidad del código en la nube**, del curso *Tecnologías de Servicios para Ciencia de Datos* (Grado en Ciencia e Ingeniería de Datos, Universidad de Las Palmas de Gran Canaria).

El proyecto parte de un servicio `Datalake` implementado en **Java**, cuya versión original depende directamente del **AWS SDK** (`S3Client`, `GetObjectRequest`, etc.).  
El objetivo de la práctica es **refactorizar** el código para eliminar la dependencia estructural con el proveedor cloud, aplicando principios de diseño orientado a objetos.

---

## Objetivo del ejercicio
Rediseñar la aplicación para que pueda operar con distintos proveedores de almacenamiento (AWS S3, MinIO, local, etc.) sin modificar la lógica principal del sistema.

El estudiante deberá aplicar los siguientes principios:
- **DIP** — Principio de Inversión de Dependencias  
- **OCP** — Principio Abierto/Cerrado  
- **LSP** — Principio de Sustitución de Liskov  
- **DI** — Inyección de Dependencias  
- **SoC** — Separación de responsabilidades  

---

## Entorno de desarrollo
- Lenguaje: **Java 17+**
- Entorno recomendado: **IntelliJ IDEA**
- Dependencias: **AWS SDK for Java v2**
- Sistema de compilación: **Maven** o **Gradle**
