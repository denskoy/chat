package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public TextArea textArea;
    @FXML
    public TextField textField;
    private final String IP_ADDRESS = "localhost";
    private final int PORT = 8189;
    @FXML
    public HBox authPanel;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public HBox chatPanel;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean authentificated;
    private String nickName;
    private final String TITLE = "GeekChat";

    private void setAutentificated(boolean autentificated) {
        this.authentificated = autentificated;
        authPanel.setVisible(!autentificated);
        authPanel.setManaged(!autentificated);
        chatPanel.setVisible(autentificated);
        chatPanel.setManaged(autentificated);
        if (!autentificated){
            nickName = "";
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(new Runnable() {
                @Override
                public void run() {


                    try {
                        while (true) {
                            String str = null;
                            str = in.readUTF();


                            if (str.equals("/end")) {
                                System.out.println("Клиент отключился");
                                break;
                            }
                            System.out.println("Клиент: " + str);
                            textArea.appendText(str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("Мы отключились от сервера");
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(ActionEvent actionEvent) {
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToAuth(ActionEvent actionEvent) {

    }
    private void setTitle(String nick){

        ((Stage)textField.getScene().getWindow()).setTitle(TITLE + " " + nick);
    }
}
