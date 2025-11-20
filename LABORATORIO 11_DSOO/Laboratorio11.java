// LABORATORIO 11 
// AUTORA: Brigitte Karolay Velasquez Puma

import javax.swing.*;
import java.awt.*;


// MODELO DEL JUEGO - almacena los datos y la logica
class GameModel {

    private String reino1 = "Inglaterra";
    private String reino2 = "Francia";

    // EJERCITO 1
    private int total1 = 10;
    private int esp1 = 3;
    private int arq1 = 1;
    private int cab1 = 4;
    private int lan1 = 2;
    private int vida1 = 85;
    private double prob1 = 78.70;

    // EJERCITO 2
    private int total2 = 3;
    private int esp2 = 1;
    private int arq2 = 0;
    private int cab2 = 1;
    private int lan2 = 1;
    private int vida2 = 23;
    private double prob2 = 21.30;

    private double aleatorio = 70.12;
    private String ganador = "Inglaterra";

    // GENERA UN NUEVO RESULTADO BASADO EN PROBABILIDAD
    public void generar() {
        aleatorio = Math.random() * 100;

        if (aleatorio <= prob1) {
            ganador = reino1;
        } else {
            ganador = reino2;
        }
    }

    // GET
    public String getReino1() { return reino1; }
    public String getReino2() { return reino2; }
    public int getTotal1() { return total1; }
    public int getEsp1() { return esp1; }
    public int getArq1() { return arq1; }
    public int getCab1() { return cab1; }
    public int getLan1() { return lan1; }
    public int getVida1() { return vida1; }
    public double getProb1() { return prob1; }
    public int getTotal2() { return total2; }
    public int getEsp2() { return esp2; }
    public int getArq2() { return arq2; }
    public int getCab2() { return cab2; }
    public int getLan2() { return lan2; }
    public int getVida2() { return vida2; }
    public double getProb2() { return prob2; }
    public double getAleatorio() { return aleatorio; }
    public String getGanador() { return ganador; }
}



// VISTA DEL JUEGO - se encarga de toda la interfaz grafica 
class GameView extends JFrame {

    JPanel tablero;
    JTextArea consola;

    JButton btnGenerar;

    JMenuItem miNuevo, miAbrir, miGuardar, miSalir, miMostrarConsola, miSobre;

    public GameView() {

        setTitle("Laboratorio 11 - GUI Avanzadas");
        setSize(1000, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        crearMenu();
        crearTablero();
        crearConsola();
    }

    // MENU 
    private void crearMenu() {

        JMenuBar barra = new JMenuBar();

        JMenu archivo = new JMenu("Archivo");
        miNuevo = new JMenuItem("Nuevo");
        miAbrir = new JMenuItem("Abrir");
        miGuardar = new JMenuItem("Guardar");
        miSalir = new JMenuItem("Salir");

        archivo.add(miNuevo);
        archivo.add(miAbrir);
        archivo.add(miGuardar);
        archivo.addSeparator();
        archivo.add(miSalir);

        JMenu ver = new JMenu("Ver");
        miMostrarConsola = new JMenuItem("Mostrar consola");
        ver.add(miMostrarConsola);

        JMenu ayuda = new JMenu("Ayuda");
        miSobre = new JMenuItem("Sobre el juego");
        ayuda.add(miSobre);

        barra.add(archivo);
        barra.add(ver);
        barra.add(ayuda);

        setJMenuBar(barra);
    }

    // TABLERO 
    private void crearTablero() {
        tablero = new JPanel(new GridLayout(10, 10));

        for (int i = 0; i < 100; i++) {
            JLabel c = new JLabel("", SwingConstants.CENTER);
            c.setOpaque(true);
            c.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            tablero.add(c);
        }

        add(tablero, BorderLayout.CENTER);
    }

    // CONSOLA Y BOTONN
    private void crearConsola() {

        JPanel derecha = new JPanel(new BorderLayout());

        btnGenerar = new JButton("Generar Resultados");
        derecha.add(btnGenerar, BorderLayout.NORTH);

        consola = new JTextArea(18, 22);
        consola.setEditable(false);
        consola.setFont(new Font("Monospaced", Font.PLAIN, 12));

        derecha.add(new JScrollPane(consola), BorderLayout.CENTER);

        add(derecha, BorderLayout.EAST);
    }

    public void limpiarConsola() { consola.setText(""); }
    public void agregarConsola(String t) { consola.append(t + "\n"); }

    public void toggleConsola() { consola.setVisible(!consola.isVisible()); }
}



// CONTROLADOR - conecta la vista y el modelo

class GameController {

