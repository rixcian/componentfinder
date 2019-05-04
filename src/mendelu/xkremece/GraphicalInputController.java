package mendelu.xkremece;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;

public class GraphicalInputController {

    @FXML
    private Label labelFilePath;

    @FXML
    private ListView<String> listViewResult;

    @FXML
    private ListView<String> listViewInput;

    private String fileName;

    public void onLoadFileButtonClicked() {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            labelFilePath.setText(file.getName());
            fileName = file.getAbsolutePath();
        }
    }

    public void onRunComponentFinderButtonClicked() {

        listViewInput.getItems().clear();
        listViewResult.getItems().clear();
        IComponentFinder componentFinder = new ComponentFinder();
        readFromFile(componentFinder);
        componentFinder.findComponents();
        writeOutput(componentFinder);

    }

    public void readFromFile(IComponentFinder componentFinder) {

        String line = null;

        try {

            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            boolean enterPressed = false;
            while((line = bufferedReader.readLine()) != null) {

                if (!enterPressed) {
                    // Cteme jednotlive uzly
                    if (!line.contains(",")) {
                        if (line.equals("")) {
                            enterPressed = true;
                            listViewInput.getItems().add("");
                        } else {
                            // Vytvorime novy uzel
                            try {
                                componentFinder.addNode(line);
                                listViewInput.getItems().add(" - Uzel " + line + " pridan!");
                            } catch (NodeException e) {
                                listViewInput.getItems().add(e.getMessage());
                            }
                        }
                    } else {
                        // Na vstupu je nevalidni uzel
                        listViewInput.getItems().add("Nezadali jste validní uzel!");
                    }
                } else {
                    // Cteme hrany mezi uzly
                    if (line.contains(",")) {
                        // vytvorime novou hranu
                        try {
                            String[] nodes = line.split(",");
                            componentFinder.addConnection(nodes[0], nodes[1]);
                            listViewInput.getItems().add(" - Hrana " + nodes[0] + "," + nodes[1] + " pridana!");
                        } catch (NodeException e) {
                            listViewInput.getItems().add(e.getMessage());
                        }
                    } else {
                        listViewInput.getItems().add("Nezadali jste validní hranu!");
                    }
                }
            }

        } catch (FileNotFoundException e) {
            listViewInput.getItems().add("Soubor " + fileName + " neexistuje!");
            // Modal okno s upozornenim !!!!
        } catch (IOException e) {
            listViewInput.getItems().add("Problém se čtením souboru " + fileName + "!");
            // Modal okno s upozornenim !!!!
        } catch (Exception e) {
            listViewInput.getItems().add(e.getMessage());
            // Modal okno s upozornenim !!!!
        }
    }

    private void writeOutput(IComponentFinder componentFinder) {

        listViewResult.getItems().add(new StringBuilder().append(componentFinder.getComponentsQuantity()).toString());

        listViewResult.getItems().add("");

        componentFinder.getComponents().forEach((singleComponent) -> {

            StringBuilder componentString = new StringBuilder();

            for (int i = 0; i < singleComponent.size(); i++) {
                if (i != singleComponent.size() - 1) {
                    componentString.append(singleComponent.get(i).getName() + ", ");
                } else {
                    componentString.append(singleComponent.get(i).getName());
                }
            }

            listViewResult.getItems().add(componentString.toString());

        });
    }

}
