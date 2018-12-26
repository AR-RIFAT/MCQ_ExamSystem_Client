package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Test implements Initializable {

    @FXML
    Button btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<ToggleGroup> tg = new ArrayList<>();

/*        for(int i=1;i<16;i++){
            RadioButton a = new RadioButton("A");
            RadioButton b = new RadioButton("B");
            RadioButton c = new RadioButton("C");
            RadioButton d = new RadioButton("D");

            ToggleGroup ansGroup = new ToggleGroup();

            a.setToggleGroup(ansGroup);
            b.setToggleGroup(ansGroup);
            c.setToggleGroup(ansGroup);
            d.setToggleGroup(ansGroup);

            Insets insets = new Insets(0,60,0,20);
            a.setPadding(insets);
            b.setPadding(insets);
            c.setPadding(insets);
            d.setPadding(insets);

            HBox hb = new HBox(new Label("Ques No. " + Integer.toString(i)+" :  "),a,b,c,d);

            Insets insp = new Insets(12,12,12,26);

            Insets insm = new Insets(6,12,6,12);

            hb.setPadding(insp);

            String style1 = "-fx-background-color: rgba(174, 214, 241);";

            String style2 = "-fx-background-color: rgba(253, 254, 254);";

            if(i%2 == 0){
                hb.setStyle(style1);
            }else{
                hb.setStyle(style2);
            }

            box.getChildren().add(hb);

            tg.add(ansGroup);

        }

        btn.setOnAction(e->{
            for(int i=0;i<15;i++){
                ToggleGroup t = tg.get(i);
                RadioButton rb = (RadioButton) t.getSelectedToggle();

                System.out.println(i + " :  "+ rb.getText());
            }
        });*/

        btn.setOnAction(e->{
// Create the custom dialog.
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Change Time");
            dialog.setHeaderText("Enter New Timeline");

// Set the icon (must be included in the project).
            dialog.setGraphic(new ImageView(this.getClass().getResource("clock.jpg").toString()));

// Set the button types.
            ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField start = new TextField();
            start.setPromptText("HH : MM");
            TextField duration = new TextField();
            duration.setPromptText("Time");

            grid.add(new Label("Start Time : "), 0, 0);
            grid.add(start, 1, 0);
            grid.add(new Label("Exam Duration : "), 0, 1);
            grid.add(duration, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
            Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
            loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
            start.textProperty().addListener((observable, oldValue, newValue) -> {
                loginButton.setDisable(newValue.trim().isEmpty());
            });

            dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
            Platform.runLater(() -> start.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    return new Pair<>(start.getText(), duration.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();

            result.ifPresent(usernamePassword -> {
                System.out.println("Start Time : " + usernamePassword.getKey() + ", Duration : " + usernamePassword.getValue());
            });
        });

    }
}
