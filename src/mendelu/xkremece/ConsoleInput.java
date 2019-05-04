package mendelu.xkremece;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ConsoleInput {

    private IComponentFinder componentFinder;
    private String fileName;

    public ConsoleInput(IComponentFinder componentFinder) {
        this.componentFinder = componentFinder;
        this.fileName = null;
    }

    public ConsoleInput(IComponentFinder componentFinder, String fileName) {
        this.componentFinder = componentFinder;
        this.fileName = fileName;
    }

    public void readFromConsole() {
        System.out.println("Vstup z konzolového rozhraní.");
        System.out.println("Nyní zadávejte hrany grafu např. A");

        Scanner input = new Scanner(System.in);
        boolean enterPressed = false;
        while (input.hasNextLine()) {
            String inputString = input.nextLine();

            if (!enterPressed) {
                // Cteme nazvy uzlu
                if (!inputString.contains(",")) {
                    if (inputString.equals("")) {
                        enterPressed = true;
                        System.out.println("Nyní zadávejte hrany grafu např. A,B");
                    } else {
                        // vytvorime novy uzel
                        try {
                            componentFinder.addNode(inputString);
                            System.out.println(" - Uzel " + inputString + " pridan!");
                        } catch (NodeException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                } else {
                    System.err.println("Nezadali jste validní uzel!");
                }
            } else {
                // Cteme hrany mezi uzly
                if (inputString.contains(",")) {
                    // vytvorime novou hranu
                    try {
                        String[] nodes = inputString.split(",");
                        componentFinder.addConnection(nodes[0], nodes[1]);
                        System.out.println(" - Hrana " + nodes[0] + "," + nodes[1] + " pridana!");
                    } catch (NodeException e) {
                        System.err.println(e.getMessage());
                    }

                } else {
                    System.err.println("Nezadali jste validní hranu!");
                }
            }
        }
    }

    public void readFromFile() {

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
                            System.out.println();
                        } else {
                            // Vytvorime novy uzel
                            try {
                                componentFinder.addNode(line);
                                System.out.println(" - Uzel " + line + " pridan!");
                            } catch (NodeException e) {
                                System.err.println(e.getMessage());
                            }
                        }
                    } else {
                        // Na vstupu je nevalidni uzel
                        System.err.println("Nezadali jste validní uzel!");
                    }
                } else {
                    // Cteme hrany mezi uzly
                    if (line.contains(",")) {
                        // vytvorime novou hranu
                        try {
                            String[] nodes = line.split(",");
                            componentFinder.addConnection(nodes[0], nodes[1]);
                            System.out.println(" - Hrana " + nodes[0] + "," + nodes[1] + " pridana!");
                        } catch (NodeException e) {
                            System.err.println(e.getMessage());
                        }
                    } else {
                        System.err.println("Nezadali jste validní hranu!");
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Soubor " + fileName + " neexistuje!");
            helpMessage();
        } catch (IOException e) {
            System.err.println("Problém se čtením souboru " + fileName + "!");
            helpMessage();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            helpMessage();
        }
    }

    public void runComponentFinder() {

        componentFinder.findComponents();
        this.writeOutput();

    }

    private void writeOutput() {

        System.out.println();

        System.out.println("-----  VÝSTUP -----");

        System.out.println(componentFinder.getComponentsQuantity());

        System.out.println();

        componentFinder.getComponents().forEach((singleComponent) -> {
            for (int i = 0; i < singleComponent.size(); i++) {
                if (i != singleComponent.size() - 1) {
                    System.out.print(singleComponent.get(i).getName() + ", ");
                } else {
                    System.out.print(singleComponent.get(i).getName());
                }
            }
            System.out.println();
        });
    }

    private void helpMessage() {

        System.out.println();
        System.out.println("-----  NÁPOVĚDA -----");
        System.out.println("Parametry (grafické rozhraní):");
        System.out.println("    --gui           zapne grafické rozhraní programu");
        System.out.println("Parametry (konzolové rozhraní):");
        System.out.println("    [soubor]        program načte vstupní hodnoty ze souboru");

    }
}
