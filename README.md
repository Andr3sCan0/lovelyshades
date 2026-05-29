Sistema Web de Gestión y Cuadre de Caja para la Tienda de Maquillaje LovelyShadesIntegrantes
•	Cesar David Agudelo Idarraga 
•	Andrés Felipe Cano Colorado 
•	José Lisandro Montoya 
•	Diego Alexander González Acevedo 

Necesidad o Problema
La tienda de maquillaje LovelyShades realiza actualmente sus procesos administrativos de forma manual, incluyendo el control de inventario, el registro de ventas, la gestión de clientes y el cuadre de caja.
Esta metodología genera errores frecuentes, pérdida de información, inconsistencias en el cierre diario de caja y dificultades en la toma de decisiones, lo que afecta la eficiencia operativa del negocio.

Descripción de la Situación Problemática
LovelyShades no cuenta con un sistema centralizado que integre la información de productos, clientes, ventas y movimientos financieros. Cada proceso se realiza de manera independiente y manual, lo que ocasiona:
•	Errores humanos en el registro de ventas. 
•	Descuadres en la caja al final del día. 
•	Falta de control sobre el stock disponible. 
•	Ausencia de alertas de bajo inventario. 
•	Demoras en la atención al cliente. 
•	Falta de reportes claros para la toma de decisiones. 
La ausencia de una herramienta tecnológica limita el crecimiento del negocio y dificulta el control adecuado de la información financiera y comercial.

Objetivo General
Desarrollar un sistema web en Java con interfaz HTML que permita automatizar y centralizar los procesos administrativos de la tienda LovelyShades, integrando la gestión de inventario, clientes, ventas, facturación, reportes y cuadre de caja, con el fin de mejorar la eficiencia operativa y reducir errores en los registros diarios.

Objetivos Específicos
• Levantar y documentar los requerimientos funcionales, no funcionales y las reglas de negocio del sistema, identificando las necesidades de la tienda. 
• Realizar el diseño del sistema, definiendo su estructura y modelo conforme a las buenas prácticas del desarrollo de software. 
• Implementar el sistema de acuerdo con los requisitos definidos, garantizando la integración y funcionamiento de sus componentes. 
• Realizar las pruebas del sistema para verificar el cumplimiento de los requisitos y asegurar su correcto funcionamiento. 
• Implantar el sistema en el entorno de producción, capacitar a los usuarios, entregar la documentación y garantizar la transición al proceso de soporte y mantenimiento.

----------------------------------------------------------------------------------------
# LovelyShades Backend

## Descripción

Backend desarrollado con Spring Boot, Spring Data JPA y SQL Server para la gestión de clientes, productos, facturas, detalle de facturas e inventario.

Actualmente los módulos implementados son:

* Cliente
* Producto
* Factura
* DetalleFactura
* Inventario

La documentación de la API está disponible mediante Swagger.

---

## Requisitos

* Java 21
* SQL Server
* Maven (o Maven Wrapper incluido en el proyecto)
* Git

---

## Configuración de la Base de Datos

### 1. Eliminar la base de datos anterior

Debido a la migración a JPA, la estructura anterior de la base de datos ya no es compatible con la implementación actual.

Eliminar la base de datos existente.

### 2. Crear una nueva base de datos vacía

Ejemplo:

```sql
CREATE DATABASE lovelyshades;
```

Importante:

* No ejecutar scripts de creación de tablas.
* No ejecutar scripts antiguos del proyecto.

Las tablas serán generadas automáticamente por JPA.

---

## Configuración de conexión

Editar el archivo:

```text
src/main/resources/application-dev.properties
```

Configurar los datos de conexión según la instalación local:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=lovelyshades;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=TU_PASSWORD

spring.jpa.hibernate.ddl-auto=update
```

---

## Ejecutar el proyecto

Desde la carpeta del backend:

Windows:

```powershell
.\mvnw spring-boot:run
```

Linux/Mac:

```bash
./mvnw spring-boot:run
```

---

## Swagger

Una vez iniciado el backend:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## Endpoints disponibles

### Clientes

```text
/api/clientes
```

### Productos

```text
/api/productos
```

### Facturas

```text
/api/facturas
```

### Detalle Factura

```text
/api/detalle-facturas
```

### Inventario

```text
/api/inventarios
```

---

## Notas

* El backend fue migrado a Spring Data JPA.
* Las entidades actuales generan automáticamente la estructura de la base de datos.
* Los scripts SQL adicionales (índices, vistas, procedimientos almacenados y triggers) pueden volver a integrarse posteriormente si son necesarios.
* Swagger debe utilizarse para validar y probar los endpoints durante el desarrollo.
