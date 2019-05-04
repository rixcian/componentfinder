package mendelu.xkremece;

import java.io.*;
import java.nio.file.Files;

public class Main {

    public static void main(String[] args) {

        IComponentFinder componentFinder;
        componentFinder = new ComponentFinder();

        if (args.length <= 0) {

            ConsoleInput input = new ConsoleInput(componentFinder);
            input.readFromConsole();
            input.runComponentFinder();

        } else if (args[0].equals("--gui")){
            System.out.println("Spouštím grafické rozhraní ...");

            GraphicalInput.main(args);

        } else {

            ConsoleInput input = new ConsoleInput(componentFinder, args[0]);
            input.readFromFile();
            input.runComponentFinder();

        }
    }
}
