package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextAlignment;
import model.Data;

/**
 *
 * @author Loïc
 */
public class SelectedView extends HBox {
    
    private final Data data;

    public SelectedView(Data data) {
        this.data = data;
        initialize();
    }

    private void initialize() {
        
        setMinWidth(650);
        setMaxWidth(650);
        setStyle("-fx-border-width: 0 0 1 0;-fx-border-color: black");
        
        Label picsLabel = new Label("Selected pic(s): 0");
        picsLabel.setMaxWidth(1000);
        picsLabel.setAlignment(Pos.CENTER);
        HBox.setHgrow(picsLabel, Priority.ALWAYS);
        Label valleysLabel = new Label("Selected valley(s): 0");
        valleysLabel.setMaxWidth(1000);
        valleysLabel.setAlignment(Pos.CENTER);
        HBox.setHgrow(valleysLabel, Priority.ALWAYS);
        
        getChildren().addAll(picsLabel, valleysLabel);
        
        data.selectedChangedProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                picsLabel.setText("Selected pic(s): " + data.getPics().size());
                valleysLabel.setText("Selected valley(s): " + data.getValleys().size());
            }
        });
        
    }

}
