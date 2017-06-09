import java.awt.Color;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Clase Cliente_Interfaz que extiende de JFrame e implementa ActionListener. 
 * Se encarga de generar la Interfaz que usaremos para implementar los métodos
 * de control usados para el Cliente de la aplicación.
 *
 * @author Odei
 * @version 24.03.2016
 */
public class Cliente_Interfaz extends JFrame implements ActionListener {      
    /**
     * Variable usada para almacenar el Frame de la Interfaz de Usuario.
     */
    protected static JFrame frame;
    
    /**
     * Variable usada para almacener el Comando ejecutado por el Usuario.
     */
    protected static TextField tfComando;
        
    /**
     * Variable usada para mostrar los eventos producidos durante la ejecución.
     */
    protected static TextArea taMensajes;
    
    /**
     * Variable botón usada para conectar con el Servidor.
     */
    protected JButton btnEnviar;
        
    /**
     * Constructor de la Interfaz Gráfica implementada para el Cliente.
     * Genera e inicializa la Interfaz y los elementos utilizados
     * para visualizar de forma interactiva la ejecución de la aplicación.
     * 
     * @param user String: nombre de Usuario a mostrar en el encabezado
     */
    public Cliente_Interfaz(String user) {
        JPanel panel = new JPanel(null);                                        // Creamos un panel para dibujar la interfaz gráfica
        JLabel lbUsuario = new JLabel("Bienvenido " + user);                    // Agregamos etiquetas, botones, y demás elementos a la Interfaz
        JLabel lbComando = new JLabel("Comando: ");
        tfComando = new TextField();
        taMensajes = new TextArea("Introduce un Comando y Pulsa el Botón." +
                "\nComandos aceptados:\n\n - DIR\n - TYPE 'archivo'\n - EXIT\n");
        btnEnviar = new JButton("Enviar");  
        panel.add(lbUsuario).setBounds(114, 5, 150, 20);                        // Y posicionamos en el panel los elementos
        panel.add(lbComando).setBounds(35, 45, 60, 20);
        panel.add(tfComando).setBounds(110, 45, 102, 20);
        panel.add(btnEnviar).setBounds(230, 42, 70, 25);
        panel.add(taMensajes).setBounds(25, 90, 290, 180);
        lbUsuario.setForeground(Color.blue);
        btnEnviar.addActionListener((ActionListener) this);
        taMensajes.setEditable(false);

        frame = new JFrame("Ventana de Transacciones");                         // Creamos JFrame y le ponemos título
        frame.add(panel);                                                       // agregando el panel previamente creado
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage(
                Cliente.class.getResource("recursos/client.png")));             // Le ponemos una imágen de icono a la ventana
        frame.setSize(350, 320);                                                // y le asignamos tamaño y demás parámetros
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowAdapter() {                           // Añadimos un escuchador de eventos para capturar el cierre de la ventana
            @Override
            public void windowClosing(WindowEvent e) {
                Cliente.procesarComando("EXIT");                                // en dicho caso procesamos la salida del Usuario para que el Servidor tenga constancia
                System.exit(0);
            }
        });
    }
 
   /**
     * Creamos un escuchador de eventos para capturar las opciones
     * utilizadas durante la ejecución de la Interfaz.
     * 
     * @param evt ActionEvent: evento lanzado por el Usuario
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        if("Enviar".equals(evt.getActionCommand())) {                           // Si el Usuario pulsa el botón Enviar
            String aux = tfComando.getText();                                   // Capturamos el comando introducido
            if (aux.equals("DIR") || aux.contains("TYPE ") || aux.equals("EXIT")) {
                Cliente.procesarComando(aux);                                   // y lo procesamos dado el caso
            } else {
                taMensajes.setText("No se Reconoce el Comando Introducido");    // mostrando un mensaje de aviso en caso contrario
            }
        }
    }
}