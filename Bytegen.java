import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;

public class Bytegen {

    public static void main(String[] args) {
        if (args.length < 2) { //takes in number of bytes and output file
            System.out.println("Usage: java Bytegen <numBytes> <outputFile>");
            return;
        }

        int totalBytes = Integer.parseInt(args[0]);
        String outputFile = args[1];

        SecureRandom rand = new SecureRandom();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 .,!?";

        try (FileWriter writer = new FileWriter(outputFile, true)) {
            int bytesWritten = 0;

            while (bytesWritten < totalBytes) {
                // Determine remaining bytes
                int remaining = totalBytes - bytesWritten;

                // Block is 100 chars; if we have less than 100 bytes left, generate smaller block
                int blockSize = Math.min(100, remaining);

                StringBuilder block = new StringBuilder(blockSize);

                // Generate the block
                for (int i = 0; i < blockSize; i++) { //each char is 1 byte
                    block.append(chars.charAt(rand.nextInt(chars.length()))); //add random character from characters available
                }

                // Write the 100-char block
                writer.write(block.toString());
                bytesWritten += blockSize;

                // Add newline
                if (bytesWritten < totalBytes) {
                    writer.write("\n");
                    bytesWritten += 1;
                }

                writer.flush(); //save to file in 100 byte blocks
            }

            System.out.println("Wrote " + bytesWritten + " bytes to " + outputFile);

        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}
