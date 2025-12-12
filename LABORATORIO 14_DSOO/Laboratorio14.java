// LABORATORIO 14
// AUTORA: Velasquez Puma Brigitte Karolay

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// MODELO DEL JUEGO
class GameModel implements Serializable {
    private static final long serialVersionUID = 1L;

    // Nombres de los reinos
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

    // Probabilidad aleatoria y ganador
    private double aleatorio = 70.12;
    private String ganador = "Inglaterra";

    // Generador de números aleatorios
    private transient Random rnd = new Random();

    // TABLERO DEL JUEGO
    private String[][] board = new String[10][10];
    private int[][] boardOwner = new int[10][10];

    // CONSTRUCTOR
    public GameModel() {
        // Inicializa el tablero vacío
        for (int r = 0; r < 10; r++)
            for (int c = 0; c < 10; c++) {
                board[r][c] = "";
                boardOwner[r][c] = 0;
            }

        // Colocar soldados iniciales Ejercito 1
        colocarSoldado(0, 3, "E", 1);
        colocarSoldado(0, 4, "A", 1);
        colocarSoldado(0, 5, "C", 1);
        colocarSoldado(0, 6, "L", 1);
        colocarSoldado(1, 3, "E", 1);
        colocarSoldado(1, 4, "A", 1);
        colocarSoldado(1, 5, "C", 1);
        colocarSoldado(1, 6, "L", 1);

        // Colocar soldados iniciales Ejercito 2
        colocarSoldado(8, 3, "E", 2);
        colocarSoldado(8, 4, "A", 2);
        colocarSoldado(8, 5, "C", 2);
        colocarSoldado(8, 6, "L", 2);
        colocarSoldado(9, 3, "E", 2);
        colocarSoldado(9, 4, "A", 2);
        colocarSoldado(9, 5, "C", 2);
        colocarSoldado(9, 6, "L", 2);

        // Movimiento inicial aleatorio y actualizar contadores
        moverAleatorioInicial();
        actualizarContadores();
    }

    // COLOCAR SOLDADO EN TABLERO
    private void colocarSoldado(int r, int c, String tipo, int owner) {
        if (r >= 0 && r < 10 && c >= 0 && c < 10) {
            board[r][c] = tipo;
            boardOwner[r][c] = owner;
        }
    }

    // MOVER SOLDADOS ALEATORIAMENTE AL INICIO
    public void moverAleatorioInicial() {
        if (rnd == null)
            rnd = new Random();
        for (int k = 0; k < 30; k++) {
            int f1 = rnd.nextInt(10);
            int c1 = rnd.nextInt(10);
            int f2 = rnd.nextInt(10);
            int c2 = rnd.nextInt(10);
            if (!board[f1][c1].isEmpty() && board[f2][c2].isEmpty()) {
                board[f2][c2] = board[f1][c1];
                boardOwner[f2][c2] = boardOwner[f1][c1];
                board[f1][c1] = "";
                boardOwner[f1][c1] = 0;
            }
        }
    }

