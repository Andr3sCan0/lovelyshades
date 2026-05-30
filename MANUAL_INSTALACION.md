# Manual de Instalación y Configuración - LovelyShades

## 1. Requisitos previos

- Java 21
- Maven (puede usarse el Maven Wrapper incluido)
- Node.js 24+
- SQL Server instalado y accesible
- Git (opcional para clonar el repositorio)

## 2. Estructura del proyecto

El proyecto se divide en dos carpetas principales:

- `lovelyshades/` → Backend Spring Boot
- `front/lovelyshades-front/` → Frontend Angular

## 3. Configuración de la base de datos

### 3.1. Crear la base de datos

1. Abre SQL Server Management Studio (SSMS) o usa `sqlcmd`.
2. Crea la base de datos `lovelyshades` si no existe:

```sql
CREATE DATABASE lovelyshades;
GO
```

### 3.2. Configurar la conexión en Spring Boot

El perfil activo está en `lovelyshades/src/main/resources/application.properties`:

```properties
spring.profiles.active=dev
```

El archivo de configuración de conexión es:

`lovelyshades/src/main/resources/application-dev.properties`

Contenido actual:

```properties
spring.application.name=lovelyshades

sqlserver.datasource.url=jdbc:sqlserver://localhost;instanceName=SQL2025;databaseName=lovelyshades;encrypt=true;trustServerCertificate=true
sqlserver.datasource.username=sa
sqlserver.datasource.password=12345
sqlserver.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

spring.datasource.url=jdbc:sqlserver://localhost;instanceName=SQL2025;databaseName=lovelyshades;encrypt=true;trustServerCertificate=true
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.username=sa
spring.datasource.password=12345
spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.jpa.show-sql=true

springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

### 3.3. Ajustar credenciales

Modifica estos valores según tu servidor SQL Server local:

- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`

Si no usas la instancia `SQL2025`, cambia la URL a algo como:

```properties
spring.datasource.url=jdbc:sqlserver://localhost;databaseName=lovelyshades;encrypt=true;trustServerCertificate=true
```

### 3.4. Crear tablas iniciales

Actualmente el modo JPA es `validate`, lo que significa que las tablas deben existir antes de ejecutar el backend.

Si arrancas por primera vez y no hay estructura, cambia temporalmente:

```properties
spring.jpa.hibernate.ddl-auto=update
```

Arranca el backend una vez para que JPA genere tablas.

Después vuelve a dejar:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

## 4. Script de inicialización de datos

El script se encuentra en `scripts/init_db.sql`.

### 4.1. Qué hace

- Crea los roles base: `ADMIN`, `USER`, `CLIENTE`
- Crea un usuario administrador inicial
- Inserta datos base de trabajo:
  - Categoría de ejemplo
  - Marca de ejemplo
  - Cliente de ejemplo
  - Producto de ejemplo

### 4.2. Cómo ejecutar el script

Con SQL Server Management Studio:

1. Abre `scripts/init_db.sql`
2. Ejecuta el script en la base de datos `lovelyshades`

Con `sqlcmd`:

```powershell
sqlcmd -S localhost\SQL2025 -U sa -P 12345 -i "c:\Proyectos\backup\Andres\lovelyshades\scripts\init_db.sql"
```

Si tu instancia es la predeterminada usa:

```powershell
sqlcmd -S localhost -U sa -P 12345 -i "c:\Proyectos\backup\Andres\lovelyshades\scripts\init_db.sql"
```

## 5. Ejecución del backend

Desde la carpeta `lovelyshades/`:

```powershell
cd c:\Proyectos\backup\Andres\lovelyshades\lovelyshades
.\mvnw.cmd clean spring-boot:run
```

O para compilar primero:

```powershell
.\mvnw.cmd clean compile
.\mvnw.cmd spring-boot:run
```

### 5.1. Verificar swagger

Una vez iniciado el backend, abre:

```text
http://localhost:8080/swagger-ui/index.html
```

## 6. Ejecución del frontend

Desde la carpeta `front/lovelyshades-front/`:

```powershell
cd c:\Proyectos\backup\Andres\lovelyshades\front\lovelyshades-front
npm install
npm run build
npm start
```

Si prefieres servir en desarrollo:

```powershell
ng serve
```

Luego abre:

```text
http://localhost:4200
```

## 7. Usuario administrador inicial

El script crea este administrador inicial:

- Email: `admin@lovelyshades.com`
- Contraseña: `admin123`
- Nombre de usuario: `admin`
- Rol: `ADMIN`


## 8. Instrucciones de login

El inicio de sesión usa el email y la contraseña.

Payload de ejemplo para el login HTTP:

```json
{
  "email": "admin@lovelyshades.com",
  "password": "admin123"
}
```

## 9. Componentes instalados

### Backend

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security
- SQL Server Driver
- Swagger (SpringDoc)

### Frontend

- Angular 21
- RxJS
- TypeScript
- Express (SSR)

## 10. Resumen rápido de comandos

```powershell
# Backend
cd c:\Proyectos\backup\Andres\lovelyshades\lovelyshades
.\mvnw.cmd clean compile
.\mvnw.cmd spring-boot:run

# Frontend
cd c:\Proyectos\backup\Andres\lovelyshades\front\lovelyshades-front
npm install
npm run build
npm start
```

## 11. Notas finales

- Si ves errores de validación de JPA, asegúrate de que la base de datos existe y de que el modo `ddl-auto` sea `update` durante la primera ejecución.
- Si el backend no encuentra SQL Server, verifica la URL y el nombre de la instancia.
- Si no puedes acceder al frontend, revisa que el backend esté corriendo en `http://localhost:8080`.
