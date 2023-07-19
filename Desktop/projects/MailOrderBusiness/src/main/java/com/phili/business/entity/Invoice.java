package com.phili.business.entity;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Invoice {

    private String customerNo;
    private List<Product> products;

    public Invoice(String customerName) {
        this.customerNo = customerName;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void print() {
        System.out.println("Invoice for: " + customerNo);
        System.out.println("------------------------------");
        double totalAmount = 0.0;
        for (Product product : products) {
            System.out.println("Title: " + product.getTitle());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Quantity: 1"); // Assuming quantity is always 1
            System.out.println("------------------------------");
            totalAmount += product.getPrice();
        }
        System.out.println("Total Amount: " + totalAmount);
        System.out.println("------------------------------");
        saveToFile();
    }

    private void saveToFile() {
        String filename = "invoice_" + customerNo.replace(" ", "_") + ".txt";
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write("Invoice for: " + customerNo + "\n");
            fileWriter.write("------------------------------\n");
            for (Product product : products) {
                fileWriter.write("Title: " + product.getTitle() + "\n");
                fileWriter.write("Price: " + product.getPrice() + "\n");
                fileWriter.write("Quantity: 1\n"); // Assuming quantity is always 1
                fileWriter.write("------------------------------\n");
            }
            fileWriter.write("Total Amount: " + getTotalAmount() + "\n");
            fileWriter.write("------------------------------\n");
        } catch (IOException e) {
            System.out.println("Error occurred while saving the invoice to file.");
            e.printStackTrace();
        }
    }

    private double getTotalAmount() {
        double totalAmount = 0.0;
        for (Product product : products) {
            totalAmount += product.getPrice();
        }
        return totalAmount;
    }
}
