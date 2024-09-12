/*Program Name: MatchGroupingSymbols.java
 * Authors: Austin P
 * Date last Updated: 9/12/2024
 * Purpose: This program is made to check if a java source-code file has correct
 * pairs of grouping symbols and the source-code file name is passed as a command-line argument
 * e.g. java MatchGroupingSymbols file_name.java
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class MatchGroupingSymbols {
    
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java GroupingSymbolsChecker <source-file>");
            return;
        }

        // The file name is passed as a command-line argument
        String fileName = args[0];
        
        try {
            // Open file
            File file = new File(fileName);
            Scanner input = new Scanner(file);

            // Stack to keep track of each of the grouping symbols
            Stack<Character> stack = new Stack<>();

            // Read the file line by line
            while (input.hasNextLine()) {
                String line = input.nextLine();

                // Process each character in the line
                // Named groupingSymbol since the program is focused on checking matching
                // or mismatched grouping symbols
                for (int i = 0; i < line.length(); i++) {
                    char groupingSymbol = line.charAt(i);

                    // Push opening symbols onto the stack
                    if (groupingSymbol == '(' || groupingSymbol == '{' || groupingSymbol == '[') {
                        stack.push(groupingSymbol);
                    }

                    // Checks the closing symbols
                    else if (groupingSymbol == ')' || groupingSymbol == '}' || groupingSymbol == ']') {
                        if (stack.isEmpty()) {
                            System.out.println("Mismatched symbol '" + groupingSymbol + "' found.");
                            input.close();
                            return;
                        }

                        char openSymbol = stack.pop();
                        if (!isMatchingPair(openSymbol, groupingSymbol)) {
                            System.out.println("Mismatched symbols: '" + openSymbol + "' and '" + groupingSymbol + "'.");
                            input.close();
                            return;
                        }
                    }
                }
            }

            input.close();

            // Checks if the stack is not empty, if so, then there are opening symbols that are unmatched 
            if (!stack.isEmpty()) {
                System.out.println("Unmatched opening symbol(s) found.");
            } else {
                System.out.println("All symbols are correctly matched.");
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }

    // Helper method to check if the grouping symbols are a matching pair
    private static boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '{' && close == '}') ||
               (open == '[' && close == ']');
    }
}
