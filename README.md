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
Configuración de Base de Datos y Conexión (DAO)
Requisitos
-SQL Server, Java JDK 11 o superior

1. Restaurar Base de Datos
-Abrir SQL Server Management Studio
-Click derecho en **Databases → Restore Database**
-Seleccionar el archivo `.bak o` proporcionado
-Nombrar la base de datos como: "lovelyshades"


2.Configuración de Conexión
La conexión a la base de datos se gestiona mediante la clase: "SqlServerConnectionProvider.java", esta clase utiliza JDBC para conectarse a SQL Server.
2.1 Parámetros de conexión:
-Servidor: "localhost"
-Puerto: "1433"
-Base de datos: "lovelyshades"
-Usuario: "sa" (o el configurado)
-Contraseña: (según instalación local)

3. Arquitectura DAO
El proyecto implementa el patrón *DAO + Adapter*, donde:
"Dao" → define las operaciones (interfaces)
"SqlServer Adapter" → implementa las consultas SQL
"ConnectionProvider" → centraliza la conexión a la BD


4 Funcionamiento
-El sistema solicita datos (ej: productos)
-Se utiliza un DAO (ej: "ProductoDao")
-El Adapter ejecuta consultas SQL
-La conexión se obtiene desde "connectionProvider"
-Se devuelven los datos al sistema


5. Prueba de funcionamiento Para validar la conexión:
-Ejecutar el proyecto
-Verificar que no existan errores de conexión
-Consultar datos desde la interfaz o endpoints

