import view.Login;

public class Main {
    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            GameFrame gameFrame = new GameFrame(700, 500);
//            gameFrame.setVisible(true);
//            gameFrame.dispose();
            Login login = new Login();
            login.setBounds(400,200,500,300);
            login.setVisible(true);
//        });
    }
}
