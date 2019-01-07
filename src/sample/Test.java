package sample;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Test implements Initializable {

    @FXML
    Button btn, loka;
    @FXML
    VBox box,bbox;
    @FXML
    Pane p2,p3,p4;
    @FXML
    StackPane stack1;
    @FXML
    HBox p1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

/*        stack1.hoverProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean show) -> {
            if (show) {
                p1.setVisible(false);
            } else {
                p1.setVisible(true);
            }
        });*/

        String demoAns = "abbcd";

        List<ToggleGroup> tgList = new ArrayList<>();
        List<StackPane> stpList = new ArrayList<>();
        List<HBox> hboxList = new ArrayList<>();

        for(int i=1;i<6;i++){
            Helper.MyRadioButton a = new Helper.MyRadioButton("A");
            Helper.MyRadioButton b = new Helper.MyRadioButton("B");
            Helper.MyRadioButton c = new Helper.MyRadioButton("C");
            Helper.MyRadioButton d = new Helper.MyRadioButton("D");

            ToggleGroup ansGroup = new ToggleGroup();

            if(!Helper.sendFile){
                char singleAns = demoAns.charAt(i-1);
                if(singleAns == 'a') a.fire();
                if(singleAns == 'b') b.fire();
                if(singleAns == 'c') c.fire();
                if(singleAns == 'd') d.fire();
            }

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

            hb.setAlignment(Pos.CENTER);

            Insets insp = new Insets(12,12,12,26);

            Insets insm = new Insets(6,12,6,12);

            hb.setPadding(insp);

            String white = "-fx-background-color: rgba(255, 255, 255);";

            String style1 = "-fx-background-color: rgba( 146, 202, 223 );";

            String style2 = "-fx-background-color: rgba(52, 152, 219);";

            hb.setStyle(white);

            HBox hbCover = new HBox(new Label("Question "+Integer.toString(i)));

            hbCover.setAlignment(Pos.CENTER);


            if(i%2 == 0){
                hbCover.setStyle(style1);
            }else{
                hbCover.setStyle(style2);
            }

            hboxList.add(hbCover);

            StackPane stMain = new StackPane(hb,hbCover);
            stMain.setPrefHeight(66);

            stpList.add(stMain);

            bbox.getChildren().add(stMain);

            tgList.add(ansGroup);

        }

        for(int i=0;i<5;i++){
            StackPane stp = stpList.get(i);
            HBox mhb = hboxList.get(i);

            stp.hoverProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean show) -> {
                if (show) {
                    mhb.setVisible(false);
                } else {
                    mhb.setVisible(true);
                }
            });

        }

        btn.setOnAction(e->{
            for(int i=0;i<5;i++){
                ToggleGroup t = tgList.get(i);
                Helper.MyRadioButton rb = (Helper.MyRadioButton) t.getSelectedToggle();

                System.out.println(i + " :  "+ rb.getText());
            }
        });

    }

    public void setPrint() {
        System.out.println("func");
    }
}
