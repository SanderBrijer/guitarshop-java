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

public class LoginScherm {
    private Stage window;
    private GitaarDatabase gitaarDatabase;

    public LoginScherm(GitaarDatabase gitaarDatabase)
    {
        GridPane grid = new GridPane();
        window = new Stage();
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

        StringProperty passwordFieldProperty = passwordInput.textProperty();

        Label visiblePass = new Label();
        GridPane.setConstraints(visiblePass, 0, 3);
        visiblePass.textProperty().bind(passwordFieldProperty);

        grid.getChildren().addAll(userLabel, userInput, passwordLabel,
                passwordInput, loginButton, visiblePass);

        loginButton.setVisible(true);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String wachtwoord = passwordInput.getText();
                String gebruikersnaam =userInput.getText();
                Gebruiker gebruiker = gitaarDatabase.checkInlog(gebruikersnaam, wachtwoord);

                if (gebruiker != null)
                {
                    new Dashboard(gebruiker, gitaarDatabase);
                    window.close();
                }
            }
        });

        Scene scene = new Scene(grid);
        window.setScene(scene);

        window.show();
    }
}
