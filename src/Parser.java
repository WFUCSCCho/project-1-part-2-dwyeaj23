/**
 * @file: Parser.java
 * @description: This class represents a parser that parses input.txt.
 * @author: Andrew Dwyer
 * @date: September 20, 2024
 */

import java.io.*;
import java.util.Scanner;

public class Parser {

    // Create a BST tree of Integer type
    private BST<Integer> mybst = new BST<>();

    // Constructor that accepts a filename to process
    public Parser(String filename) throws FileNotFoundException {
        process(new File(filename)); // Call process on the input file
    }

    // Process the input file and handle commands
    public void process(File input) throws FileNotFoundException {
        Scanner scanner = new Scanner(input); // Open the input file for reading

        // Iterate through each line of the input file
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim(); // Remove leading and trailing spaces
            if (line.isEmpty()) {
                continue; // Skip blank lines
            }

            // Split the command by spaces
            String[] command = line.split("\\s+");

            // Call operate_BST to execute the command
            operate_BST(command);
        }
        scanner.close(); // Close the scanner
    }

    // Determine the incoming command and operate on the BST
    public void operate_BST(String[] command) {
        String operation = command[0].toLowerCase(); // Convert to lowercase to handle case sensitivity
        try {
            switch (operation) {
                case "insert":
                    // Insert the value into the BST
                    int insertValue = Integer.parseInt(command[1]);
                    mybst.insert(insertValue);
                    writeToFile("insert " + insertValue, "./result.txt");
                    break;

                case "search":
                    // Search the BST for the value
                    int searchValue = Integer.parseInt(command[1]);
                    boolean found = mybst.search(searchValue);
                    if (found) {
                        writeToFile("found " + searchValue, "./result.txt");
                    } else {
                        writeToFile("search failed", "./result.txt");
                    }
                    break;

                case "remove":
                    // Remove the value from the BST
                    int removeValue = Integer.parseInt(command[1]);
                    mybst.remove(removeValue);
                    writeToFile("removed " + removeValue, "./result.txt");
                    break;

                case "print":
                    // Print the BST in ascending order
                    StringBuilder result = new StringBuilder();
                    for (int value : mybst) {
                        result.append(value).append(" ");
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