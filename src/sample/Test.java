package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Test implements Initializable {

    @FXML
    Button btn;
    @FXML
    private VBox box;

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


    }
}
