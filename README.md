Servidor Transferencia 0.9
================================

Aplicación desarrollada en Java que implementa un servidor de transferencia de comandos mediante Sockets con 
funciones de logger. El programa permite al cliente conectarse con el servidor de forma concurrente, siendo 
posible la conexión de un número indeterminado de estos a un mismo servidor mientras hacemos uso de hilos.

La aplicación habilita diferentes interfaces gráficas tanto para el cliente como para el servidor, posibilitando
así las herramientas de autenticación necesarias para facilitar el correcto y fácil acceso al programa, una vez
introducidos éstos datos, la herramienta nos permite interactuar con el servidor a través del uso de diferentes
comandos introducidos por teclado.

Para acceder a las distintas opciones internas, usaremos como datos de acceso el nombre del fichero de texto 
(cualquiera de ellos) alojado en la carpeta 'src/recursos' del proyecto, y como clave utilizaremos el contenido
del mismo (lorem:ipsum y/o odei:riveiro), de forma paralela, se realiza un registro de todas las acciones realizadas
por el usuario en el servidor, dicho fichero se almacenará en la carpeta 'src/recursos' cada vez que cerremos el
servidor.

Para facilitar la puesta en marcha de la aplicación se proporcionan varios ejecutables .jar con el 
proyecto construido y listo para ser ejecutado de manera gráfica.

## Requisitos
- [Java] 7 o superior (para ejecutar la Aplicación)

## Entorno de desarrollo
La aplicación ha sido desarrollada utilizando el IDE [NetBeans] pero también es posible su importanción 
en [Eclipe] y demás IDE's.

## Licencia
Esta aplicación se ofrece bajo licencia [GPL versión 3].

[GPL versión 3]: https://www.gnu.org/licenses/gpl-3.0.en.html
[NetBeans]: https://netbeans.org/
[Eclipe]: https://eclipse.org/
[Java]: https://www.java.com/
