# Console Launcher

Launcher Android en desarrollo, con una interfaz de paneles horizontales inspirada en Xbox 360 y Xbox Series X|S.

## Estado actual — 0.1.0

- Proyecto Android nativo con Kotlin y Jetpack Compose.
- Inicio con panel destacado y accesos a secciones.
- Navegación entre Inicio, Juegos, Aplicaciones y Configuración.
- Listado de aplicaciones que Android expone como iniciables y apertura al seleccionarlas.
- Guía rápida lateral.
- Selector de color de acento.
- Declaración de actividad HOME para poder probarlo como launcher predeterminado.

## Abrir

Abre este repositorio con Android Studio (Giraffe o posterior), espera la sincronización de Gradle y ejecuta la configuración `app` en un dispositivo Android.

## Pendiente

1. Guardar preferencias y orden de paneles.
2. Biblioteca persistente de juegos.
3. Integración configurable con emuladores.
4. Carátulas y metadatos.
5. Navegación completa con gamepad.
6. Diseño específico para doble pantalla de AYN Thor.
7. Pruebas y generación de APK mediante GitHub Actions.

## Compatibilidad

La base usa minSdk 26. La visibilidad de aplicaciones depende de las reglas de package visibility de Android. La segunda pantalla de AYN Thor requerirá comprobar las APIs que ofrece el fabricante.

Este repositorio es un prototipo de desarrollo; todavía no es una versión estable ni un APK publicado.
