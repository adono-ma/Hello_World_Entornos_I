import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class GetNextLine2 {
    private BufferedReader reader;
    private String buffer = "";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Initialize the file reader
            GetNextLine2 reader = new GetNextLine2("el_quijote.txt");
            String line;

            // Read and print one line at a time, waiting for the user to press Enter or type "stop"
            while ((line = reader.getNextLine()) != null) {
                System.out.println(line);  // Print the current line
                System.out.println("Press Enter to read the next line, or type 'stop' to exit.");
                
                // Get user input
                String input = scanner.nextLine();
                
                // Check if the user wants to stop
                if (input.equalsIgnoreCase("stop")) {
                    System.out.println("Stopping the program.");
                    break;
                }
            }

            // Close the reader when done
            reader.close();
        } catch (IOException e) {
            System.out.println("Error leyendo el archivo: " + e.getMessage());
        }
    }

    public GetNextLine2(String filePath) throws IOException {
        reader = new BufferedReader(new FileReader(filePath));
    }

    // Method to read the next line up to a newline character
    public String getNextLine() throws IOException {
        if (buffer == null) {
            return null;
        }

        while (!containsNewline(buffer)) {
            char[] chunk = new char[BUFFER_SIZE];
            int bytesRead = reader.read(chunk, 0, BUFFER_SIZE);

            if (bytesRead == -1) {
                String line = buffer;
                buffer = null;
                return line.isEmpty() ? null : line;
            }

            buffer += new String(chunk, 0, bytesRead);
        }

        String result = getResult(buffer);
        buffer = getStaticBuffer(buffer);
        return result;
    }

    // Method to check if there is a newline character in the buffer
    private boolean containsNewline(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '\n') {
                return true;
            }
        }
        return false;
    }

    // Extract content up to the newline character
    private String getResult(String str) {
        int i = indexOf(str, '\n');
        if (i == -1) i = str.length();

        StringBuilder result = new StringBuilder();
        for (int j = 0; j <= i; j++) {
            result.append(str.charAt(j));
        }
        return result.toString();
    }

    // Get remaining content after the newline
    private String getStaticBuffer(String str) {
        int i = indexOf(str, '\n');
        if (i == -1 || i == str.length() - 1) return null;

        StringBuilder sta = new StringBuilder();
        for (int j = i + 1; j < str.length(); j++) {
            sta.append(str.charAt(j));
        }
        return sta.toString();
    }

    // Find the position of a character
    private int indexOf(String str, char c) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c) {
                return i;
            }
        }
        return -1;
    }

    // Constant buffer size
    private static final int BUFFER_SIZE = 1024;

    public void close() throws IOException {
        reader.close();
    }
}
