package nl.inholland.GuitarShop_UI;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.inholland.GuitarShop_DAO.GitaarDatabase;
import nl.inholland.GuitarShop_Models.Gebruiker;

import javax.swing.*;

public class LoginScherm {
    private Stage window;
    private GitaarDatabase gitaarDatabase;

    public LoginScherm(GitaarDatabase gitaarDatabase) {
        this.gitaarDatabase = gitaarDatabase;
        window = new Stage();

        GridPane grid = maakGridPane();

        Scene scene = new Scene(grid);
        window.setScene(scene);

        window.show();
    }

    private void loginButtonAction(Button loginButton, TextField passwordInput, TextField userInput) {

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String wachtwoord = passwordInput.getText();
                String gebruikersnaam = userInput.getText();
                Gebruiker gebruiker = null;
                if (wachtwoord.length() != 0 && gebruikersnaam.length() != 0) {
                    try {
                        gebruiker = gitaarDatabase.checkInlog(gebruikersnaam, wachtwoord);

                        if (gebruiker != null) {
                            new Dashboard(gebruiker, gitaarDatabase);
                            window.close();
                        }
                        else
                        {
                            String bericht = "De combinatie van inloggegevens zijn onjuist.";
                            JOptionPane.showMessageDialog(null, bericht);
                        }

                    } catch (Exception exception) {
                        String bericht = "Connectie met de database is niet gelukt.";
                        JOptionPane.showMessageDialog(null, bericht);
                    }
                }
                else
                    {
                    String bericht = "Vul eerst alle velden in.";
                    JOptionPane.showMessageDialog(null, bericht);
                }
            }
        });
    }

    private GridPane maakGridPane()
    {
        GridPane grid = new GridPane();

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        Label userLabel = new Label("Gebruikersnaam: ");
        GridPane.setConstraints(userLabel, 0, 0);

        Label passwordLabel = new Label("Wachtwoord: ");
        GridPane.setConstraints(passwordLabel, 0, 1);

        TextField userInput = new TextField();
        userInput.setPromptText("Gebruikersnaam");
        GridPane.setConstraints(userInput, 1, 0);

        TextField passwordInput = new PasswordField();
        passwordInput.setPromptText("Wachtwoord");
        GridPane.setConstraints(passwordInput, 1, 1);

        Button loginButton = new Button("Log in");
        GridPane.setConstraints(loginButton, 1, 2);

        grid.getChildren().addAll(userLabel, userInput, passwordLabel,
                passwordInput, loginButton);

        loginButtonAction(loginButton, passwordInput, userInput);

        return grid;
    }

}
