import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GetNextLine {
    private BufferedReader reader;
    private String buffer = "";

    public static void main(String[] args) {
        try {
            // Especifica la ruta del archivo de prueba
            GetNextLine reader = new GetNextLine("el_quijote.txt");
            String line;
            
            // Lee línea por línea hasta llegar al final del archivo
            while ((line = reader.getNextLine()) != null) {
                System.out.println(line);
            }
            
            // Cierra el lector para liberar recursos
            reader.close();
        } catch (IOException e) {
            System.out.println("Error leyendo el archivo: " + e.getMessage());
        }
    }

    public GetNextLine(String filePath) throws IOException {
                    reader = new BufferedReader(new FileReader(filePath));
    }

    // Método para leer la siguiente línea del archivo
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

    // Método para verificar si existe un salto de línea en el buffer
    private boolean containsNewline(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '\n') {
                return true;
            }
        }
        return false;
    }

    // Extrae el contenido hasta el salto de línea
    private String getResult(String str) {
        int i = indexOf(str, '\n');
        if (i == -1) i = str.length();

        StringBuilder result = new StringBuilder();
        for (int j = 0; j <= i; j++) {
            result.append(str.charAt(j));
        }
        return result.toString();
    }

    // Obtiene el contenido restante después del salto de línea
    private String getStaticBuffer(String str) {
        int i = indexOf(str, '\n');
        if (i == -1 || i == str.length() - 1) return null;

        StringBuilder sta = new StringBuilder();
        for (int j = i + 1; j < str.length(); j++) {
            sta.append(str.charAt(j));
        }
        return sta.toString();
    }

    // Encuentra la posición de un carácter
    private int indexOf(String str, char c) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c) {
                return i;
            }
        }
        return -1;
    }

    // Constante de tamaño de búfer
    private static final int BUFFER_SIZE = 1024; // O tamaño que desees
    
    public void close() throws IOException {
        reader.close();
    }
}
