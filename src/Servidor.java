import java.io.* ;
import java.net.* ;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase Servidor que extiende de Thread.
 * Se encarga de generar los Procedimientos que usaremos para implementar 
 * los métodos de control usados para el Servidor de la aplicación.
 *
 * @author Odei
 * @version 24.03.2016
 */
public class Servidor extends Thread {    
    /**
     * Variable entera usada para almacenar el Puerto de Conexión a la escucha.
     */
    protected static final int Puerto = 1800;
    
    /**
     * Variable de tipo cadena usada para almacenar el HOST de Conexión.
     */
    protected static final String HOST = "localhost";

    /**
     * Variable de tipo cadena usada para almacenar la Ruta del Servidor 
     * donde estan almacenados los ficheros que usaremos durante el Programa.
     */
    protected static final String ruta = "src/recursos/";
    
    /**
     * Constructor de la Clase Servidor.
     * Genera e inicializa los elementos utilizados para gestionar la
     * ejecución del Usuario haciendo las veces de Servidor.
     */
    public Servidor() {
        Servidor_Interfaz interfaz = new Servidor_Interfaz();                   // Lanzamos una Instancia de la Interfaz Gráfica para el Servidor
    }
    
    /**
     * Método que simula la lógica de la aplicación.
     * Realiza un bucle infinito donde se atienden las peticiones
     * enviadas desde los Clientes y se procesan las mismas.
     */
    @Override
    public void run(){
        try {
            ServerSocket skServidor = new ServerSocket(Puerto);                 // Iniciamos la escucha del servidor en el puerto preestablecido
            while (true) {                                                      // Repetimos indefinidamente la escucha de peticiones hasta que cerremos la Interfaz
                try (Socket sCliente = skServidor.accept()) {                   // Esperamos a que se conecte un cliente y creamos un nuevo socket para el mismo
                    InputStream is = sCliente.getInputStream();                 // Obtenemos el flujo de entrada mandado por el Cliente
                    DataInputStream flujo_entrada = new DataInputStream(is);
                    String ent = flujo_entrada.readUTF();                       // Y lo almacenamos para usarlo posteriormente

                    OutputStream os = sCliente.getOutputStream();               // Creamos flujo de salida para enviar al Cliente
                    DataOutputStream flujo_salida=new DataOutputStream(os);     // los datos que formatearemos posteriormente
                    String fecha = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy")
                            .format(new Date().getTime());                      // Obtenemos fecha y hora actual formateada
                    String msj;
                    String user = ent.substring(0,ent.indexOf("*"));            // Extraemos el Usuario enviado desde el Cliente para operar con el mismo
                    String aux = ent.substring(ent.indexOf("*")+1);             // Extraemos el Comando ejecutado por el Usuario dado el caso
                    if (aux.equals("DIR")) {                                    // Si ejecuta la directriz DIR
                        flujo_salida.writeUTF("1*"+obtenerListado("tmp"));      // Le mandamos el contenido del directorio enviado por el mismo
                        msj="Listado ficheros '"+user+"' -> "+fecha;            // y capturamos la actividad en el Servidor
                    } else if (aux.contains("TYPE ")) {
                        flujo_salida.writeUTF("2*" + 
                            leerFichero(ent.substring(ent.indexOf(" ")+1)));
                        msj="Lectura fichero '"+user+"' -> "+fecha;
                    } else if (aux.equals("EXIT")) {                            // Realizamos el resto de operaciones solicitadas por el Usuario
                        flujo_salida.writeUTF("-1");
                        msj="Salida usuario '" + user + "' -> " + fecha;
                    } else {
                        String res = obtenerUsuario(ent);                       // Comprobamos si el Usuario existe y le mandamos el resultado al Cliente
                        flujo_salida.writeUTF(res);
                        msj="Acceso "+(res.equals("ok")?"aceptado":"denegado")
                            + " '" + user + "' -> " + fecha;
                    }
                    Servidor_Interfaz.taMensajes.setText(
                        Servidor_Interfaz.taMensajes.getText() + msj + "\n");   // Registramos la operación realizada previamente
                    sCliente.close();                                           // Cerramos el socket
                } catch(Exception e) {
                    Servidor_Interfaz.taMensajes.setText(e.getMessage());       // Capturando cualquier posible problema producido
                }
            }
        } catch(IOException e) {
            if (e.getMessage().equals("Address already in use: JVM_Bind")) {
                Servidor_Interfaz.taMensajes.setText("El Servidor ya está arrancado");
            } else {
                Servidor_Interfaz.taMensajes.setText(e.getMessage());
            }
        }
    }
    
