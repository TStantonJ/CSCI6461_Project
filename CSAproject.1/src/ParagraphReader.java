import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ParagraphReader {

    public static void main(String[] args) {

        // The path of the text file to read
        String filePath = "/Users/shahin/NetBeansProjects/LearningProjects/JavaBasicsConcepts/file.txt";

        // A StringBuilder to store the contents of the file
        StringBuilder stringBuilder = new StringBuilder();

        try {
            // Create a FileReader object to read the file
            FileReader fileReader = new FileReader(filePath);

            // Create a BufferedReader object to read the FileReader object
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Read each line of the file and append it to the StringBuilder
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n"); // Add a newline character after each line
            }

            // Close the BufferedReader and FileReader objects
            bufferedReader.close();
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert the StringBuilder to a String and store it in a variable
        String paragraph = stringBuilder.toString();
          // The paragraph to search in
//        String paragraph = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ut purus nulla. Etiam tempor justo augue, eget bibendum risus vestibulum id. Quisque viverra non magna non fermentum. Integer convallis augue sit amet ullamcorper consequat. Praesent auctor fringilla risus, ac blandit leo dignissim ac. In hac habitasse platea dictumst. Suspendisse potenti. Maecenas quis dolor enim. Morbi posuere nisi felis, a rhoncus ipsum elementum sit amet. Sed sit amet odio eu elit iaculis posuere.";
        
        // The word to search for
        String word = "amet";
        
        // Split the paragraph into an array of words
        String[] words = paragraph.split(" ");
        
        // Loop through each word in the array
        for (int i = 0; i < words.length; i++) {
            
            // If the word is found, print a message and break out of the loop
            if (words[i].equalsIgnoreCase(word)) {
                System.out.println("The word '" + word + "' was found at position " + i + ".");
                break;
            }
        }

        // Print the paragraph
        System.out.println(paragraph);
    }
}