    // ACTUALIZAR CONTADORES DE SOLDADOS
    public void actualizarContadores() {
        esp1 = arq1 = cab1 = lan1 = 0;
        esp2 = arq2 = cab2 = lan2 = 0;

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                String tipo = board[r][c];
                int owner = boardOwner[r][c];
                if (tipo == null || tipo.isEmpty())
                    continue;
                if (owner == 1) {
                    switch (tipo) {
                        case "E":
                            esp1++;
                            break;
                        case "A":
                            arq1++;
                            break;
                        case "C":
                            cab1++;
                            break;
                        case "L":
                            lan1++;
                            break;
                    }
                } else if (owner == 2) {
                    switch (tipo) {
                        case "E":
                            esp2++;
                            break;
                        case "A":
                            arq2++;
                            break;
                        case "C":
                            cab2++;
                            break;
                        case "L":
                            lan2++;
                            break;
                    }
                }
            }
        }
        total1 = esp1 + arq1 + cab1 + lan1;
        total2 = esp2 + cab2 + arq2 + lan2;
    }

    // MOVER UN SOLDADO (controlador)

    public boolean mover(int r1, int c1, int r2, int c2) {
        if (!validCell(r1, c1) || !validCell(r2, c2))
            return false;
        if (board[r1][c1].isEmpty())
            return false;
        if (!board[r2][c2].isEmpty())
            return false;

        board[r2][c2] = board[r1][c1];
        boardOwner[r2][c2] = boardOwner[r1][c1];
        board[r1][c1] = "";
        boardOwner[r1][c1] = 0;

        actualizarContadores();
        return true;
    }

    private boolean validCell(int r, int c) {
        return r >= 0 && r < 10 && c >= 0 && c < 10;
    }

    // GENERAR PROBABILIDAD Y GANADOR

    public void generar() {
        if (rnd == null)
            rnd = new Random();
        aleatorio = Math.round(rnd.nextDouble() * 10000.0) / 100.0;
        ganador = (aleatorio <= prob1) ? reino1 : reino2;
    }

    // TEXTO PARA CONSOLA

    public String generarTextoConsola() {
        actualizarContadores();
        StringBuilder sb = new StringBuilder();
        sb.append("Ejercito 1: ").append(reino1).append("\n");
        sb.append("Cantidad total de soldados: ").append(total1).append("\n");
        sb.append("Espadachines: ").append(esp1).append("\n");
        sb.append("Arqueros: ").append(arq1).append("\n");
        sb.append("Caballeros: ").append(cab1).append("\n");
        sb.append("Lanceros: ").append(lan1).append("\n\n");

        sb.append("Ejercito 2: ").append(reino2).append("\n");
        sb.append("Cantidad total de soldados: ").append(total2).append("\n");
        sb.append("Espadachines: ").append(esp2).append("\n");
        sb.append("Arqueros: ").append(arq2).append("\n");
        sb.append("Caballeros: ").append(cab2).append("\n");
        sb.append("Lanceros: ").append(lan2).append("\n\n");

        sb.append("Ejercito 1: ").append(reino1)
                .append(": ").append(vida1)
                .append(" ").append(String.format("%.2f", prob1)).append("% de probabilidad de victoria\n");

        sb.append("Ejercito 2: ").append(reino2)
                .append(": ").append(vida2)
                .append(" ").append(String.format("%.2f", prob2)).append("% de probabilidad de victoria\n\n");

        sb.append("El ganador es el ejercito ").append(ganador.equals(reino1) ? "1" : "2")
                .append(" de: ").append(ganador)
                .append(" (Aleatorio generado: ").append(String.format("%.2f", aleatorio)).append(")");

        return sb.toString();
    }

    // RANKING

    public String generarRankingTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append("RANKING EJERCITO 1 (POR VIDA):\n");
        sb.append(" EjemploSoldado1 [Vida=").append(vida1).append("]\n\n");
        sb.append("RANKING EJERCITO 2 (POR VIDA):\n");
        sb.append(" EjemploSoldado2 [Vida=").append(vida2).append("]\n");
        return sb.toString();
    }

    // CONFIGURACION DEL JUEGO
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

    // GETTERS
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

    // ACCESO A TABLERO
    public String getCell(int r, int c) {
        return validCell(r, c) ? board[r][c] : "";
    }

    public int getOwner(int r, int c) {
        return validCell(r, c) ? boardOwner[r][c] : 0;
    }

    public void setCell(int r, int c, String val, int owner) {
        if (!validCell(r, c))
            return;
        board[r][c] = (val == null ? "" : val);
        boardOwner[r][c] = owner;
        actualizarContadores();
    }

    public String[][] getBoard() {
        return board;
    }

    public int[][] getBoardOwner() {
        return boardOwner;
    }

    public void setBoard(String[][] b, int[][] o) {
        if (b == null || o == null)
            return;
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++) {
                board[i][j] = b[i][j] == null ? "" : b[i][j];
                boardOwner[i][j] = o[i][j];
            }
        actualizarContadores();
    }

    // PARA SERIALIZACION
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        rnd = new Random();
    }
}

/* VISTA DEL JUEGO */
class GameView extends JFrame {

    JPanel tablero;
    JTextArea consola;
    JButton btnGenerar;

