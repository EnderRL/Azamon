package IA.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.server.ExportException;

public class Escritor {

    private BufferedWriter bufferedWriter;

    public Escritor(String archivo) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(new File(archivo)));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean write(String s) {
        try {
            bufferedWriter.write(s);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void close() {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
