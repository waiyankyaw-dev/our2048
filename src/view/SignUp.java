package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SignUp extends JFrame {
    JTextField text1, text2;
    JButton button1, button2;
    JLabel label1, label2;


    public SignUp() {
        setLayout(null); //no layout, u can put flow or grid if u want
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        label1 = new JLabel("SignUp");
        label1.setFont(new Font("Times New Roman", Font.BOLD, 30));
        label1.setForeground(Color.black);
        label1.setBounds(120, 10, 300, 30);
        add(label1);


        text1 = new JTextField(60);
        text2 = new JPasswordField(60);
        button1 = new JButton("SingUp");
        button2 = new JButton("Login");


        text1.setBounds(100, 60, 120, 30);
        text2.setBounds(100, 100, 120, 30);
        button1.setBounds(120, 140, 80, 30);
        button2.setBounds(120, 190, 80, 30);

        label2 = new JLabel("");
        label2.setBounds(250, 80, 300, 30);
        add(label2);


        add(text1);
        add(text2);
        add(button1);
        add(button2);


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //writeCode

                //validate empty username and empty password
                if (text1.getText().isEmpty() || text2.getText().isEmpty()) {
                    label2.setText("must not be empty");
                } else
                    //validate no spacing is allowed in username and password

                    if (containsWhiteSpace(text1.getText()) || containsWhiteSpace(text2.getText())) {
                        label2.setText("Username and Password must not have space");


                    } else  //check whether account already exist!
                        if (isAccountExist(text1.getText(), text2.getText())) {
                            label2.setText("Account already exist!. Just login in.");
                        } else {
                            try {
                                FileWriter fw = new FileWriter("login.txt", true);
                                fw.write(text1.getText() + "\t" + text2.getText() + "\n");
                                fw.close();
                                JFrame f = new JFrame();
                                JOptionPane.showMessageDialog(f, "Registration Completed!");
                                dispose();
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //writeCode
                dispose();
                Login login = new Login();
                login.setVisible(true);
                login.setBounds(400, 200, 500, 300);
            }
        });


    }

    public static boolean containsWhiteSpace(String line) {
        boolean space = false;
        if (line != null) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == ' ') {
                    space = true;
                }
            }
        }
        return space;
    }

    public static boolean isAccountExist(String userName, String password) {
        try {
            FileReader fr = new FileReader("login.txt");
            BufferedReader br = new BufferedReader(fr); //read line by line
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(userName + "\t" + password)) {
                    return true;
                }
            }
            fr.close();
        } catch (Exception error) {
            throw new RuntimeException(error);
        }
        return false;
    }
}
