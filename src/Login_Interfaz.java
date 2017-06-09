import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Clase Cliente_Interfaz que extiende de JFrame e implementa ActionListener. 
 * Se encarga de generar la Interfaz que usaremos para implementar los métodos
 * de control usados para el Cliente de la aplicación.
 *
 * @author Odei
 * @version 24.03.2016
 */
public class Login_Interfaz extends JFrame implements ActionListener {    
    /**
     * Variable usada para almacenar el Frame de la Interfaz de Login.
     */
    protected static JFrame frame;
    
    /**
     * Variable usada para almacenar el Nombre de Usuario a Conectar.
     */
    protected static JTextField tfUser;
    
    /**
     * Variable usada para almacenar el Password de Usuario a Conectar.
     */
    protected static JPasswordField tfPass;
       
    /**
     * Variable usada para mostrar el Resultado de la Conexión del Usuario.
     */
    protected static JLabel tfResultado;
    
    /**
     * Variable botón usada para Autenticar al Usuario en el Sistema.
     */
    protected JButton btnConectar;
        
    /**
     * Constructor de la Interfaz Gráfica implementada para el Login.
     * Genera e inicializa la Interfaz y los elementos utilizados
     * para visualizar de forma interactiva la ejecución del Login.
     */
    public Login_Interfaz() {
        JPanel panel = new JPanel(null);                                        // Creamos un panel para dibujar la interfaz gráfica
        JLabel lbUser = new JLabel("Usuario:");                                 // Agregamos etiquetas, botones, y demás elementos a la Interfaz
        JLabel lbPassword = new JLabel("Contraseña:");
        tfUser = new JTextField("");
        tfPass = new JPasswordField("");
        tfResultado = new JLabel("");
        btnConectar = new JButton("Conectar");
        panel.add(lbUser).setBounds(35, 25, 80, 20);
        panel.add(lbPassword).setBounds(35, 55, 80, 20);
        panel.add(tfUser).setBounds(140, 25, 100, 20);
        panel.add(tfPass).setBounds(140, 55, 100, 20);
        panel.add(tfResultado).setBounds(240, 25, 50, 50);
        panel.add(btnConectar).setBounds(140, 90, 100, 25);
        tfResultado.setVisible(false);
        btnConectar.addActionListener((ActionListener) this);

        frame = new JFrame("Formulario de Acceso");                             // Creamos JFrame y le ponemos título
        frame.add(panel);                                                       // agregando el panel previamente creado
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage(
                Cliente.class.getResource("recursos/client.png")));             // Le ponemos una imágen de icono a la ventana
        frame.setSize(285, 170);                                                // y le asignamos tamaño y demás parámetros
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
 
   /**
     * Creamos un escuchador de eventos para capturar las opciones
     * utilizadas durante la ejecución de la Interfaz.
     * 
     * @param evt ActionEvent: evento lanzado por el Jugador
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        if("Conectar".equals(evt.getActionCommand())) {                         // Si pulsamos el botón Conectar
            if (tfUser.getText().equals("") || tfPass.getText().equals("")) {   // y esta vacío algunos de los campos necesarios
                lanzarError("vacio");                                           // mostramos un aviso en forma de imágem
            } else {
                Cliente.procesarConexion();                                     // en caso contrario procesamos conexión
            }
        }
    }
    
    /**
     * Método usado para generar un Sonido que simula el acceso erróneo o no 
     * que se produce cada vez que un Usuario pulsa el botón Enviar.
     * 
     * @param nombre String: variable usada para almacenar el audio a ejecutar
     */
    protected static void generarAudio(String nombre) {
        try {
            Clip audio = AudioSystem.getClip();                                 // Creamos un objeto Clip para reproducir 
            audio.open(AudioSystem.getAudioInputStream(
                Cliente_Interfaz.class.getResource("recursos/"+nombre+".wav")));// un audio cada vez que se intenta realizar una Conexión
            audio.start();                                                      // y lo ejecutamos una vez
        }catch(LineUnavailableException | UnsupportedAudioFileException | IOException e){
            Cliente_Interfaz.taMensajes.setText(e.toString());                  // Capturando cualquier posible problema producido
        }
    }
    
    /**
     * Método usado para activar la visualización de la imágen usada para
     * mostrar errores o avisos al intentar acceder al Servidor.
     * 
     * @param nombre String: variable usada para almacenar el aviso a mostrar
     */
    protected static void lanzarError(String nombre) {
        tfResultado.setVisible(true);                                           // Activamos la imágen de aviso
        tfResultado.setIcon(new ImageIcon(
            Cliente_Interfaz.class.getResource("recursos/"+nombre+".png")));    // y le asignamos su fichero correspondiente
    }
}