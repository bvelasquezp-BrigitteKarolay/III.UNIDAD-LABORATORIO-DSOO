import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/*
 MODELO DEL JUEGO
*/
class GameModel {

    private String reino1 = "Inglaterra";
    private String reino2 = "Francia";

    // E. 1
    private int total1 = 10;
    private int esp1 = 3;
    private int arq1 = 1;
    private int cab1 = 4;
    private int lan1 = 2;
    private int vida1 = 85;
    private double prob1 = 78.70;

    // E. 2
    private int total2 = 3;
    private int esp2 = 1;
    private int arq2 = 0;
    private int cab2 = 1;
    private int lan2 = 1;
    private int vida2 = 23;
    private double prob2 = 21.30;

    private double aleatorio = 70.12;
    private String ganador = "Inglaterra";

    private Random rnd = new Random();

    // Genera resultado basado en probabilidad
    public void generar() {
        aleatorio = Math.round(rnd.nextDouble() * 10000.0) / 100.0;
        if (aleatorio <= prob1) {
            ganador = reino1;
        } else {
            ganador = reino2;
        }
    }

    // METODO PARA OBTENER EL TEXTO QUE VA EN LA CONSOLA
    public String generarTextoConsola() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ejercito 1: ").append(reino1).append("\n");
        sb.append("Cantidad total: ").append(total1).append("\n");
        sb.append("Espadachines: ").append(esp1).append("\n");
        sb.append("Arqueros: ").append(arq1).append("\n");
        sb.append("Caballeros: ").append(cab1).append("\n");
        sb.append("Lanceros: ").append(lan1).append("\n\n");

        sb.append("Ejercito 2: ").append(reino2).append("\n");
        sb.append("Cantidad total: ").append(total2).append("\n");
        sb.append("Espadachines: ").append(esp2).append("\n");
        sb.append("Arqueros: ").append(arq2).append("\n");
        sb.append("Caballeros: ").append(cab2).append("\n");
        sb.append("Lanceros: ").append(lan2).append("\n\n");

        sb.append("Suma niveles vida - ").append(reino1).append(": ").append(vida1).append("  ");
        sb.append(reino2).append(": ").append(vida2).append("\n");
        sb.append(String.format("%s: %.2f%% de probabilidad de victoria\n", reino1, prob1));
        sb.append(String.format("%s: %.2f%% de probabilidad de victoria\n", reino2, prob2));
        sb.append("Aleatorio generado: ").append(aleatorio).append("\n");
        sb.append("Ganador segun experimento: ").append(ganador).append("\n");
        return sb.toString();
    }

    // METODOS PARA GUARDAR RANKING
    public String generarRankingTexto() {
        List<String> items = new ArrayList<>();
        items.add(String.format("CABALLERO Caballero1X1 [Vida=%d]", vida1 > vida2 ? vida1 : vida2));
        StringBuilder sb = new StringBuilder();
        sb.append("RANKING EJERCITO 1 (POR VIDA):\n");
        sb.append("  EjemploSoldado1 [Vida=").append(vida1).append("]\n");
        sb.append("\nRANKING EJERCITO 2 (POR VIDA):\n");
        sb.append("  EjemploSoldado2 [Vida=").append(vida2).append("]\n");
        return sb.toString();
    }

    // METODO PARA GUARDAR CONFIGURACION ACTUAL
    public String generarConfiguracionTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append("CONFIGURACION JUEGO\n");
        sb.append("Reino1=").append(reino1).append("\n");
        sb.append("Reino2=").append(reino2).append("\n");
        sb.append("ProbabilidadReino1=").append(prob1).append("\n");
        sb.append("ProbabilidadReino2=").append(prob2).append("\n");
        sb.append("Tablero=10x10\n");
        return sb.toString();
    }

    // GET
    public String getReino1() {
        return reino1;
    }

    public String getReino2() {
        return reino2;
    }

    public int getTotal1() {
        return total1;
    }

    public int getEsp1() {
        return esp1;
    }

    public int getArq1() {
        return arq1;
    }

    public int getCab1() {
        return cab1;
    }

    public int getLan1() {
        return lan1;
    }

    public int getVida1() {
        return vida1;
    }

    public double getProb1() {
        return prob1;
    }

    public int getTotal2() {
        return total2;
    }

    public int getEsp2() {
        return esp2;
    }

    public int getArq2() {
        return arq2;
    }

    public int getCab2() {
        return cab2;
    }

    public int getLan2() {
        return lan2;
    }

    public int getVida2() {
        return vida2;
    }

    public double getProb2() {
        return prob2;
    }

    public double getAleatorio() {
        return aleatorio;
    }

    public String getGanador() {
        return ganador;
    }
}

/*
 * VISTA DEL JUEGO
 */
class GameView extends JFrame {

    JPanel tablero;
    JTextArea consola;

    JButton btnGenerar;

    JMenuItem miNuevo, miAbrir, miAbrirLogs, miGuardar, miGuardarLogs, miGuardarRanking,
            miGuardarConfig, miSalir, miMostrarConsola, miSobre, miVerLogs, miVerRanking, miVerConfig;

