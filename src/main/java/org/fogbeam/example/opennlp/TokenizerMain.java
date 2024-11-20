package org.fogbeam.example.opennlp;

import java.io.BufferedWriter;
import java.io.FileInputStream;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class TokenizerMain
{
    public static void main( String[] args ) throws Exception
    {
        if (args.length < 2) {
            System.out.println("Usage: TokenizerMain <output-file> <input-file1> <input-file2> ...");
            return;
        }

        String outputFilePath = args[0];
        String[] inputFilePaths = Arrays.copyOfRange(args, 1, args.length);
		

        // Load the trained model
        try (InputStream modelIn = new FileInputStream("models/en-token.model")) {
            TokenizerModel model = new TokenizerModel(modelIn);
            Tokenizer tokenizer = new TokenizerME(model);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
                for (String inputFilePath : inputFilePaths) {
                    List<String> lines = Files.readAllLines(Paths.get(inputFilePath));
                    for (String line : lines) {
                        String[] tokens = tokenizer.tokenize(line);
                        for (String token : tokens) {
                            writer.write(token);
                            writer.write(" ");
                        }
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
        }
    }
}