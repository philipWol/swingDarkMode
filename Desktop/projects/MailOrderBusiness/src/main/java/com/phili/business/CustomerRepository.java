package com.phili.business;

import com.phili.business.entity.Address;
import com.phili.business.entity.Customer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepository {
    private final String filePath =  "src/main/resources/";
    private final String customerCsvFileName = "customers.csv";
    private static CustomerRepository customerRepository = null;
    List<Customer> customers;

    private CustomerRepository() {
        this.customers = loadCustomers();
    }

    public static CustomerRepository getInstance(){
        if(customerRepository == null){
            customerRepository = new CustomerRepository();
        }
        return customerRepository;
    }

    private List<Customer> loadCustomers(){
        List<Customer> customers =   new ArrayList<>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filePath, customerCsvFileName))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] dataFromCSVByLine = currentLine.split(",");
                Customer customer = new Customer(dataFromCSVByLine[0], dataFromCSVByLine[1], dataFromCSVByLine[2], null);
                customers.add(customer);
            }
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.err.printf("%s %s", "invalid customer csv data or unable to read customers csv file: ", e.getMessage() );
            e.printStackTrace();
        }
        return customers;
    }

    public Optional<Customer> findCustomerByNumber(String customerNo) {
        return  customers.stream()
                        .filter(customer -> customerNo.equals(customer.getCustomerNumber()))
                        .findFirst();
    }

    public void save(Customer customer) {
       String customerCSV = customer.toCsv();
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(filePath, customerCsvFileName), StandardOpenOption.APPEND)) {
            bufferedWriter.write(customerCSV + "\r\n");
            customers.add(customer);
        } catch (IOException e) {
            System.err.printf("%s %s", "unable to write to customer csv file: ", e.getMessage() );
            e.printStackTrace();
        }
    }

    public void update(Customer customer, String fieldName, Object data) {
        switch (fieldName){
            case "firstname" -> customer.setFirstName((String)data);
            case "lastname" -> customer.setLastName((String)data);
            case "address" -> customer.setAddress((Address) data);
        }
        Customer updateCustomer = new Customer(customer.getCustomerNumber(), customer.getFirstName(), customer.getLastName(), customer.getAddress());
        Boolean isCustomerCSVUpdated = updateCustomerInCSVFile(updateCustomer);
        if(isCustomerCSVUpdated) {
            customers.remove(customer);
            customers.add(updateCustomer);
        } else {
            restoreOldCsvData();
        }
    }

    /***
     * restoreOldCsvData - add old csv back to customer csv file
     */
    private void restoreOldCsvData() {
        clearOldCustomerFileContent();
        for (Customer customer: customers) {
            try (BufferedWriter bufferedWriter =  Files.newBufferedWriter(Paths.get(filePath, customerCsvFileName),
                    StandardOpenOption.APPEND)) {
                bufferedWriter.write(customer.toCsv() + "\r\n");
            } catch (IOException e) {
                System.err.printf("%s %s", "unable to restore old customer csv data: ", e.getMessage());
            }
        }

    }

    private Boolean updateCustomerInCSVFile(Customer customerToUpdate) {
        if(clearOldCustomerFileContent()){
            for (Customer customer: customers) {
                try (BufferedWriter bufferedWriter =  Files.newBufferedWriter(Paths.get(filePath, customerCsvFileName),
                                     StandardOpenOption.APPEND)) {
                    if(customer.getCustomerNumber().equals(customerToUpdate.getCustomerNumber())) {
                        bufferedWriter.write(customerToUpdate.toCsv()  + "\r\n");
                    }
                    else {
                        bufferedWriter.write(customer.toCsv() + "\r\n");
                    }
                } catch (IOException e) {
                    System.err.printf("%s %s", "unable to write to customer csv file: ", e.getMessage());
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean clearOldCustomerFileContent() {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(filePath, customerCsvFileName))) {
            bufferedWriter.write("");
            return true;
        } catch (IOException e) {
            System.err.printf("%s %s", "unable to clear customer csv file: ", e.getMessage() );
            return false;
        }
    }
}
