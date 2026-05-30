# 🏗️ Arquitectura del Sistema - Lovely Shades

## 📐 Diagrama General del Sistema

```
┌─────────────────────────────────────────────────────────────────┐
│                    LOVELY SHADES SYSTEM                         │
│                                                                 │
│                      Angular Frontend                           │
│                    (TypeScript/Standalone)                      │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │         COMPONENTES (Standalone)                         │  │
│  │                                                          │  │
│  │  ├─ Home Component                                       │  │
│  │  ├─ Login Component                                      │  │
│  │  ├─ Clientes Component                                   │  │
│  │  ├─ Productos Component                                  │  │
│  │  ├─ Categorías Component (Admin Only) ⭐                  │  │
│  │  ├─ Marcas Component (Admin Only) ⭐                      │  │
│  │  ├─ Facturas Component (New) ⭐                           │  │
│  │  └─ Inventario Component (New) ⭐                         │  │
│  │                                                          │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │              SERVICIOS (HttpClient)                      │  │
│  │                                                          │  │
│  │  ├─ AuthService        (Auth & JWT)                      │  │
│  │  ├─ ClienteService     (CRUD)                            │  │
│  │  ├─ ProductoService    (CRUD)                            │  │
│  │  ├─ CategoriaService   (CRUD)                            │  │
│  │  ├─ MarcaService       (CRUD)                            │  │
│  │  ├─ FacturaService     (CRUD) ⭐                          │  │
│  │  ├─ DetalleFacturaService (CRUD) ⭐                       │  │
│  │  └─ InventarioService  (CRUD) ⭐                          │  │
│  │                                                          │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │         GUARDS (Seguridad en Rutas)                      │  │
│  │                                                          │  │
│  │  ├─ authGuard         (Requiere autenticación)          │  │
│  │  └─ adminGuard        (Requiere rol ADMIN)              │  │
│  │                                                          │  │
│  └──────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                              ↓ HTTP ↓
                    API REST (Base: localhost:8080)
                              ↓ ↑
┌─────────────────────────────────────────────────────────────────┐
│               Spring Boot Backend (Java 21)                     │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │            CONTROLLERS (REST Endpoints)                  │  │
│  │                                                          │  │
│  │  POST/GET/PUT/DELETE /api/categorias                     │  │
│  │  POST/GET/PUT/DELETE /api/marcas                         │  │
│  │  POST/GET/PUT/DELETE /api/facturas                       │  │
│  │  POST/GET/PUT/DELETE /api/detalle-facturas              │  │
│  │  POST/GET/PUT/DELETE /api/inventarios                   │  │
│  │  POST/GET/PUT/DELETE /api/clientes                      │  │
│  │  POST/GET/PUT/DELETE /api/productos                     │  │
│  │                                                          │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │          BUSINESS LOGIC (Services)                       │  │
│  │                                                          │  │
│  │  ├─ CategoriaService        (extends AbstractCrudService)  │
│  │  ├─ MarcaService            (extends AbstractCrudService)  │
│  │  ├─ FacturaService          (extends AbstractCrudService)  │
│  │  ├─ DetalleFacturaService   (extends AbstractCrudService)  │
│  │  ├─ InventarioService       (extends AbstractCrudService)  │
│  │  ├─ ClienteService          (extends AbstractCrudService)  │
│  │  ├─ ProductoService         (extends AbstractCrudService)  │
│  │  ├─ AuthService             (JWT + Security)               │
│  │  └─ UsuarioService          (User Management)              │
│  │                                                          │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │         DATA ACCESS (Repositories)                       │  │
│  │                                                          │  │
│  │  ├─ CategoriaRepository      (JpaRepository)            │  │
│  │  ├─ MarcaRepository          (JpaRepository)            │  │
│  │  ├─ FacturaRepository        (JpaRepository)            │  │
│  │  ├─ DetalleFacturaRepository (JpaRepository)            │  │
│  │  ├─ InventarioRepository     (JpaRepository)            │  │
│  │  ├─ ClienteRepository        (JpaRepository)            │  │
│  │  ├─ ProductoRepository       (JpaRepository)            │  │
│  │  ├─ UsuarioRepository        (JpaRepository)            │  │
│  │  └─ RolRepository            (JpaRepository)            │  │
│  │                                                          │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │     ENTITIES (JPA/Hibernate)                             │  │
│  │                                                          │  │
│  │  ├─ Categoria                                           │  │
│  │  ├─ Marca                                               │  │
│  │  ├─ Factura        (1 → M DetalleFactura)              │  │
│  │  ├─ DetalleFactura (M ← 1 Factura, M ← 1 Producto)   │  │
│  │  ├─ Inventario                                          │  │
│  │  ├─ Producto       (M → 1 Categoria, M → 1 Marca)     │  │
│  │  ├─ Cliente                                             │  │
│  │  ├─ Usuario        (M → 1 Rol)                          │  │
│  │  └─ Rol                                                  │  │
│  │                                                          │  │
│  └──────────────────────────────────────────────────────────┘  │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │    CONFIGURATION (Spring Boot Config)                    │  │
│  │                                                          │  │
│  │  ├─ SecurityConfig         (JWT + Authorization)        │  │
│  │  ├─ CorsConfig             (Cross-Origin)               │  │
│  │  ├─ SwaggerConfig          (OpenAPI 3.0)                │  │
│  │  └─ JwtAuthenticationFilter (Token Validation)          │  │
│  │                                                          │  │
│  └──────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                              ↓ JDBC ↓
                    SQL Server Database
                              ↓ ↑
┌─────────────────────────────────────────────────────────────────┐
│               DATABASE (SQL Server)                             │
│                                                                 │
│  Tablas:                                                        │
│  ├─ Categoria                                                   │
│  ├─ Marca                                                       │
│  ├─ Factura           (Relación: 1 → M DetalleFactura)       │
│  ├─ DetalleFactura    (Relación: M ← 1 Factura)              │
│  │                    (Relación: M ← 1 Producto)             │
│  ├─ Inventario        (Relación: M ← 1 Producto)             │
│  │                    (Relación: M ← 1 Factura)              │
│  ├─ Producto          (Relación: M ← 1 Categoria)            │
│  │                    (Relación: M ← 1 Marca)                │
│  ├─ Cliente                                                     │
│  ├─ Usuario           (Relación: M ← 1 Rol)                  │
│  ├─ Rol                                                         │
│  ├─ Permiso                                                     │
│  └─ RolPermiso        (Relación: M ← 1 Rol)                  │
│                       (Relación: M ← 1 Permiso)              │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## 📊 Flujo de Datos - Factura

```
┌──────────────────────┐
│  Usuario en Browser  │
│   (Angular App)      │
└──────────┬───────────┘
           │ 1. Click "Crear Factura"
           ↓
