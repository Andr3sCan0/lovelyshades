# 📋 Documentación - Sistema Lovely Shades

## ✅ Cambios Implementados

### 1. 🏷️ **Categorías y Marcas - Protección Admin**
- ✨ Las rutas para Categorías y Marcas ahora requieren autenticación + rol de administrador
- 📍 Rutas:
  - `/admin/categorias` - Gestión de categorías (Solo ADMIN)
  - `/admin/marcas` - Gestión de marcas (Solo ADMIN)
- 🔒 Protegidas con `authGuard` y `adminGuard`

### 2. 📄 **Módulo de Facturas** (NUEVO)
- ✨ Permite crear facturas asociadas a clientes
- ✨ Agregar múltiples productos a una factura (detalles)
- ✨ Cálculo automático de subtotal, IVA (19%) y total
- ✨ Aplicar descuentos
- ✨ Seleccionar medio de pago (Efectivo, Tarjeta, Transferencia, Cheque)
- ✨ Numerar facturas automáticamente
- 📍 Ruta: `/facturas` (Requiere autenticación)

**Funcionalidades:**
```
1. Crear nueva factura
   - Seleccionar cliente
   - Agregar uno o más productos con cantidades
   - Aplicar descuento opcional
   - Seleccionar medio de pago
   - El sistema calcula automáticamente el total

2. Ver listado de facturas
   - Buscar por número de factura
   - Ver detalles de cada factura
   - Editar o eliminar facturas
```

### 3. 📦 **Módulo de Inventario** (NUEVO)
- ✨ Controlar movimientos de inventario
- ✨ Registrar entrada, salida, ajuste y devolución de stock
- ✨ Validación automática de disponibilidad
- ✨ Historial completo de movimientos
- 📍 Ruta: `/inventario` (Requiere autenticación)

**Tipos de Movimiento:**
- **ENTRADA**: Agregar stock (compra, recepción)
- **SALIDA**: Retirar stock (venta, uso)
- **AJUSTE**: Corrección manual de stock
- **DEVOLUCION**: Stock devuelto por cliente

**Funcionalidades:**
```
1. Registrar movimiento
   - Seleccionar producto
   - Elegir tipo de movimiento
   - Ingresar cantidad
   - Sistema calcula stock antes y después
   - Agregar motivo y observaciones

2. Validaciones automáticas
   - No permite retirar más de lo disponible
   - Calcula correctamente el stock resultante
   - Registra fecha y usuario que realizó el movimiento

3. Ver historial
   - Buscar movimientos
   - Editar movimientos
   - Eliminar movimientos
```

---

## 📁 Estructura de Archivos Creados/Modificados

### Frontend (Angular)

**Nuevas Páginas:**
```
src/app/pages/
├── facturas/
│   ├── facturas.ts          (Componente TypeScript)
│   ├── facturas.html        (Template HTML)
│   └── facturas.scss        (Estilos)
├── inventario/
│   ├── inventario.ts        (Componente TypeScript)
│   ├── inventario.html      (Template HTML)
│   └── inventario.scss      (Estilos)
```

**Archivos Modificados:**
- `src/app/app.routes.ts` - Agregadas rutas protegidas para:
  - `/admin/categorias` (CategoriasComponent)
  - `/admin/marcas` (MarcasComponent)
  - `/facturas` (FacturasComponent)
  - `/inventario` (InventarioComponent)

**Modelos (Ya existían):**
- `src/app/models/factura.ts`
- `src/app/models/detalle-factura.ts`
- `src/app/models/inventario.ts`

**Servicios (Ya existían):**
- `src/app/services/factura.service.ts`
- `src/app/services/detalle-factura.service.ts`
- `src/app/services/inventario.service.ts`

### Backend (Spring Boot)

**Controladores (Ya existían):**
- `com.lovelyshades.controller.CategoriaController`
- `com.lovelyshades.controller.MarcaController`
- `com.lovelyshades.controller.FacturaController`
- `com.lovelyshades.controller.DetalleFacturaController`
- `com.lovelyshades.controller.InventarioController`

