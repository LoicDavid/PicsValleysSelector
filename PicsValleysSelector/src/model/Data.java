package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Loïc
 */
public class Data {
    
    private final ArrayList<Double> yList = new ArrayList<>();
    private final ArrayList<Double> timeList = new ArrayList<>();
    private final ArrayList<Integer> pics = new ArrayList<>();
    private final ArrayList<Integer> valleys = new ArrayList<>();
    private final StringProperty inputFile = new SimpleStringProperty();
    private final IntegerProperty changed = new SimpleIntegerProperty(0);
    private final IntegerProperty selectedChanged = new SimpleIntegerProperty(0);
    
    public Data() {}
    
    public void loadData(String inputFile) throws Exception {
        yList.clear();
        timeList.clear();
        String[] tab = inputFile.split("\\\\");
        this.inputFile.setValue(tab[tab.length - 1]);
        System.out.println(this.inputFile);
        InputStream flux = new FileInputStream(inputFile);
        InputStreamReader in = new InputStreamReader(flux);
        BufferedReader buff = new BufferedReader(in);
        buff.readLine();
        String line;
        while ((line = buff.readLine()) != null) {
            if (line.length() > 0) {
                String[] elements = line.split(";");
                double y = Double.parseDouble(elements[42]);
                if (y < 1000) {
                    yList.add(y);
                    timeList.add(Double.parseDouble(elements[0]));
                }
            }
        }
        buff.close();
        in.close();
        flux.close();
        changed.setValue(changed.getValue() + 1);
        selectedChanged.setValue(selectedChanged.getValue() + 1);
        
    }
    
    public void saveData(String outputFile) throws Exception {
        FileWriter fileWriter = new FileWriter(outputFile);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        String header = "pics;;valleys\ntime;value;time;value\n";
        writer.append(header);
        for (int i = 0; i < Math.max(pics.size(), valleys.size()); i++) {
            if (i >= pics.size()) {
                writer.append(";;" + timeList.get(valleys.get(i)) + ";" + yList.get(valleys.get(i)) + "\n");
            }
            else if (i >= valleys.size()) {
                writer.append(timeList.get(pics.get(i)) + ";" + yList.get(pics.get(i)) + "\n");
            }
            else {
                writer.append(timeList.get(pics.get(i)) + ";" + yList.get(pics.get(i)) + ";" + timeList.get(valleys.get(i)) + ";" + yList.get(valleys.get(i)) + "\n");
            }
        }
        writer.close();
        fileWriter.close();
    }

    public ArrayList<Double> getYList() {
        return yList;
    }

    public ArrayList<Double> getTimeList() {
        return timeList;
    }

    public ArrayList<Integer> getPics() {
        return pics;
    }

    public ArrayList<Integer> getValleys() {
        return valleys;
    }

    public StringProperty inputFileProperty() {
        return inputFile;
    }
    
    public IntegerProperty changedProperty() {
        return changed;
    }
    
    public IntegerProperty selectedChangedProperty() {
        return selectedChanged;
    }
    
}
