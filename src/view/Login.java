package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

public class Login extends JFrame {
    JTextField text1, text2;
    JButton button1,button2;
    JLabel label1, label2;



    public Login() {
        setLayout(null); //no layout, u can put flow or grid if u want
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        label1 = new JLabel("REGISTRATION");
        label1.setFont(new Font("Times New Roman", Font.BOLD, 30));
        label1.setForeground(Color.black);
        label1.setBounds(110,10,300,30);
        add(label1);


        text1 = new JTextField(60);
        text2 = new JPasswordField(60);
        button1 = new JButton("LogIn");
        button2 = new JButton("SignUp");


        text1.setBounds(160,60,120,30);
        text2.setBounds(160,100,120,30);
        button1.setBounds(180,140,80,30);
        button2.setBounds(180,190,80,30);


        label2 = new JLabel("");
        label2.setBounds(250,80,300,30);
        add(label2);


        add(text1);
        add(text2);
        add(button1);
        add(button2);

        //check when log in
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //writeCode
                boolean matched = false;
                String userName = text1.getText();
                String password = text2.getText();
                try {
                    FileReader fr = new FileReader("login.txt");
                    BufferedReader br = new BufferedReader(fr); //read line by line
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.equals(userName+"\t"+password)) {
                            matched = true;
                        }
                    }
                    fr.close();
                } catch (Exception error) {
                    throw new RuntimeException(error);
                }


                if(matched) {
                    //open gameboard
//                    label2.setText("Successful!");
                    System.out.println("Login Successful");
                    dispose();
            GameFrame gameFrame = new GameFrame(700, 500, "waiyan", 6, 32);
            gameFrame.setVisible(true);
                }else{
                    JFrame f = new JFrame();
                    label2.setText("Invalid Username or Password!");
                }
            }
        });

        //check when signUp
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //writeCode
                dispose();
                SignUp signup = new SignUp();
                signup.setVisible(true);
                signup.setBounds(400,200,500,300);
                //validate no spacing is allowed in username and password
//                if(){
//
//                if (containsWhiteSpace(text1.getText()) || containsWhiteSpace(text2.getText())) {
//                    label2.setText("Username and Password must not have space");
//
//
//                } else  //check whether account already exist!
//                    if (isAccountExist(text1.getText(), text2.getText())) {
//                        label2.setText("Account already exist!. Just login in.");
//                    } else {
//                        try {
//                            FileWriter fw = new FileWriter("login.txt", true);
//                            fw.write(text1.getText() + " " + text2.getText() + "\n");
//                            fw.close();
//                            JFrame f = new JFrame();
//                            JOptionPane.showMessageDialog(f, "Registration Completed!");
//                            //After successful registration, open game
//                            dispose();
//                        } catch (IOException ex) {
//                            throw new RuntimeException(ex);
//                        }
//                    }
            }
        });

    }


}
