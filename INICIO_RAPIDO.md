# 🚀 Guía de Inicio Rápido - Lovely Shades

## ⚡ 5 Minutos para Estar Funcionando

### Paso 1: Compilar el Backend (Java)

```powershell
cd c:\Proyectos\backup\Andres\lovelyshades\lovelyshades
mvn clean compile
mvn spring-boot:run
```

**Esperado:** Servidor iniciando en `http://localhost:8080`

---

### Paso 2: Preparar el Frontend (Angular)

```powershell
cd c:\Proyectos\backup\Andres\lovelyshades\front\lovelyshades-front
npm install
ng serve
```

**Esperado:** Angular sirviendo en `http://localhost:4200`

---

### Paso 3: Acceder a la Aplicación

1. Abre el navegador
2. Ve a `http://localhost:4200`
3. Inicia sesión con tus credenciales

---

## 🧪 Prueba Rápida de Funcionalidades

### Prueba 1: Crear Categoría (Admin)
```
1. Inicia sesión como ADMIN
2. Ve a /admin/categorias
3. Completa el formulario
4. Haz clic en "Guardar"
✅ Categoría creada
```

### Prueba 2: Crear Factura (User)
```
1. Inicia sesión como USER cualquiera
2. Ve a /facturas
3. Selecciona un cliente
4. Agrega 1+ productos
5. Selecciona medio de pago
6. Haz clic en "Guardar Factura"
✅ Factura creada
```

### Prueba 3: Registrar Movimiento de Inventario (User)
```
1. Ve a /inventario
2. Selecciona un producto
3. Elige tipo "SALIDA"
4. Ingresa cantidad
5. Haz clic en "Registrar Movimiento"
✅ Movimiento registrado
```

---

## 🔧 Troubleshooting Rápido

### Error: "Cannot GET /admin/categorias"
**Solución:** No estás autenticado o tu usuario no es ADMIN
- Verifica que iniciaste sesión
- Verifica que tu usuario tenga rol ADMIN

### Error: "CORS error"
**Solución:** El backend no está ejecutándose
- Verifica que ejecutaste `mvn spring-boot:run`
- Verifica que está en puerto 8080

### Error: "Productos no se cargan"
**Solución:** No hay productos en la BD
- Crea algunos productos primero en `/productos`

### Error: "Stock insuficiente"
**Solución:** El producto no tiene stock
- Registra una ENTRADA de inventario primero

---

## 📊 Usuarios de Prueba Sugeridos

Asume que tienes estos usuarios en la BD:

```
Usuario: admin
Rol: ADMIN
Permisos: Todas las rutas

Usuario: vendedor
Rol: USER
Permisos: Facturas, Inventario

Usuario: gestor
Rol: USER
Permisos: Facturas, Inventario
```

---

## 🗂️ Estructura de Carpetas (Referencia Rápida)

### Frontend
```
src/
├── app/
│   ├── pages/
│   │   ├── facturas/      ⭐ NUEVO
│   │   ├── inventario/    ⭐ NUEVO
│   │   ├── categorias/
│   │   └── marcas/
│   ├── services/
│   ├── guards/
│   └── models/
└── assets/
```

### Backend
```
src/main/java/com/lovelyshades/
├── controller/
│   ├── FacturaController        ⭐
│   ├── DetalleFacturaController ⭐
│   ├── InventarioController     ⭐
│   └── ...
├── service/
│   ├── impl/
│   │   ├── FacturaServiceImpl
│   │   ├── DetalleFacturaServiceImpl
│   │   ├── InventarioServiceImpl
│   │   └── ...
├── entity/
├── repository/
└── config/
```

---

## 🎯 Checklist de Verificación

- [ ] Backend compilado sin errores
- [ ] Frontend compilado sin errores
- [ ] Usuario ADMIN creado en BD
- [ ] Algunos clientes creados
- [ ] Algunos productos creados
- [ ] Servidor Spring Boot corriendo
- [ ] Angular sirviendo sin errores
- [ ] Puedes iniciar sesión
- [ ] Puedes acceder a /admin/categorias (como ADMIN)
- [ ] Puedes acceder a /facturas (como USER)
- [ ] Puedes crear una factura
- [ ] Puedes registrar un movimiento de inventario

---

## 💡 Tips Importantes

1. **JWT Token**
   - Se almacena en localStorage
   - Se incluye automáticamente en cada request
   - No expira durante la sesión

2. **Validaciones**
   - Frontend valida antes de enviar
   - Backend valida nuevamente
   - Ambas capas son importantes

3. **Cálculos**
   - Totales se calculan en tiempo real
   - Stock se recalcula automáticamente
   - Todo es bidireccional

4. **Seguridad**
   - Cambiar puerto 8080 en producción
   - Cambiar URL de backend en producción
   - Implementar HTTPS
   - Configurar CORS correctamente

---

## 📞 Endpoints Principales

| Método | URL | Descripción |
|--------|-----|-------------|
| POST | /api/auth/login | Iniciar sesión |
| GET | /api/facturas | Listar facturas |
| POST | /api/facturas | Crear factura |
| GET | /api/inventarios | Listar inventario |
| POST | /api/inventarios | Registrar movimiento |
| GET | /api/categorias | Listar categorías (Admin) |
| POST | /api/categorias | Crear categoría (Admin) |
| GET | /api/marcas | Listar marcas (Admin) |
| POST | /api/marcas | Crear marca (Admin) |

---

## 🎓 Próximas Lecciones Recomendadas

1. **Tests Unitarios**
   - JUnit para backend
   - Jasmine para frontend

2. **Tests E2E**
   - Cypress o Selenium

3. **Deployment**
   - Docker
   - Docker Compose
   - Heroku/AWS

4. **Monitoreo**
   - Logging
   - Métricas
   - Alertas

---

## 📚 Documentación Relacionada

- **RESUMEN_IMPLEMENTACION.md** - Descripción completa de cambios
- **DOCUMENTACION_CAMBIOS.md** - Guía detallada de funcionalidades
- **ARQUITECTURA_SISTEMA.md** - Diagrama técnico de la arquitectura

---

**¡Estás listo para comenzar! 🎉**

Si tienes preguntas, consulta la documentación completa.

---

**Última actualización:** 30 de Mayo, 2026
**Versión:** 1.0.0