┌──────────────────────────┐
│  FacturasComponent       │
│  - Selecciona cliente    │
│  - Agrega productos      │
│  - Calcula totales       │
└──────────┬───────────────┘
           │ 2. POST /api/facturas
           ↓
┌──────────────────────────┐
│  FacturaController       │
│  @PostMapping("/")       │
└──────────┬───────────────┘
           │ 3. Call crear()
           ↓
┌──────────────────────────┐
│  FacturaService          │
│  - Validación            │
│  - Lógica de negocio     │
└──────────┬───────────────┘
           │ 4. repository.save()
           ↓
┌──────────────────────────┐
│  FacturaRepository       │
│  (JpaRepository)         │
└──────────┬───────────────┘
           │ 5. JDBC INSERT
           ↓
┌──────────────────────────┐
│  SQL Server              │
│  INSERT INTO Factura     │
│  INSERT INTO DetalleFactura │
└──────────┬───────────────┘
           │ 6. Response OK
           ↓
┌──────────────────────────┐
│  FacturaController       │
│  Returns Factura object  │
└──────────┬───────────────┘
           │ 7. JSON Response
           ↓
┌──────────────────────────┐
│  FacturasComponent       │
│  - Recibe respuesta      │
│  - Actualiza lista       │
│  - Muestra confirmación  │
└──────────────────────────┘
```

---

## 📦 Flujo de Datos - Inventario

```
┌──────────────────────┐
│  Usuario en Browser  │
│   (Angular App)      │
└──────────┬───────────┘
           │ 1. Click "Registrar Movimiento"
           ↓
