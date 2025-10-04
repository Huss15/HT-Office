package com.hassuna.tech.htoffice;



import org.mustangproject.ZUGFeRD.ZUGFeRDImporter;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class Reader {

    public static void main(String[] args) throws URISyntaxException {

        ClassLoader classLoader = Reader.class.getClassLoader();
        URL resource = classLoader.getResource("empty.pdf");
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        }
        String filePath = Paths.get(resource.toURI()).toString();
        ZUGFeRDImporter zu = new ZUGFeRDImporter(filePath);
        System.out.println("Total amount: "+zu.getAmount());

    }
}