    /**
     * Método usado para comprobar si el Usuario enviado es correcto.
     * Devuelve una cadena tras comprobar si el nombre de usuario está entre
     * los ficheros del Servidor y su contenido es igual a la Contraseña enviada.
     *
     * @param usuario String: cadena con el Usuario a identificar
     * @return aux String: resultado obtenido tras verificar o no al Usuario
     */
    protected static String obtenerUsuario(String usuario) {
        String user = usuario.substring(0, usuario.indexOf("*"));               // Extramos usuario de cadena recibida
        String pass = usuario.substring(usuario.indexOf("*")+1);                // Extramos contraseña de cadena recibida
        String aux = "error";
        try {
            if (new File(ruta + user + ".txt").isFile()) {                      // Comprobamos si existe un fichero con el nombre del usuario
                BufferedReader br = 
                    new BufferedReader(new FileReader(ruta + user + ".txt"));   // Obtenemos los datos contenidos dentro del fichero (donde se almancena la contraseña) y leemos su contenido
                aux = br.readLine();
                if (aux.equals(pass)) {                                         // Si el password recibido es igual al leido                                    
                    aux = "ok";                                                 // marcamos como ok la conexión
                } else {
                    aux = "error";                                              // en caso contrario marcamos error
                }
            }
        } catch (IOException ex) {
            Servidor_Interfaz.taMensajes.setText(ex.getMessage());
        }
        return aux;                                                             // y devolvemos en forma de cadena el resultado
    }
        
    /**
     * Método usado para guardar un fichero log en el Servidor.
     * Crea un fichero con la fecha de cierre del Servidor que contiene
     * todas las operaciones realizadas en el mismo durante su ejecución.
     */
    protected static void guardarLog() {
        try {
            String h = new SimpleDateFormat("HH.mm.ss dd-MM-yyyy")
                        .format(new Date().getTime());                          // Obtenemos la fecha y hora actual formateada
            BufferedWriter bw = new BufferedWriter(
                        new FileWriter(ruta + "server "+h+".log"));             // y creamos/Sobrescribimos el fichero log
            bw.write(Servidor_Interfaz.taMensajes.getText());                   // con los valores contenidos en el Text Area
            bw.flush();
        } catch (IOException ex) { }
    }
    
    /**
     * Método usado para obtener un listado de ficheros de un Directorio.
     * Obtiene el listado completo de ficheros de una carpeta concreta.
     * 
     * @param aux String: nombre de carpeta raíz que usaremos para buscar.
     * @return cadena String: listado resultante de los ficheros del directorio
     */
    protected static String obtenerListado(String aux) {
        String cadena = "";
        try {      
            File f = new File(ruta + aux + "/");                                // Obtenemos la ruta a procesar
            File[]rutas = f.listFiles();                                        // y capturamos el listado de ficheros de la misma
            for (File rut:rutas) {                                              // recorriendolos en forma de bucle
                cadena += " - " + rut.getName() + "\n";                         // y capturando los nombres de los ficheros
            }
        } catch(Exception e){ }
        return cadena;                                                          // devolvemos el resultado en forma de cadena
    }

    /**
     * Método usado para obtener el contenido del Fichero recibido.
     * Devuelve una cadena tras leer el fichero mandado como parámetro.
     *
     * @param fic String: nombre del fichero a buscar y leer
     * @return aux String: cadena con el resultado obtenido tras leer el fichero
     */
    protected static String leerFichero(String fic) {
        String aux = "";
        try {
            BufferedReader br = 
                new BufferedReader(new FileReader(ruta + "tmp/" + fic));        // Creamos un Buffer para capturar los datos contenidos dentro del fichero enviado
            String aux2;
            while((aux2 = br.readLine()) != null) {                             // lo recorremos de principio a fin
                aux += aux2 + "\n";                                             // almacenando su contenido en una variable
            }
        } catch (IOException ex) { }
        return aux;                                                             // y devolviéndolo en forma de cadena
    }
    
    /**
     * Método Principal de la Clase Servidor.
     * Lanza un Hilo del Programa llamando al Constructor.
     * 
     * @param args String[]: argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        new Servidor().start();        
    }
}