┌──────────────────────────────┐
│  InventarioComponent         │
│  - Selecciona producto       │
│  - Elige tipo movimiento     │
│  - Ingresa cantidad          │
│  - Valida stock disponible   │
└──────────┬──────────────────┘
           │ 2. POST /api/inventarios
           ↓
┌──────────────────────────┐
│  InventarioController    │
│  @PostMapping("/")       │
└──────────┬───────────────┘
           │ 3. Call crear()
           ↓
┌──────────────────────────┐
│  InventarioService       │
│  - Validación de stock   │
│  - Cálculo de resultante │
│  - Lógica de negocio     │
└──────────┬───────────────┘
           │ 4. repository.save()
           │ 5. Actualizar Producto.stock
           ↓
┌──────────────────────────┐
│  InventarioRepository    │
│  ProductoRepository      │
│  (JpaRepository)         │
└──────────┬───────────────┘
           │ 6. JDBC INSERT/UPDATE
           ↓
┌──────────────────────────┐
│  SQL Server              │
│  INSERT INTO Inventario  │
│  UPDATE Producto         │
└──────────┬───────────────┘
           │ 7. Response OK
           ↓
┌──────────────────────────┐
│  InventarioController    │
│  Returns Inventario      │
└──────────┬───────────────┘
           │ 8. JSON Response
           ↓
┌──────────────────────────┐
│  InventarioComponent     │
│  - Recibe respuesta      │
│  - Actualiza lista       │
│  - Muestra confirmación  │
└──────────────────────────┘
```

---

## 🔐 Flujo de Autenticación y Autorización

```
┌─────────────────────┐
│  Usuario: Login     │
└────────────┬────────┘
             │ 1. POST /api/auth/login
             │    {username, password}
             ↓
┌─────────────────────────────┐
│  AuthController             │
│  @PostMapping("/login")     │
└────────────┬────────────────┘
             │ 2. Verificar credenciales
             ↓
┌─────────────────────────────┐
│  AuthService                │
│  - Buscar usuario en BD     │
│  - Verificar password       │
│  - Generar JWT Token        │
└────────────┬────────────────┘
             │ 3. Return Token
             ↓
┌─────────────────────────────┐
│  Frontend recibe JWT        │
│  - Almacena en localStorage │
│  - Incluye en cada request  │
└────────────┬────────────────┘
             │ 4. Request a /api/facturas
             │    {Authorization: Bearer <token>}
             ↓
┌─────────────────────────────┐
│  JwtAuthenticationFilter    │
│  - Extrae token             │
│  - Valida firma             │
│  - Obtiene Usuario          │
└────────────┬────────────────┘
             │ 5. SecurityContext.setAuth()
             ↓
┌─────────────────────────────┐
│  FacturaController          │
│  @GetMapping("/")           │
│  (authGuard aplicado)       │
└────────────┬────────────────┘
             │ 6. Ejecutar endpoint
             ↓
┌─────────────────────────────┐
│  Response: 200 OK           │
│  Datos de la factura        │
└─────────────────────────────┘

SI ACCESA /admin/categorias:
┌─────────────────────────────┐
│  CategoriaController        │
│  (authGuard + adminGuard)   │
└────────────┬────────────────┘
             │ Verificar rol
             ↓
    ¿Tiene rol ADMIN?
        /  \
       /    \
      SI     NO
     /         \
    ↓           ↓
 Permitir   Redirect a /
 acceso      (403 Forbidden)
