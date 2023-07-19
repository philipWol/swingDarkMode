package com.phili.business;

import com.phili.business.entity.Address;
import com.phili.business.entity.Customer;
import com.phili.business.entity.Product;

import java.util.Optional;
import java.util.Scanner;

public class MailOrderBusinessApplication {
   private final CustomerRepository customerRepository = CustomerRepository.getInstance();
    public void start() {
        welcomeScreen();
    }

    private void userOperation(int userOperation) {
        switch (userOperation) {
            case 1 -> loginUser();
            case 2 -> registerUser();
        }
    }

    private void loginUser() {
        System.out.println("Enter your customer number to Login: ");
        Scanner scanner = new Scanner(System.in);
        String customerNo = scanner.nextLine();
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByNumber(customerNo);

        if (optionalCustomer.isPresent()) {
            System.out.println("Welcome Back: " + customerNo);
            showCustomerDashboardOptions(optionalCustomer.get());
        } else {
            System.out.println("invalid customer number. try again");
            int loginOrRegister = getLoginOrRegisterUserResponse(scanner);
            userOperation(loginOrRegister);
        }

    }

    private void showCustomerDashboardOptions(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to do? ");
        System.out.println("""
                1. Edit Data
                2. View Products
                3. Logout
                """);
        String option = scanner.next();
        switch (option) {
            case "1" -> editCustomerData(customer.getCustomerNumber());
            case "2" -> showProducts();
            case "3" -> welcomeScreen();

        }

    }

    private void showProducts() {
        CustomerRepository customerRepository = CustomerRepository.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Editing data...");


    }

    private void editCustomerData(String customerNo) {
        CustomerRepository customerRepository = CustomerRepository.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Editing data...");
        var optionalCustomer = customerRepository.findCustomerByNumber(customerNo);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.print(); //old customer data
            System.out.println("""
                    1. first name
                    2. last name
                    3. address
                    """);
            String options = scanner.next();
            switch (options) {
                case "1" -> {
                    System.out.println("Enter new firstname");
                    var firstName = scanner.next();
                    customerRepository.update(customer, "firstname", firstName);
                }
                case "2" -> {
                    System.out.println("Enter new lastname");
                    String lastName = scanner.next();
                    customerRepository.update(customer, "lastname", lastName);
                }
                case "3" ->  showAddressEditOptionMenu(customer);
            }
            System.out.println("customer update success details is: ");
            customer = customerRepository.findCustomerByNumber(customerNo).get();
            customer.print();
            showCustomerDashboardOptions(customer);
        }
    }

    private void showAddressEditOptionMenu(Customer targetCustomerToUpdateAddress) {
            Address address = targetCustomerToUpdateAddress.getAddress();
            if(address == null) address = new Address();
            System.out.println("Choose address option to edit:");
            System.out.println("""
                    1. postal code
                    2. city
                    3. house number
                    """);
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next().trim();
            switch (option) {
                case "1" -> {
                    System.out.println("Enter new postal code");
                    var postalCode = scanner.next().trim();
                    address.setPostCode(postalCode);
                }
                case "2" -> {
                    System.out.println("Enter new city");
                    var city = scanner.next().trim();
                    address.setCity(city);
                }
                case "3" -> {
                    System.out.println("Enter new house number");
                    var houseNumber = scanner.next().trim();
                    address.setHouseNumber(Integer.valueOf(houseNumber));
                }
            }
            customerRepository.update(targetCustomerToUpdateAddress, "address", address);
            System.out.println("address update successful");
            showCustomerDashboardOptions(targetCustomerToUpdateAddress);
    }

    private void registerUser() {
        System.out.println("creating new customer account...");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer first name: ");
        String firstName = scanner.next();
        System.out.print("Enter customer last name: ");
        String lastName = scanner.next();
        System.out.println("Hello and Welcome: " + firstName +" " + lastName);
        System.out.println("Address details");
        System.out.print("Enter postal code: ");
        String postCode = scanner.next();
        System.out.print("Enter city name: ");
        String city = scanner.next();
        System.out.print("Enter house number: ");
        String houseNumber = scanner.next();
        while (!houseNumber.matches("\\d+")) {//if the entered house number doesn't match the Regex!!!
            System.out.print("Enter a valid house number: ");
            houseNumber = scanner.next();//keep the scanner open and ask again for a valid input
        }
        Address address = new Address(postCode, city, Integer.parseInt(houseNumber));//Integer.parseInt converts the inputted String number into an Integer
        String number = String.valueOf(System.currentTimeMillis()); // Generate a random Customer Number using the Java function 'currentTimeMillis '
        System.out.println("Registration successful. your customer number is: " + number);

        Customer customer = new Customer(number, firstName, lastName, address);//Here I saved the Created info in the CustomerRepository
        CustomerRepository customerRepository = CustomerRepository.getInstance();
        customerRepository.save(customer);
        userOperation(1);//afterward send the user back to the Dashboard

    }

    private int getLoginOrRegisterUserResponse(Scanner scanner) {
        System.out.println("""
                Enter
                1. Login
                2. Register
                """);
        String userInput = scanner.next();
        while (!userInput.equals("1") && !userInput.equals("2")) {
            userInput = scanner.next();
        }
        return Integer.parseInt(userInput);
    }

    private void welcomeScreen() {
        String welcomeText = """
                Welcome!
                ***Mail Order Business***
                """;
        System.out.print(welcomeText);
        System.out.println("Press Enter to continue ");
        var scanner = new Scanner(System.in);
        var input = scanner.nextLine();
        while (!input.isBlank()) {
            if (scanner.hasNextLine())
                break;
            input = scanner.nextLine();
        }
        int userInput = getLoginOrRegisterUserResponse(scanner);
        userOperation(userInput);
       // scanner.close();
    }
}
