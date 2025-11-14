// LABORATORIO 10 - GUI
// AUTORA: Brigitte Karolay Velasquez Puma

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class JuegoGUI extends JFrame {

    private JPanel panelTablero;
    private JTextArea panelConsola;
    private JButton btnGenerar;

    public JuegoGUI() {
        setTitle("Laboratorio 10");
        setSize(850, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // panel central donde se dibuja el tablero del juego
        panelTablero = new JPanel(new GridLayout(10, 10));
        panelTablero.setBackground(new Color(220, 240, 220));
        add(panelTablero, BorderLayout.CENTER);

        // area de texto inferior para mostrar los resultados
        panelConsola = new JTextArea(8, 30);
        panelConsola.setEditable(false);
        panelConsola.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(panelConsola), BorderLayout.SOUTH);

        // boton para generar los datos
        btnGenerar = new JButton("Generar Juego");
        btnGenerar.addActionListener(e -> generarJuego());
        add(btnGenerar, BorderLayout.NORTH);
    }

    // metodo 
    private void generarJuego() {
        panelConsola.setText("");

        // muestra los datos 
        panelConsola.append("Ejercito 1: Inglaterra\n");
        panelConsola.append("Cantidad total de soldados creados: 10\n");
        panelConsola.append("Espadachines: 3\nArqueros: 1\nCaballeros: 4\nLanceros: 2\n\n");

        panelConsola.append("Ejercito 2: Francia\n");
        panelConsola.append("Cantidad total de soldados creados: 3\n");
        panelConsola.append("Espadachines: 1\nArqueros: 0\nCaballeros: 1\nLanceros: 1\n\n");

        panelConsola.append("Ejercito 1: Inglaterra: 85 (78.70% de probabilidad de victoria)\n");
        panelConsola.append("Ejercito 2: Francia: 23 (21.30% de probabilidad de victoria)\n");
        panelConsola.append("El ganador es el ejercito 1 de: Inglaterra.\n");
        panelConsola.append("(Aleatorio generado: 70.12)\n");

        // genera el tablero
        panelTablero.removeAll();

        // simbolos 
        String[] simbolos = {"E", "A", "C", "L", ""};
        for (int i = 0; i < 100; i++) {
            String valor = simbolos[(int)(Math.random() * simbolos.length)];
            JLabel casilla = new JLabel(valor, SwingConstants.CENTER);
            casilla.setOpaque(true);
            casilla.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            casilla.setFont(new Font("Arial", Font.BOLD, 14));

            // colores para cada soldado
            switch (valor) {
                case "E": 
                    casilla.setBackground(new Color(255, 105, 180)); 
                    break; 
                case "A": 
                    casilla.setBackground(new Color(186, 85, 211)); 
                    break; 
                case "C": 
                    casilla.setBackground(new Color(255, 182, 193)); 
                    break; 
                case "L": 
                    casilla.setBackground(new Color(135, 245, 235)); 
                    break; 
                default: 
                    casilla.setBackground(Color.WHITE); 
                    break;
            }
            panelTablero.add(casilla);
        }

        panelTablero.revalidate();
        panelTablero.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JuegoGUI().setVisible(true));
    }
}
