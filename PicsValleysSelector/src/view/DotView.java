package view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Data;

/**
 *
 * @author Loïc
 */
public class DotView extends Circle {
    
    private final BooleanProperty pic = new SimpleBooleanProperty(false);
    private final BooleanProperty valley = new SimpleBooleanProperty(false);
    private final Integer index;
    private final Data data;

    public DotView(double centerX, double centerY, Integer index, Data data) {
        super(centerX, centerY, 2);
        this.index = index;
        this.data = data;
        initialize();
    }

    private void initialize() {
        
        addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (!valley.getValue()) {
                        if (pic.getValue()) {
                            setFill(Color.BLACK);
                            pic.setValue(false);
                            data.getPics().remove(index);
                            data.selectedChangedProperty().setValue(data.selectedChangedProperty().getValue() + 1);
                        }
                        else {
                            setFill(Color.RED);
                            pic.setValue(true);
                            data.getPics().add(index);
                            data.selectedChangedProperty().setValue(data.selectedChangedProperty().getValue() + 1);
                        }
                    }
                }
                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    if (!pic.getValue()) {
                        if (valley.getValue()) {
                            setFill(Color.BLACK);
                            valley.setValue(false);
                            data.getValleys().remove(index);
                            data.selectedChangedProperty().setValue(data.selectedChangedProperty().getValue() + 1);
                        }
                        else {
                            setFill(Color.BLUE);
                            valley.setValue(true);
                            data.getValleys().add(index);
                            data.selectedChangedProperty().setValue(data.selectedChangedProperty().getValue() + 1);
                        }
                    }
                }
            }
        });
        
    }

    public BooleanProperty picProperty() {
        return pic;
    }
    
    public BooleanProperty valleyProperty() {
        return valley;
    }
    
}