    JMenuItem miNuevo, miAbrir, miAbrirLogs, miGuardar, miGuardarLogs,
            miGuardarRanking, miGuardarConfig, miSalir, miMostrarConsola, miSobre,
            miVerLogs, miVerRanking, miVerConfig, miGuardarBin, miCargarBin;

    JLabel[] cells = new JLabel[100];

    static final Color CELDA_CLARA = Color.decode("#F8F4F0");
    static final Color CELDA_OSCURA = Color.decode("#E8DCC2");
    static final Color EJERCITO1 = Color.decode("#4A6FA5");
    static final Color EJERCITO2 = Color.decode("#8B2E3A");
    static final Color SELECTION = Color.decode("#D4B15A");

    public void setCellEstilizado(int fila, int col, String texto, Color color) {
        int index = fila * 10 + col;
        JLabel lab = cells[index];

        lab.setText(texto);
        lab.setBackground(color == null ? CELDA_CLARA : color);
        lab.setOpaque(true);

        lab.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lab.setForeground(Color.WHITE);

        if (!texto.isEmpty()) {
            lab.setBorder(new CompoundBorder(
                    new LineBorder(Color.BLACK, 2),
                    new EmptyBorder(6, 6, 6, 6)));
        } else {
            lab.setBorder(new LineBorder(Color.GRAY, 1));
            lab.setForeground(Color.BLACK);
        }
    }

    public GameView() {
        setTitle("Laboratorio DSOO - GUI Avanzada");
        setSize(1100, 780);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        crearMenu();
        crearTablero();
        crearConsola();
    }

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

        // lab13: binario
        miGuardarBin = new JMenuItem("Guardar partida (bin)");
        miCargarBin = new JMenuItem("Cargar partida (bin)");
        archivo.add(miGuardarBin);
        archivo.add(miCargarBin);

        archivo.addSeparator();
        archivo.add(miSalir);

        JMenu ver = new JMenu("Ver");
        miMostrarConsola = new JMenuItem("Mostrar consola");
        ver.add(miMostrarConsola);

        JMenu verArchivos = new JMenu("Ver archivos");
        miVerLogs = new JMenuItem("Ver logs.txt");
        miVerRanking = new JMenuItem("Ver ranking.txt");
        miVerConfig = new JMenuItem("Ver configuracion.txt");
        verArchivos.add(miVerLogs);
        verArchivos.add(miVerRanking);
        verArchivos.add(miVerConfig);

        JMenu ayuda = new JMenu("Ayuda");
        miSobre = new JMenuItem("Sobre el juego");
        ayuda.add(miSobre);

        barra.add(archivo);
        barra.add(ver);
        barra.add(verArchivos);
        barra.add(ayuda);