    public GameView() {

        setTitle("Laboratorio 12 - GUI Avanzada");
        setSize(1100, 780);
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
        miAbrir = new JMenuItem("Abrir...");
        miAbrirLogs = new JMenuItem("Abrir logs");
        miGuardar = new JMenuItem("Guardar...");
        miGuardarLogs = new JMenuItem("Guardar logs");
        miGuardarRanking = new JMenuItem("Guardar ranking");
        miGuardarConfig = new JMenuItem("Guardar configuracion");
        miSalir = new JMenuItem("Salir");

        archivo.add(miNuevo);
        archivo.add(miAbrir);
        archivo.add(miAbrirLogs);
        archivo.addSeparator();
        archivo.add(miGuardar);
        archivo.add(miGuardarLogs);
        archivo.add(miGuardarRanking);
        archivo.add(miGuardarConfig);
        archivo.addSeparator();
        archivo.add(miSalir);

        JMenu ver = new JMenu("Ver");
        miMostrarConsola = new JMenuItem("Mostrar consola");
        ver.add(miMostrarConsola);

        JMenu ayuda = new JMenu("Ayuda");
        miSobre = new JMenuItem("Sobre el juego");
        ayuda.add(miSobre);

        JMenu verArchivos = new JMenu("Ver archivos");
        miVerLogs = new JMenuItem("Ver logs.txt");
        miVerRanking = new JMenuItem("Ver ranking.txt");
        miVerConfig = new JMenuItem("Ver configuracion.txt");
        verArchivos.add(miVerLogs);
        verArchivos.add(miVerRanking);
        verArchivos.add(miVerConfig);

        barra.add(archivo);
        barra.add(ver);
        barra.add(verArchivos);
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
            c.setBackground(Color.WHITE);
            tablero.add(c);
        }

        add(tablero, BorderLayout.CENTER);
    }

    // PANEL DERECHA CON BOTON Y CONSOLA
    private void crearConsola() {

        JPanel derecha = new JPanel(new BorderLayout());
        derecha.setPreferredSize(new Dimension(380, 0));

        btnGenerar = new JButton("Generar Resultados");
        derecha.add(btnGenerar, BorderLayout.NORTH);

        consola = new JTextArea(20, 30);
        consola.setEditable(false);
        consola.setFont(new Font("Monospaced", Font.PLAIN, 12));
        derecha.add(new JScrollPane(consola), BorderLayout.CENTER);

        add(derecha, BorderLayout.EAST);
    }

    // METODOS PARA QUE EL CONTROLADOR INTERACTUE
    public void limpiarConsola() {
        consola.setText("");
    }

    public void agregarConsola(String t) {
        consola.append(t + "\n");
    }

    public String getConsolaTexto() {
        return consola.getText();
    }

    public void toggleConsola() {
        consola.setVisible(!consola.isVisible());
    }

    // METODOS PARA MODIFICAR CELDAS DEL TABLERO
    public void setCell(int fila, int col, String texto, Color color) {
        int index = fila * 10 + col;
        Component comp = tablero.getComponent(index);
        if (comp instanceof JLabel) {
            JLabel lab = (JLabel) comp;
            lab.setText(texto);
            lab.setOpaque(true);
            lab.setBackground(color);
            lab.setForeground(Color.BLACK);
        }
    }

    public void clearBoard() {
        for (Component comp : tablero.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel lab = (JLabel) comp;
                lab.setText("");
                lab.setOpaque(true);
                lab.setBackground(Color.WHITE);
            }
        }
    }

    public void showFileContent(String title, String content) {
        JTextArea ta = new JTextArea(content);
        ta.setEditable(false);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane sp = new JScrollPane(ta);
        sp.setPreferredSize(new Dimension(700, 400));
        JOptionPane.showMessageDialog(this, sp, title, JOptionPane.PLAIN_MESSAGE);
    }
}

/*
 * CONTROLADOR
 */
class GameController {

    private GameModel model;
    private GameView view;

    private final String FILE_LOGS = "logs.txt";
    private final String FILE_RANKING = "ranking.txt";
    private final String FILE_CONFIG = "configuracion.txt";

    public GameController(GameModel m, GameView v) {
        model = m;
        view = v;
        eventos();
    }

