package com.tutorialspoint.lucene;

import java.io.BufferedWriter;
import java.io.File;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Scanner;

public class Cria_arquivos {

    public void insere_arquivo(String url) throws IOException {

        String[] division;
        division = url.split("//");

        File file = new File("arquivos/" + division[1] + ".html");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter arq = new FileWriter("arquivos/" + division[1] + ".html");

        BufferedWriter buffW = new BufferedWriter(arq);
        try {
            Document doc = Jsoup.connect(url).get();
            buffW.write(doc.toString());
            buffW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleta_arquivos() {
        File folder = new File("arquivos/");
        if (folder.isDirectory()) {
            File[] sun = folder.listFiles();
            for (File toDelete : sun) {
                toDelete.delete();
            }
        }
    }
}