    private GameModel model;
    private GameView view;

    public GameController(GameModel m, GameView v) {
        model = m;
        view = v;
        eventos();
    }

    // MANEJO DEL MENU Y BOTON
    private void eventos() {

        view.btnGenerar.addActionListener(e -> {
            model.generar();
            actualizarVista();
        });

        view.miNuevo.addActionListener(e -> {
            view.limpiarConsola();
            limpiarTablero();
            JOptionPane.showMessageDialog(view, "JUEGO NUEVO CREADO");
        });

        view.miAbrir.addActionListener(e -> {
            JOptionPane.showMessageDialog(view, "FUNCION NO DISPONIBLE");
        });

        view.miGuardar.addActionListener(e -> {
            JOptionPane.showMessageDialog(view, "FUNCION NO DIAPONIBLE");
        });

        view.miSalir.addActionListener(e -> {
            int r = JOptionPane.showConfirmDialog(view, "DESEA SALIR?", "SALIR", JOptionPane.YES_NO_OPTION);
            if (r == 0) System.exit(0);
        });

        view.miMostrarConsola.addActionListener(e -> view.toggleConsola());

        view.miSobre.addActionListener(e -> {
            JOptionPane.showMessageDialog(view,
                "Laboratorio 11 - Desarrollo de Software Orientado a Objetos\nAutora: Brigitte Karolay Velasquez Puma",
                "Sobre el juego",
                JOptionPane.INFORMATION_MESSAGE);
        });
    }

    // ACTUALIZA CONSOLA Y TABLERO
    private void actualizarVista() {

        view.limpiarConsola();

        view.agregarConsola("Ejercito 1: " + model.getReino1());
        view.agregarConsola("Cantidad total: " + model.getTotal1());
        view.agregarConsola("Espadachines: " + model.getEsp1());
        view.agregarConsola("Arqueros: " + model.getArq1());
        view.agregarConsola("Caballeros: " + model.getCab1());
        view.agregarConsola("Lanceros: " + model.getLan1());
        view.agregarConsola("");

        view.agregarConsola("Ejercito 2: " + model.getReino2());
        view.agregarConsola("Cantidad total: " + model.getTotal2());
        view.agregarConsola("Espadachines: " + model.getEsp2());
        view.agregarConsola("Arqueros: " + model.getArq2());
        view.agregarConsola("Caballeros: " + model.getCab2());
        view.agregarConsola("Lanceros: " + model.getLan2());
        view.agregarConsola("");

        view.agregarConsola("El ganador es: " + model.getGanador());
        view.agregarConsola("Aleatorio generado: " + model.getAleatorio());

        dibujar();
    }

    private void dibujar() {
        limpiarTablero();

        Color rosa = new Color(255, 130, 210);
        Color celeste = new Color(120, 200, 255);

        colocar(0, 0, "E", rosa);
        colocar(1, 2, "C", rosa);
        colocar(2, 3, "A", rosa);
        colocar(3, 4, "L", rosa);

        colocar(8, 7, "C", celeste);
        colocar(9, 9, "E", celeste);
    }

    // COLOCA UN SOLDADO EN LA CELDA
    private void colocar(int f, int c, String t, Color color) {
        JLabel celda = (JLabel) view.tablero.getComponent(f * 10 + c);
        celda.setText(t);
        celda.setBackground(color);
    }

    // LIMPIA EL TABLERO
    private void limpiarTablero() {
        for (Component comp : view.tablero.getComponents()) {
            JLabel celda = (JLabel) comp;
            celda.setText("");
            celda.setBackground(Color.WHITE);
        }
    }
}


// CLASE PUBLICA PRINCIPAL - inicia el mvc
public class Laboratorio11 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameModel model = new GameModel();
            GameView view = new GameView();
            new GameController(model, view);
            view.setVisible(true);
        });
    }
}
