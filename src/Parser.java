import java.io.*;
import java.util.Scanner;

public class Parser {

    // Create a BST tree of Pokemon type
    private BST<Pokemon> pokemonBST = new BST<>();

    // Constructor that accepts a filename to process
    public Parser(String filename) throws FileNotFoundException {
        process(new File(filename)); // Call process on the input file
    }

    // Process the input file and handle commands
    public void process(File input) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(input)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim(); // Remove leading and trailing spaces
                if (line.isEmpty()) continue; // Skip blank lines

                // Split the command by spaces or commas
                String[] command = line.split("\\s+");

                // Call operate_BST to execute the command
                operate_BST(command);
            }
        }
    }

    // Determine the incoming command and operate on the BST
    public void operate_BST(String[] command) {
        String operation = command[0].toLowerCase(); // Convert to lowercase to handle case sensitivity
        try {
            switch (operation) {
                case "insert":
                    if (command.length < 14) { // Expecting 14 attributes for Pokemon
                        writeToFile("Invalid Command", "./result.txt");
                        return;
                    }
                    // Parse the Pokemon attributes from the command
                    int id = Integer.parseInt(command[1]);
                    String name = command[2];
                    String type1 = command[3];
                    String type2 = command[4].equals("None") ? "" : command[4];
                    int total = Integer.parseInt(command[5]);
                    int hp = Integer.parseInt(command[6]);
                    int attack = Integer.parseInt(command[7]);
                    int defense = Integer.parseInt(command[8]);
                    int specialAttack = Integer.parseInt(command[9]);
                    int specialDefense = Integer.parseInt(command[10]);
                    int speed = Integer.parseInt(command[11]);
                    int generation = Integer.parseInt(command[12]);
                    boolean isLegendary = Boolean.parseBoolean(command[13]);

                    // Create Pokemon object and insert it into the BST
                    Pokemon pokemon = new Pokemon(id, name, type1, type2, total, hp, attack, defense,
                            specialAttack, specialDefense, speed, generation, isLegendary);
                    pokemonBST.insert(pokemon);
                    writeToFile("insert " + name, "./result.txt");
                    break;

                case "search":
                    if (command.length < 2) {
                        writeToFile("Invalid Command", "./result.txt");
                        return;
                    }
                    // Search by name
                    String searchName = command[1];
                    System.out.println(searchName);
                    Pokemon searchPokemon = new Pokemon(0, searchName, "", "", 0, 0, 0, 0, 0, 0, 0, 0, false);
                    boolean found = pokemonBST.search(searchPokemon);
                    if (found) {
                        writeToFile("found " + searchName, "./result.txt");
                    } else {
                        writeToFile("search failed", "./result.txt");
                    }
                    break;

                case "remove":
                    if (command.length < 2) {
                        writeToFile("Invalid Command", "./result.txt");
                        return;
                    }
                    // Remove by name
                    String removeName = command[1];
                    Pokemon removePokemon = new Pokemon(0, removeName, "", "", 0, 0, 0, 0, 0, 0, 0, 0, false);
                    pokemonBST.remove(removePokemon);
                    writeToFile("removed " + removeName, "./result.txt");
                    break;

                case "print":
                    // Print the BST in ascending order
                    StringBuilder result = new StringBuilder();
                    for (Pokemon p : pokemonBST) {
                        result.append(p.getName()).append(" ");
                    }
                    writeToFile(result.toString().trim(), "./result.txt");
                    break;

                default:
                    // Handle invalid commands
                    writeToFile("Invalid Command", "./result.txt");
                    break;
            }
        } catch (Exception e) {
            // Handle invalid input cases (e.g., missing parameters)
            writeToFile("Invalid Command", "./result.txt");
        }
    }
    // Write the output to a result file
    public void writeToFile(String content, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(content); // Write the content
            writer.newLine(); // Add a new line
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}