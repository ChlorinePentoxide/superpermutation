package org.chlorinepentoxide.spt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class DirectStreamReader {

    public static Vector<String> read(String file) {
        Vector<String> data = new Vector<>(1,1);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while(true) {
                String st = bufferedReader.readLine();
                if(st == null) break;
                data.addElement(st);
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

}
