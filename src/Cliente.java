import java.io.*;
import java.net.*;

/**
 * Clase Cliente. 
 * Se encarga de generar los Procedimientos que usaremos para implementar 
 * los métodos de control usados para el Cliente de la aplicación.
 *
 * @author Odei
 * @version 24.03.2016
 */
public class Cliente {        
    /**
     * Variable Socket estática usada para almacenar la Conexión que el
     * Cliente utilizará para enlazar con el Servidor durante la conexión.
     */
    protected static Socket sCliente;    
    
    /**
     * Variable cadena que almacena el nombre de Usuario autenticado.
     */
    protected static String user;    
    
    /**
     * Constructor de la Clase Cliente.
     * Genera e inicializa los elementos utilizados para gestionar la
     * ejecución del Usuario haciendo las veces de Cliente.
     */
    public Cliente() {
        Login_Interfaz login = new Login_Interfaz();                            // Lanzamos una Instancia de la Pantalla de Login para el Cliente
    }
    
    /**
     * Método usado para procesar las Conexiones que el Cliente 
     * realiza para conectarse al Servidor.
     */
    protected static void procesarConexion() {
        try {
            sCliente = new Socket(Servidor.HOST, Servidor.Puerto);              // Nos conectamos al Servidor por el puerto preestablecido
            OutputStream os = sCliente.getOutputStream();
            DataOutputStream flujo_salida = new DataOutputStream(os);
            flujo_salida.writeUTF(Login_Interfaz.tfUser.getText()+"*"+
                                  Login_Interfaz.tfPass.getText());             // Y la mandamos mediante un flujo de salida los datos de conexión del Usuario
            InputStream is = sCliente.getInputStream();
            DataInputStream flujo_entrada = new DataInputStream(is);
            String resultado = flujo_entrada.readUTF();                         // Capturamos el flujo de datos enviado desde el Servidor con el resultado
            Login_Interfaz.generarAudio(resultado);                             // Generamos un sonido con el resultado obtenido
            if (resultado.equals("ok")) {                                       // Si los datos son correctos
                user = Login_Interfaz.tfUser.getText();
                Cliente_Interfaz cliente = 
                        new Cliente_Interfaz(user);                             // Lanzamos una instancia de la nueva Interfaz para el Usuario y
                Login_Interfaz.frame.dispose();                                 // cerramos el frame con la ventana de Login
            } else {
                Login_Interfaz.lanzarError("error");                            // en caso contrario mostramos una imágen de error de acceso
            }
        } catch(IOException | NumberFormatException e) {                        // Capturamos cualquier excepción
            Login_Interfaz.lanzarError("error2");                               // y dado el caso mostramos una imágen de aviso
        }
    }
    
    /**
     * Método usado para procesar los Comandos que el Usuario
     * introduce para interactuar con el Servidor.
     * 
     * @param comando String: variable que contiene el comando a ejecutar
     */
    protected static void procesarComando(String comando) {
        try {
            sCliente = new Socket(Servidor.HOST, Servidor.Puerto);              // Nos conectamos al Servidor por el puerto preestablecido
            OutputStream os = sCliente.getOutputStream();
            DataOutputStream flujo_salida = new DataOutputStream(os);
            flujo_salida.writeUTF(user+"*"+comando);                            // Y la mandamos mediante un flujo de salida los datos del comando ejecutado
            InputStream is = sCliente.getInputStream();
            DataInputStream flujo_entrada = new DataInputStream(is);
            String res = flujo_entrada.readUTF();                               // Capturamos el flujo de datos enviado desde el Servidor con el resultado
            if (res.equals("-1")) {                                             // Si devuelve -1 procesamos la salida del Usuario
                Cliente_Interfaz.frame.dispose();                               // cerrando el frame con la ventana de Usuario y
                sCliente.close();                                               // también el socket del Cliente
            } else if (res.substring(0,res.indexOf("*")).equals("1")) {         // Si devuelve 1 procesamos el listado de ficheros recibido
                Cliente_Interfaz.taMensajes.setText("Listado de Ficheros " +
                    "Disponibles\n\n" + res.substring(res.indexOf("*")+1));     // mostrando el mensaje pertinente en la Interfaz de Usuario
            } else {                                                            // En caso contrario procesamos el contenido del fichero
                String aux = res.substring(res.indexOf("*")+1);
                if (aux.equals("")) {                                           // Si no se devuelven datos el fichero no existe y mostramos un mensaje
                    Cliente_Interfaz.taMensajes.setText("El Fichero no existe.");
                } else {                                                        // en caso contrario mostramos el contenido del mismo
                    Cliente_Interfaz.taMensajes.setText(
                            "Contenido de Fichero\n\n" + aux);
                }
            }
        } catch(IOException | NumberFormatException e) {                        // Capturamos cualquier excepción y la mostramos dado el caso
            if (e.getMessage().equals("Connection refused: connect")) {
                Cliente_Interfaz.taMensajes.setText("El Servidor no está arrancado");
            } else {
                Cliente_Interfaz.taMensajes.setText(e.getMessage());
            }
        }
    }
    
    /**
     * Método Principal de la Clase Cliente.
     * Lanza una Instancia del Programa llamando al Constructor del Programa.
     * 
     * @param args String[]: argumentos de la línea de comandos
     */
    public static void main( String[] args ) {
        Cliente cliente = new Cliente();                                        // Creamos una Instancia del Programa para el Cliente
    }
}