```

---

## 🌐 Endpoints API REST

### Categorías
```
POST   /api/categorias              → Crear categoría
GET    /api/categorias              → Listar todas
GET    /api/categorias/{id}         → Obtener por ID
PUT    /api/categorias/{id}         → Actualizar
DELETE /api/categorias/{id}         → Eliminar
```

### Marcas
```
POST   /api/marcas                  → Crear marca
GET    /api/marcas                  → Listar todas
GET    /api/marcas/{id}             → Obtener por ID
PUT    /api/marcas/{id}             → Actualizar
DELETE /api/marcas/{id}             → Eliminar
```

### Facturas
```
POST   /api/facturas                → Crear factura
GET    /api/facturas                → Listar todas
GET    /api/facturas/{id}           → Obtener por ID
PUT    /api/facturas/{id}           → Actualizar
DELETE /api/facturas/{id}           → Eliminar
```

### Detalle Facturas
```
POST   /api/detalle-facturas        → Crear detalle
GET    /api/detalle-facturas        → Listar todos
GET    /api/detalle-facturas/{id}   → Obtener por ID
PUT    /api/detalle-facturas/{id}   → Actualizar
DELETE /api/detalle-facturas/{id}   → Eliminar
```

### Inventario
```
POST   /api/inventarios             → Crear movimiento
GET    /api/inventarios             → Listar todos
GET    /api/inventarios/{id}        → Obtener por ID
PUT    /api/inventarios/{id}        → Actualizar
DELETE /api/inventarios/{id}        → Eliminar
```

---

## 📋 Relaciones de Base de Datos

```
Factura (1) ──────── (M) DetalleFactura
  ↑                      ↓
  │                  Producto
  │
Cliente


Producto (M) ──── (1) Categoria
   │
   └─ (M) ──── (1) Marca


Inventario (M) ──── (1) Producto
   │
   └─ (M) ──── (1) Factura (opcional)


Usuario (M) ──── (1) Rol


RolPermiso (M) ──── (1) Rol
   │
   └─ (M) ──── (1) Permiso
```

---

## 🎯 Matriz de Acceso (RBAC)

| Recurso | Anónimo | USER | ADMIN |
|---------|---------|------|-------|
| Home | ✅ | ✅ | ✅ |
| Login | ✅ | ✅ | ✅ |
| Perfil | ❌ | ✅ | ✅ |
| Clientes | ❌ | ✅ | ✅ |
| Productos | ❌ | ✅ | ✅ |
| Categorías | ❌ | ❌ | ✅ |
| Marcas | ❌ | ❌ | ✅ |
| Facturas | ❌ | ✅ | ✅ |
| Inventario | ❌ | ✅ | ✅ |
| Roles | ❌ | ❌ | ✅ |
| Usuarios | ❌ | ❌ | ✅ |

---

## 🚀 Stack Tecnológico

### Frontend
- **Framework:** Angular 21+
- **Lenguaje:** TypeScript
- **Estilos:** SCSS
- **HTTP:** HttpClient
- **Autenticación:** JWT Token
- **Guards:** authGuard, adminGuard

### Backend
- **Framework:** Spring Boot 3.5.13
- **Lenguaje:** Java 21
- **Base de Datos:** SQL Server
- **ORM:** Hibernate/JPA
- **API:** REST con Swagger/OpenAPI 3.0
- **Seguridad:** Spring Security + JWT
- **Build:** Maven

### Herramientas
- **IDE Frontend:** Visual Studio Code
- **IDE Backend:** IntelliJ/Eclipse
- **Control Versiones:** Git
- **Testing:** JUnit + Jasmine (recomendado)

---

**Arquitectura lista para escalabilidad y mantenimiento.** ✨
