package com.phili.business;


import com.phili.business.entity.Customer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
  private final String filePath = "src/main/resources/";

    public static void main(String[] args) throws IOException {
        new MailOrderBusinessApplication().start();
    }


}