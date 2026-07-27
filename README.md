# Servidor de Transferencia

Cliente-servidor en Java por sockets TCP que simula un mini-FTP por comandos: login, listado de directorio y lectura de ficheros remotos, con registro de actividad.

## Características

- Autenticación remota: el cliente envía usuario y contraseña al servidor, que los valida contra un fichero `usuario.txt` en `src/recursos/` (usuario = nombre del fichero, contraseña = su contenido; p. ej. `lorem`/`ipsum` u `odei`/`riveiro`).
- Comando `DIR`: solicita al servidor el listado de ficheros del directorio `src/recursos/tmp/`.
- Comando `TYPE <fichero>`: solicita al servidor el contenido de un fichero de texto concreto dentro de `tmp/`.
- Comando `EXIT`: cierra la sesión del cliente de forma ordenada.
- Registro (log) de toda la actividad del servidor (accesos aceptados/denegados, listados, lecturas, salidas) con fecha y hora, exportado a un fichero `.log` con marca de tiempo al cerrar el servidor.
- Servidor multi-hilo (`Servidor extends Thread`) capaz de atender conexiones concurrentes de distintos clientes.
- Interfaces gráficas independientes para cliente (login + envío de comandos) y servidor (visualización del log en tiempo real).

## Tecnologías

- Java (`java.net.Socket`, `java.net.ServerSocket`, streams de E/S, hilos con `Thread`)
- Swing / AWT para las interfaces gráficas de cliente y servidor
- Apache Ant + NetBeans (`build.xml`, estructura de proyecto NetBeans)

## Instalación / Cómo ejecutarlo

**Opción rápida (ejecutables ya compilados):**
1. Arranca primero el servidor:
   ```
   java -jar dist/Servidor.jar
   ```
2. En otra terminal (o equipo, ajustando `Servidor.HOST`), arranca el cliente:
   ```
   java -jar dist/Cliente.jar
   ```
3. Autentícate con un usuario de `src/recursos` (p. ej. `lorem` / `ipsum`) y prueba los comandos `DIR`, `TYPE <fichero>` y `EXIT`.

**Compilando desde el código fuente:**
1. Abre el proyecto con NetBeans (o cualquier IDE compatible con Ant), o compílalo con `ant` desde la raíz del proyecto usando el `build.xml` incluido.
2. Ejecuta primero la clase `Servidor` y después la clase `Cliente`.

Requiere Java 7 o superior.

Ejercicio académico que practica la comunicación cliente-servidor con sockets TCP en Java, implementando un protocolo de comandos propio y registro de actividad (logging) en el servidor.

## Licencia

GPL versión 3 (ver archivo [LICENSE](LICENSE)).
