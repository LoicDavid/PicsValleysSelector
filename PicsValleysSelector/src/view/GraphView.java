package view;

import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import model.Data;

/**
 *
 * @author Loïc
 */
public class GraphView extends Pane {
    
    private final Data data;
    private Label timeLabel;
    private Label yLabel;
    private final ArrayList<DotView> dots = new ArrayList<>();
    private final ArrayList<Line> lines = new ArrayList<>();
    private final ArrayList<Line> tGrads = new ArrayList<>();
    private final ArrayList<Label> tLabels = new ArrayList<>();
    private final ArrayList<Line> yGrads = new ArrayList<>();
    private final ArrayList<Label> yLabels = new ArrayList<>();
    private final IntegerProperty pointedChange = new SimpleIntegerProperty(0);
    private DotView pointedDot;
    private double timeScale;
    private double yScale;
    private double yOffset;
    private int tStep;
    private int yStep;
    private int yStart;
    private final StringProperty xCursor = new SimpleStringProperty("0");
    private final StringProperty yCursor = new SimpleStringProperty("0");

    public GraphView(Data data) {
        this.data = data;
        initialize();
    }

    private void initialize() {
        
        setMinSize(650, 450);
        setMaxSize(650, 450);
        
        Line xAxis = new Line(50, 400, 650, 400);
        xAxis.setStrokeWidth(2);
        Line xArrow1 = new Line(650, 400, 647, 397);
        Line xArrow2 = new Line(650, 400, 647, 403);
        Line yAxis = new Line(50, 0, 50, 400);
        yAxis.setStrokeWidth(2);
        Line yArrow1 = new Line(50, 0, 47, 3);
        Line yArrow2 = new Line(50, 0, 53, 3);
        getChildren().addAll(xAxis, yAxis, xArrow1, xArrow2, yArrow1, yArrow2);
        
        timeLabel = new Label("Time");
        yLabel = new Label("Y");
        getChildren().addAll(timeLabel, yLabel);
        Platform.runLater(() -> {
            timeLabel.setLayoutX(650 - timeLabel.getWidth());
            timeLabel.setLayoutY(450 - timeLabel.getHeight());
            yLabel.setLayoutX(0);
            yLabel.setLayoutY(0);
        });
        
        data.changedProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                updateGraph();
            }
        });
        
        addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                if (!tGrads.isEmpty() && x >= 50 && y <= 400) {
                    xCursor.setValue("" + (double)((int)(tViewToModel(x) * 100)) / 100);
                    yCursor.setValue("" + (double)((int)(yViewToModel(y) * 100)) / 100);
                }
            }
        });
        
    }
    
    private void updateGraph() {
        resetGraph();
        loadGraph();
    }
    
    private void loadGraph() {
        ArrayList<Double> timeList = data.getTimeList();
        ArrayList<Double> yList = data.getYList();
        double tMax = Collections.max(timeList);
        double yMin = Collections.min(yList);
        double yMax = Collections.max(yList);
        setCoordinatesSystem(tMax, yMin, yMax);
        for (int i = 0; i < timeList.size(); i++) {
            newDot(tModelToView(timeList.get(i)), yModelToView(yList.get(i)), i);
        }
        for (int i = 0; i < dots.size() - 1; i++) {
            DotView dot1 = dots.get(i);
            DotView dot2 = dots.get(i + 1);
            Line line = new Line(dot1.getCenterX(), dot1.getCenterY(), dot2.getCenterX(), dot2.getCenterY());
            line.setMouseTransparent(true);
            lines.add(line);
            getChildren().add(line);
        }
        Platform.runLater(() -> {
                for (int i = 0; i < tLabels.size(); i++) {
                    Label lab = tLabels.get(i);
                    lab.setLayoutX(tModelToView(tStep * i) - lab.getWidth() / 2);
                    lab.setLayoutY(405);
                }
                for (int i = 0; i < yLabels.size(); i++) {
                    Label lab = yLabels.get(i);
                    lab.setLayoutX(45 - lab.getWidth());
                    lab.setLayoutY(yModelToView(yStart + yStep * i) - lab.getHeight() / 2);
                }
            });
    }
    
    private void resetGraph() {
        resetCoordinatesSystem();
        resetDots();
    }
    
    private void setCoordinatesSystem(double tMax, double yMin, double yMax) {
        
        double tm = (((int)tMax) / 5 + 1) * 5;
        timeScale = 600 / tm;
        yStart = (((int)yMin) / 5 - 1) * 5;
        int ym;
        if (yMax < 0) {
            ym = (((int)yMax) / 5) * 5;
        }
        else {
            ym = (((int)yMax) / 5 + 1) * 5;
        }
        yScale = 400. / (yStart - ym);
        yOffset = -yScale * ym;
        
        tStep = ((int)((tm - 1) / 60) + 1) * 5;
        for (int i = 0; i < tm; i += tStep) {
            Line line = new Line(tModelToView(i), 398, tModelToView(i), 402);
            Label label = new Label("" + i);
            tGrads.add(line);
            tLabels.add(label);
            getChildren().addAll(line, label);
        }
        yStep = ((int)((ym - yStart - 1) / 40) + 1) * 5;
        for (int i = yStart; i < ym; i += yStep) {
            Line line = new Line(48, yModelToView(i), 52, yModelToView(i));
            Label label = new Label("" + i);
            yGrads.add(line);
            yLabels.add(label);
            getChildren().addAll(line, label);
        }

    }
    
    private void resetCoordinatesSystem() {
        for (Line line : tGrads) {
            getChildren().remove(line);
        }
        for (Line line : yGrads) {
            getChildren().remove(line);
        }
        for (Label label : tLabels) {
            getChildren().remove(label);
        }
        for (Label label : yLabels) {
            getChildren().remove(label);
        }
        tGrads.clear();
        tLabels.clear();
        yGrads.clear();
        yLabels.clear();
    }
    
    private void newDot(double x, double y, Integer index) {
        
        DotView dot = new DotView(x, y, index, data);
        dots.add(dot);
        getChildren().add(dot);
        
        dot.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dot.setRadius(3);
                pointedDot = dot;
                pointedChange.setValue(pointedChange.getValue() + 1);
            }
        });
        dot.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!dot.picProperty().getValue() && !dot.valleyProperty().getValue()) {
                    dot.setRadius(2);
                }
                pointedDot = null;
                pointedChange.setValue(pointedChange.getValue() + 1);
            }
        });
        
    }
    
    private void resetDots() {
        for (DotView dot : dots) {
            getChildren().remove(dot);
        }
        for (Line line : lines) {
            getChildren().remove(line);
        }
        dots.clear();
        lines.clear();
    }
    
    public double tModelToView(double t) {
        return timeScale * t + 50;
    }
    
    public double tViewToModel(double t) {
        return (t - 50) / timeScale;
    }
    
    public double yModelToView(double y) {
        return yScale * y + yOffset;
    }
    
    public double yViewToModel(double y) {
        return (y - yOffset) / yScale;
    }

    public DotView getPointedDot() {
        return pointedDot;
    }

    public StringProperty xCursorProperty() {
        return xCursor;
    }

    public StringProperty yCursorProperty() {
        return yCursor;
    }
    
    public IntegerProperty pointedChangeProperty() {
        return pointedChange;
    }
    
}