        setJMenuBar(barra);
    }

    private void crearTablero() {
        // grid 10x10
        tablero = new JPanel(new GridLayout(10, 10, 2, 2));
        tablero.setBackground(Color.DARK_GRAY);
        Font f = new Font("Segoe UI", Font.BOLD, 16);

        for (int i = 0; i < 100; i++) {
            JLabel c = new JLabel("", SwingConstants.CENTER);
            c.setOpaque(true);
            c.setBorder(new LineBorder(Color.GRAY, 1));
            // alternancion de colores
            boolean even = ((i / 10) + (i % 10)) % 2 == 0;
            c.setBackground(even ? CELDA_CLARA : CELDA_OSCURA);
            c.setFont(f);
            c.setForeground(Color.BLACK);
            c.setPreferredSize(new Dimension(50, 50));
            cells[i] = c;
            tablero.add(c);
        }
        add(tablero, BorderLayout.CENTER);
    }

    private void crearConsola() {
        JPanel derecha = new JPanel(new BorderLayout());
        derecha.setPreferredSize(new Dimension(380, 0));

        btnGenerar = new JButton("Generar Resultados");
        btnGenerar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        derecha.add(btnGenerar, BorderLayout.NORTH);

        consola = new JTextArea(20, 30);
        consola.setEditable(false);
        consola.setFont(new Font("Monospaced", Font.PLAIN, 12));
        derecha.add(new JScrollPane(consola), BorderLayout.CENTER);

        add(derecha, BorderLayout.EAST);
    }

    // metodos para que controlador use la vista
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

    // setear celda visualmente (texto y color)
    public void setCell(int fila, int col, String texto, Color color) {
        int index = fila * 10 + col;
        JLabel lab = cells[index];
        lab.setText(texto);
        lab.setBackground(color == null ? (((fila + col) % 2 == 0) ? CELDA_CLARA : CELDA_OSCURA) : color);
        lab.setOpaque(true);

        if (!texto.isEmpty()) {
            lab.setFont(new Font("Segoe UI", Font.BOLD, 18));
            lab.setForeground(Color.WHITE);
            lab.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(4, 4, 4, 4)));
        } else {
            lab.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lab.setForeground(Color.BLACK);
            lab.setBorder(new LineBorder(Color.GRAY, 1));
        }
    }

    // limpiar tablero
    public void clearBoard() {
        for (int i = 0; i < cells.length; i++) {
            boolean even = ((i / 10) + (i % 10)) % 2 == 0;
            cells[i].setText("");
            cells[i].setBackground(even ? CELDA_CLARA : CELDA_OSCURA);
            cells[i].setBorder(new LineBorder(Color.GRAY, 1));
            cells[i].setForeground(Color.BLACK);
        }
    }

    // mostrar contenido de archivo en cuadro
    public void showFileContent(String title, String content) {
        JTextArea ta = new JTextArea(content);
        ta.setEditable(false);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane sp = new JScrollPane(ta);
        sp.setPreferredSize(new Dimension(700, 400));
        JOptionPane.showMessageDialog(this, sp, title, JOptionPane.PLAIN_MESSAGE);
    }

    public JLabel[] getCells() {
        return cells;
    }
}

/* CONTROLADOR */
class GameController {

    private GameModel model;
    private GameView view;

    private final String FILE_LOGS = "logs.txt";
    private final String FILE_RANKING = "ranking.txt";
    private final String FILE_CONFIG = "configuracion.txt";
    private final String FILE_PARTIDA = "partida.bin";

    private int selectedRow = -1;
    private int selectedCol = -1;

    public GameController(GameModel m, GameView v) {
        model = m;
        view = v;
        eventos();
        actualizarVista();
    }

    private void eventos() {

        // CLICK SOBRE CELDAS - manejo de seleccion y movimiento
        JLabel[] cells = view.getCells();
        for (int i = 0; i < cells.length; i++) {
            final int index = i;
            cells[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int r = index / 10;
                    int c = index % 10;
                    manejarClickEnCelda(r, c);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    cells[index].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            });
        }

        // BOTON GENERAR
        view.btnGenerar.addActionListener(e -> {
            model.generar();
            actualizarVista();
            appendLogs("GENERAR -> " + new Date() + " -> Aleatorio: " + model.getAleatorio() + " Ganador: "
                    + model.getGanador());
        });

        // MENU - NUEVO
        view.miNuevo.addActionListener(e -> {

            model = new GameModel();
            view.limpiarConsola();
            view.clearBoard();

            dibujarEjercitos();

            JOptionPane.showMessageDialog(view, "JUEGO NUEVO CREADO");
        });

        // MENU - ABRIR
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
        view.miAbrirLogs.addActionListener(e -> openAndShowFile(FILE_LOGS));

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
                    "Laboratorio DSOO - DSOO\nAutora: Brigitte Karolay Velasquez Puma",
                    "Sobre el juego",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // VER ARCHIVOS
        view.miVerLogs.addActionListener(e -> openAndShowFile(FILE_LOGS));
        view.miVerRanking.addActionListener(e -> openAndShowFile(FILE_RANKING));
        view.miVerConfig.addActionListener(e -> openAndShowFile(FILE_CONFIG));

