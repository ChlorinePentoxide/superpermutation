package org.chlorinepentoxide.spt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class DirectStreamWriter {

    public static void write(Vector<String> data, String file) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for(String st:data) {
                bufferedWriter.write(st);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
