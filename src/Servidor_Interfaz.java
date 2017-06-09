import java.awt.Color;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Clase Servidor_Interfaz que extiende de JFrame e implementa ActionListener. 
 * Se encarga de generar la Interfaz que usaremos para implementar los métodos
 * de control usados para el Servidor de la aplicación.
 *
 * @author Odei
 * @version 24.03.2016
 */
public class Servidor_Interfaz extends JFrame implements ActionListener {
    /**
     * Variable usada para mostrar los eventos producidos durante la ejecución.
     */
    protected static TextArea taMensajes; 
        
    /**
     * Constructor de la Interfaz Gráfica implementada para el Servidor.
     * Genera e inicializa la Interfaz y los elementos utilizados
     * para visualizar de forma interactiva la ejecución de la aplicación.
     */
    public Servidor_Interfaz() {
        JPanel panel = new JPanel(null);                                        // Creamos un panel para dibujar la interfaz gráfica
        JLabel lbTit = new JLabel("Servidor");                                  // Agregamos etiquetas y demás elementos a la Interfaz
        String hora = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy")
                                    .format(new Date().getTime());
        taMensajes = new TextArea("Servidor arrancado -> "+hora+"\n");
        panel.add(lbTit).setBounds(180, 5, 80, 20);                             // Los posicionamos en el panel
        panel.add(taMensajes).setBounds(25, 35, 360, 230);
        lbTit.setForeground(Color.black);                                       // Cambiamos el color del label del título
        taMensajes.setEditable(false);                                          // Imposibilitamos la edición del Text Area

        JFrame frame = new JFrame("Servidor de Transacciones");                 // Creamos JFrame y le ponemos título
        frame.add(panel);                                                       // agregando el panel previamente creado
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage(
                Cliente.class.getResource("recursos/server.png")));             // Le ponemos una imágen de icono a la ventana
        frame.setSize(420, 320);                                                // y le asignamos tamaño y demás parámetros
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowAdapter() {                           // Añadimos un escuchador de eventos para capturar el cierre de la ventana
            @Override
            public void windowClosing(WindowEvent e) {
                Servidor.guardarLog();                                          // en dicho caso almacenamos en un fichero log el contenido de la sesión actual
                System.exit(0);
            }
        });
    }

   /**
     * Escuchador de eventos usado para capturar las opciones
     * utilizadas durante la ejecución de la Interfaz.
     * 
     * @param evt ActionEvent: evento lanzado por el Jugador
     */
    @Override
    public void actionPerformed(ActionEvent evt) { }
}