        // lab13 - GUARDAR BINARIO
        view.miGuardarBin.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(".");
            chooser.setSelectedFile(new File(FILE_PARTIDA));
            int r = chooser.showSaveDialog(view);
            if (r == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
                guardarPartidaBinario(f.getPath());
            }
        });

        // lab13 - CARGAR MODELO COMPLETO
        view.miCargarBin.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(".");
            int r = chooser.showOpenDialog(view);
            if (r == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
                cargarPartidaBinario(f.getPath());
            }
        });
    }

    // manejar click en celda
    private void manejarClickEnCelda(int r, int c) {
        String val = model.getCell(r, c);
        if (selectedRow == -1) {

            if (!val.isEmpty()) {
                selectedRow = r;
                selectedCol = c;

                view.getCells()[r * 10 + c].setBorder(new LineBorder(GameView.SELECTION, 3));
                view.agregarConsola("Seleccionada ficha en: " + r + "," + c + " -> " + val);
            }
        } else {

            if (selectedRow == r && selectedCol == c) {
                boolean even = ((r + c) % 2 == 0);
                view.getCells()[r * 10 + c].setBorder(new LineBorder(Color.GRAY, 1));
                selectedRow = -1;
                selectedCol = -1;
                return;
            }
            String movingVal = model.getCell(selectedRow, selectedCol);
            int owner = model.getOwner(selectedRow, selectedCol);

            model.setCell(r, c, movingVal, owner);
            model.setCell(selectedRow, selectedCol, "", 0);

            actualizarVista();
            view.agregarConsola("Movimiento: (" + selectedRow + "," + selectedCol + ") -> (" + r + "," + c + ")");

            selectedRow = -1;
            selectedCol = -1;
        }
    }

    // DIBUJAR LOS EJÉRCITOS
    private void dibujarEjercitos() {
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                String letra = model.getCell(r, c);
                int owner = model.getOwner(r, c);

                Color base = ((r + c) % 2 == 0) ? GameView.CELDA_CLARA : GameView.CELDA_OSCURA;
                if (!letra.isEmpty()) {
                    if (owner == 1)
                        base = GameView.EJERCITO1;
                    if (owner == 2)
                        base = GameView.EJERCITO2;
                }
                view.setCellEstilizado(r, c, letra, base);
            }
        }
    }

    // actualiza la vista según el contenido del modelo
    private void actualizarVista() {
        view.limpiarConsola();
        view.agregarConsola(model.generarTextoConsola());

        Color azulPetroleo = GameView.EJERCITO1;
        Color rojoVino = GameView.EJERCITO2;

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                String letra = model.getCell(r, c);
                int owner = model.getOwner(r, c);

                Color base = ((r + c) % 2 == 0) ? GameView.CELDA_CLARA : GameView.CELDA_OSCURA;
                if (!letra.isEmpty()) {
                    if (owner == 1)
                        base = azulPetroleo;
                    if (owner == 2)
                        base = rojoVino;
                }

                view.setCellEstilizado(r, c, letra, base);
            }
        }
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

    // abrir y mostrar archivo texto
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

    // GUARDAR partida binaria
    private void guardarPartidaBinario(String archivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(model);
            JOptionPane.showMessageDialog(view, "Partida guardada en " + archivo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al guardar partida: " + e.getMessage());
        }
    }

    // CARGAR partida binaria
    private void cargarPartidaBinario(String archivo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            GameModel loaded = (GameModel) in.readObject();
            if (loaded != null) {

                this.model = loaded;
                view.clearBoard();
                actualizarVista();

                JOptionPane.showMessageDialog(view, "Partida cargada con éxito.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al cargar partida: " + e.getMessage());
        }
    }

    public class RankingDAO {

        public static void guardarResultado(String ganador, String perdedor, int soldadosVivos) {
            System.out.println("GUARDANDO EN BD: " + ganador + " | " + perdedor + " | " + soldadosVivos);

            String sql = "INSERT INTO ranking (ganador, perdedor, soldados_vivos) VALUES (?, ?, ?)";

            try {
                Connection conn = Conexion.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setString(1, ganador);
                stmt.setString(2, perdedor);
                stmt.setInt(3, soldadosVivos);

                stmt.executeUpdate();
                stmt.close();

                System.out.println("GUARDADO CON ÉXITO");

            } catch (SQLException e) {
                System.out.println("ERROR AL GUARDAR: " + e.getMessage());
            }
        }

    }

}

/*
 * MAIN - inicia el MVC
 */
public class Laboratorio14 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameModel model = new GameModel();
            GameView view = new GameView();
            new GameController(model, view);
            view.setVisible(true);
        });
    }
}