    private void eventos() {

        // BOTON GENERAR
        view.btnGenerar.addActionListener(e -> {
            model.generar();
            actualizarVista();
            appendLogs("GENERAR -> " + new Date() + " -> Aleatorio: " + model.getAleatorio() + " Ganador: "
                    + model.getGanador());
        });

        // NUEVO
        view.miNuevo.addActionListener(e -> {
            view.limpiarConsola();
            view.clearBoard();
            JOptionPane.showMessageDialog(view, "JUEGO NUEVO CREADO");
        });

        // ABRIR
        view.miAbrir.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(".");
            int r = chooser.showOpenDialog(view);
            if (r == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
                try {
                    String content = new String(Files.readAllBytes(f.toPath()));
                    view.showFileContent("Archivo: " + f.getName(), content);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(view, "ERROR AL ABRIR: " + ex.getMessage());
                }
            }
        });

        // ABRIR LOGS
        view.miAbrirLogs.addActionListener(e -> {
            openAndShowFile(FILE_LOGS);
        });

        // GUARDAR
        view.miGuardar.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(".");
            chooser.setDialogTitle("Guardar texto de consola como...");
            int r = chooser.showSaveDialog(view);
            if (r == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
                try {
                    writeStringToFile(f.getPath(), view.getConsolaTexto());
                    JOptionPane.showMessageDialog(view, "GUARDADO: " + f.getName());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(view, "ERROR AL GUARDAR: " + ex.getMessage());
                }
            }
        });

        // GUARDAR LOGS
        view.miGuardarLogs.addActionListener(e -> {
            try {
                writeStringToFile(FILE_LOGS, view.getConsolaTexto());
                JOptionPane.showMessageDialog(view, "LOGS GUARDADOS EN: " + FILE_LOGS);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "ERROR AL GUARDAR LOGS: " + ex.getMessage());
            }
        });

        // GUARDAR RANKING
        view.miGuardarRanking.addActionListener(e -> {
            try {
                String ranking = model.generarRankingTexto();
                writeStringToFile(FILE_RANKING, ranking);
                JOptionPane.showMessageDialog(view, "RANKING GUARDADO EN: " + FILE_RANKING);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "ERROR AL GUARDAR RANKING: " + ex.getMessage());
            }
        });

        // GUARDAR CONFIGURACION
        view.miGuardarConfig.addActionListener(e -> {
            try {
                String cfg = model.generarConfiguracionTexto();
                writeStringToFile(FILE_CONFIG, cfg);
                JOptionPane.showMessageDialog(view, "CONFIGURACION GUARDADA EN: " + FILE_CONFIG);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "ERROR AL GUARDAR CONFIGURACION: " + ex.getMessage());
            }
        });

        // SALIR
        view.miSalir.addActionListener(e -> {
            int r = JOptionPane.showConfirmDialog(view, "DESEA SALIR?", "SALIR", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION)
                System.exit(0);
        });
        view.miMostrarConsola.addActionListener(e -> view.toggleConsola());

        // SOBRE
        view.miSobre.addActionListener(e -> {
            JOptionPane.showMessageDialog(view,
                    "Laboratorio 12 - DSOO \nAutora: Brigitte Karolay Velasquez Puma",
                    "Sobre el juego",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // VER ARCHIVOS
        view.miVerLogs.addActionListener(e -> openAndShowFile(FILE_LOGS));
        view.miVerRanking.addActionListener(e -> openAndShowFile(FILE_RANKING));
        view.miVerConfig.addActionListener(e -> openAndShowFile(FILE_CONFIG));
    }

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

        view.agregarConsola("Suma niveles vida - " + model.getReino1() + ": " + model.getVida1()
                + "  " + model.getReino2() + ": " + model.getVida2());
        view.agregarConsola(
                String.format("%s: %.2f%% de probabilidad de victoria", model.getReino1(), model.getProb1()));
        view.agregarConsola(
                String.format("%s: %.2f%% de probabilidad de victoria", model.getReino2(), model.getProb2()));
        view.agregarConsola("Aleatorio generado: " + model.getAleatorio());
        view.agregarConsola("Ganador segun experimento: " + model.getGanador());

        dibujar();
    }

    private void dibujar() {
        view.clearBoard();

        Color rosa = new Color(255, 130, 210);
        Color celeste = new Color(120, 200, 255);
        Color morado = new Color(186, 85, 211);
        Color verde = new Color(135, 245, 235);

        view.setCell(0, 0, "E", rosa);
        view.setCell(1, 2, "C", morado);
        view.setCell(2, 3, "A", verde);
        view.setCell(3, 4, "L", celeste);

        view.setCell(8, 7, "C", celeste);
        view.setCell(9, 9, "E", morado);
    }

    // METODO PARA ESCRIBIR STRING EN ARCHIVO
    private void writeStringToFile(String path, String content) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write(content);
        }
    }

    private void appendLogs(String line) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_LOGS, true))) {
            bw.write(line);
            bw.newLine();
        } catch (IOException ex) {

            JOptionPane.showMessageDialog(view, "ERROR AL ESCRIBIR LOGS: " + ex.getMessage());
        }
    }

    private void openAndShowFile(String filename) {
        Path p = Paths.get(filename);
        if (!Files.exists(p)) {
            JOptionPane.showMessageDialog(view, "Archivo no existe: " + filename);
            return;
        }
        try {
            String content = new String(Files.readAllBytes(p));
            view.showFileContent("Archivo: " + filename, content);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(view, "ERROR AL LEER: " + ex.getMessage());
        }
    }
}

/*
 * MAIN - inicia el mvc
 */
public class Laboratorio12 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameModel model = new GameModel();
            GameView view = new GameView();
            new GameController(model, view);
            view.setVisible(true);
        });
    }
}