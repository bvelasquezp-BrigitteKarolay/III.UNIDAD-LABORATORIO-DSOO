
/* CLASE MAIN INICIA EL PATRON MVC*/
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            GameModel model = new GameModel();
            GameView view = new GameView();
            GameController controller = new GameController(model, view);
            view.setVisible(true);
        });
    }
}
