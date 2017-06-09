Servidor Transferencia 0.9
================================

Aplicaci�n desarrollada en Java que implementa un servidor de transferencia de comandos mediante Sockets con 
funciones de logger. El programa permite al cliente conectarse con el servidor de forma concurrente, siendo 
posible la conexi�n de un n�mero indeterminado de estos a un mismo servidor mientras hacemos uso de hilos.

La aplicaci�n habilita diferentes interfaces gr�ficas tanto para el cliente como para el servidor, posibilitando
as� las herramientas de autenticaci�n necesarias para facilitar el correcto y f�cil acceso al programa, una vez
introducidos �stos datos, la herramienta nos permite interactuar con el servidor a trav�s del uso de diferentes
comandos introducidos por teclado.

Para acceder a las distintas opciones internas, usaremos como datos de acceso el nombre del fichero de texto 
(cualquiera de ellos) alojado en la carpeta 'src/recursos' del proyecto, y como clave utilizaremos el contenido
del mismo (lorem:ipsum y/o odei:riveiro).

Para facilitar la puesta en marcha de la aplicaci�n se proporcionan varios ejecutables .jar con el 
proyecto construido y listo para ser ejecutado de manera gr�fica.

## Requisitos
- [Java] 7 o superior (para ejecutar la Aplicaci�n)

## Entorno de desarrollo
La Aplicaci�n ha sido desarrollada utilizando el IDE [NetBeans] pero tambi�n es posible su importanci�n 
en [Eclipe] y dem�s IDE's.

## Licencia
Esta aplicaci�n se ofrece bajo licencia [GPL versi�n 3].

[GPL versi�n 3]: https://www.gnu.org/licenses/gpl-3.0.en.html
[NetBeans]: https://netbeans.org/
[Eclipe]: https://eclipse.org/
[Java]: https://www.java.com/