**Servicios Implementados:**
- `com.lovelyshades.service.impl.CategoriaServiceImpl`
- `com.lovelyshades.service.impl.MarcaServiceImpl`
- `com.lovelyshades.service.impl.FacturaServiceImpl`
- `com.lovelyshades.service.impl.DetalleFacturaServiceImpl`
- `com.lovelyshades.service.impl.InventarioServiceImpl`

---

## 🚀 Cómo Usar

### Acceder a Categorías y Marcas (Admin Only)
1. Inicia sesión con una cuenta de administrador
2. Ve a `/admin/categorias` o `/admin/marcas`
3. Crea, edita o elimina categorías/marcas según sea necesario

### Crear una Factura
1. Ve a `/facturas` en el menú
2. Selecciona un cliente
3. Haz clic en "Nuevo Movimiento de Inventario" para agregar productos
4. Para cada producto:
   - Selecciónalo del dropdown
   - Ajusta la cantidad
   - El precio unitario se completa automáticamente
   - Haz clic en "Agregar Producto"
5. Vuelve a repetir el paso anterior para agregar más productos si es necesario
6. Selecciona el medio de pago
7. (Opcional) Aplica un descuento
8. Haz clic en "Guardar Factura"

### Registrar Movimiento de Inventario
1. Ve a `/inventario` en el menú
2. Selecciona el producto
3. Elige el tipo de movimiento (ENTRADA, SALIDA, AJUSTE, DEVOLUCION)
4. Ingresa la cantidad
5. El sistema calcula automáticamente el stock resultante
6. (Opcional) Agrega un motivo y observaciones
7. Haz clic en "Registrar Movimiento"

---

## 🔐 Permisos y Acceso

| Módulo | Autenticación | Admin | Descripción |
|--------|---------------|-------|-------------|
| Categorías | ✅ | ✅ | Solo administradores |
| Marcas | ✅ | ✅ | Solo administradores |
| Facturas | ✅ | ❌ | Todos los usuarios autenticados |
| Inventario | ✅ | ❌ | Todos los usuarios autenticados |

---

## 🎨 Estilos y Diseño

Todos los módulos nuevos incluyen:
- Diseño responsive (mobile, tablet, desktop)
- Paleta de colores consistente
- Iconos emoji para mejor visualización
- Tablas interactivas con búsqueda
- Formularios con validación
- Botones de acción claros

---

## 📊 Flujo de Datos

### Factura → DetalleFactura → Inventario
```
1. Se crea una FACTURA
2. La factura contiene múltiples DETALLES FACTURA (productos)
3. Al registrar la venta, se crean movimientos de INVENTARIO (salida)
```

---

## ✨ Características Especiales

### En Facturas:
- ✅ Generación automática de número de factura
- ✅ Cálculo en tiempo real del IVA (19%)
- ✅ Validación de cliente seleccionado
- ✅ Validación de productos agregados
- ✅ Control de descuentos

### En Inventario:
- ✅ Cálculo automático de stock resultante
- ✅ Validación de stock disponible (en salidas)
- ✅ Historial completo de movimientos
- ✅ Registro de usuario y fecha
- ✅ Observaciones para auditoría

---

## 🐛 Troubleshooting

**Si no ves las rutas en el menú:**
- Asegúrate de agregar los enlaces en tu componente de navegación
- Verifica que estés autenticado
- Para admin, verifica que tu usuario tenga rol de administrador

**Si falta el medio de pago:**
- Selecciona una opción del dropdown

**Si no puedes registrar un movimiento:**
- Verifica que el producto esté disponible
- Verifica que la cantidad sea válida
- En SALIDA, verifica que haya stock disponible

---

## 📝 Próximas Mejoras Sugeridas

1. Integración con reportes de ventas
2. Exportación de facturas a PDF
3. Control de devoluciones automático
4. Auditoría de cambios en inventario
5. Notificaciones de stock bajo
6. Historial de cambios en productos

---

**Última actualización:** 30 de Mayo, 2026
**Versión:** 1.0.0
**Estado:** ✅ Completado
