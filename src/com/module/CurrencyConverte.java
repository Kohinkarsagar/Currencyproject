
package com.module;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class CurrencyConverte {
    private static final Map<String, Double> exchangeRates = new HashMap<>();
    private static final Map<String, String> supportedCurrencies = new HashMap<>();
    private static final Map<String, ConversionRecord> conversionHistory = new HashMap<>();
    private static final Map<String, String> favoriteCurrencies = new HashMap<>();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
   
    private static Scanner scanner = new Scanner(System.in);
   
  

    public static void main(String[] args) {
        System.out.println("************* Welcome To The Currency Converter! **************");
        initializeExchangeRates();
        initializeSupportedCurrencies();
       

        while (true) {
            displayMainMenu();

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    performCurrencyConversion();
                    break;
                case 2:
                    displaySupportedCurrencies();
                    break;
                case 3:
                    displayExchangeRates();
                    break;
                case 4:
                    addFavoriteCurrency();
                    break;
                case 5:
                    removeFavoriteCurrency();
                    break;
                case 6:
                    viewFavoriteCurrencies();
                    break;
                case 7:
                    performMultipleCurrencyConversion();
                    break;
                case 8:
                    displaySavedHistory();
                    break;
                case 9:
                    saveHistoryToFile();
                    System.out.println("Thank you for using the Currency Converter!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
   
	private static void initializeExchangeRates() {
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.85);
        exchangeRates.put("GBP", 0.74);
        exchangeRates.put("INR", 75.0);
    }
   

    private static void initializeSupportedCurrencies() {
        supportedCurrencies.put("USD", "United States Dollar");
        supportedCurrencies.put("EUR", "Euro");
        supportedCurrencies.put("GBP", "British Pound Sterling");
        supportedCurrencies.put("INR", "Indian Rupee");
        
    }

    private static void displayMainMenu() {
        System.out.println("\nCurrency Converter Menu:");
        System.out.println("1. Perform Currency Conversion");
        System.out.println("2. Display Supported Currencies");
        System.out.println("3. Display Exchange Rates");
        System.out.println("4. Add a Favorite Currency");
        System.out.println("5. Remove a Favorite Currency");
        System.out.println("6. View Favorite Currencies");
        System.out.println("7. Perform Multiple Currency Conversion");
        System.out.println("8. Display Conversion History");
        System.out.println("9. Save and Exit");
        System.out.print("Enter your choice: ");
    }

    private static void performCurrencyConversion() {
        System.out.print("Enter the amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); 

        System.out.println("Display Supported Currencies:");
        displaySupportedCurrencies();

        System.out.print("Enter the source currency code: ");
        String sourceCurrencyCode = scanner.nextLine().toUpperCase();

        System.out.print("Enter the target currency code: ");
        String targetCurrencyCode = scanner.nextLine().toUpperCase();

        if (exchangeRates.containsKey(sourceCurrencyCode) && exchangeRates.containsKey(targetCurrencyCode)) {
            double exchangeRate = exchangeRates.get(targetCurrencyCode) / exchangeRates.get(sourceCurrencyCode);
            double convertedAmount = amount * exchangeRate;

            System.out.println(amount + " " + supportedCurrencies.get(sourceCurrencyCode) +
                    " = " + convertedAmount + " " + supportedCurrencies.get(targetCurrencyCode));

            saveConversionToHistory(amount, sourceCurrencyCode, targetCurrencyCode, convertedAmount);
        } else {
            System.out.println("Invalid currency codes. Please try again.");
        }
    }

    private static void displaySupportedCurrencies() {
        System.out.println("Supported Currencies:");
        for (Map.Entry<String, String> entry : supportedCurrencies.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    private static void displayExchangeRates() {
        System.out.println("Exchange Rates:");
        for (Map.Entry<String, Double> entry : exchangeRates.entrySet()) {
            System.out.println(entry.getKey() + " to USD: " + entry.getValue());
        }
    }
    private static void addFavoriteCurrency() {
        System.out.print("Enter the currency code to add to favorites: ");
        String currencyCode = scanner.nextLine().toUpperCase();

        if (supportedCurrencies.containsKey(currencyCode)) {
            favoriteCurrencies.put(currencyCode, supportedCurrencies.get(currencyCode));
            System.out.println(currencyCode + " added to favorite currencies.");
        } else {
            System.out.println("Invalid currency code. Please try again.");
        }
    }

    private static void removeFavoriteCurrency() {
        System.out.print("Enter the currency code to remove from favorites: ");
        String currencyCode = scanner.nextLine().toUpperCase();

        if (favoriteCurrencies.containsKey(currencyCode)) {
            favoriteCurrencies.remove(currencyCode);
            System.out.println(currencyCode + " removed from favorite currencies.");
        } else {
        	 System.out.println("Currency code not found in favorites. Please try again.");
        }
    }

    private static void viewFavoriteCurrencies() {
        System.out.println("\nFavorite Currencies:");
        for (Map.Entry<String, String> entry : favoriteCurrencies.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
        

    private static void performMultipleCurrencyConversion() {
        System.out.print("Enter the number of conversions to perform: ");
        int num = scanner.nextInt();
        scanner.nextLine(); 

        for (int i = 0; i < num; i++) {
            System.out.println("\nConversion " + (i + 1) + ":");
            performCurrencyConversion();
        }
    }

    private static void saveConversionToHistory(double amount, String sourceCurrencyCode, String targetCurrencyCode, double convertedAmount) {
        System.out.println("Conversion Details:");
        System.out.println("Amount: " + amount + " " + supportedCurrencies.get(sourceCurrencyCode));
        System.out.println("Converted Amount: " + convertedAmount + " " + supportedCurrencies.get(targetCurrencyCode));
        System.out.println("Source Currency: " + supportedCurrencies.get(sourceCurrencyCode));
        System.out.println("Target Currency: " + supportedCurrencies.get(targetCurrencyCode));

        System.out.print("Do you want to save this conversion to history? (yes/no): ");
        String saveToHistoryAnswer = scanner.next().toLowerCase();

        if (saveToHistoryAnswer.equals("yes")) {
            String conversionKey = sourceCurrencyCode + "->" + targetCurrencyCode;
            ConversionRecord record = new ConversionRecord(amount, sourceCurrencyCode, targetCurrencyCode, convertedAmount);
            saveConversionRecord(conversionKey, record);
            System.out.println("Conversion saved to history.");
        } else {
            System.out.println("Conversion not saved to history.");
        }
    }

    private static void saveConversionRecord(String conversionKey, ConversionRecord record) {
        conversionHistory.put(conversionKey, record);
    }
      
    private static void saveHistoryToFile() {
        try (FileWriter writer = new FileWriter("conversion_history.csv")) {
        writer.write("Source Currency,Target Currency,Amount,Converted Amount,Date and Time\n");

        for (Map.Entry<String, ConversionRecord> entry : conversionHistory.entrySet()) {
        ConversionRecord record = entry.getValue();
         String csvLine = String.format("%s,%s,%.2f,%.2f,%s\n",
         record.getSourceCurrencyCode(), record.getTargetCurrencyCode(),
         record.getAmount(), record.getConvertedAmount(), record.getDateTime());
         writer.write(csvLine);
                        }

          System.out.println("Conversion history saved to file (conversion_history.csv).");
          } catch (IOException e) {
            System.out.println("Error saving conversion history to file: " + e.getMessage());
           }
        }

          private static void displaySavedHistory() {
          System.out.println("Saved Conversion History:");
          try (Scanner fileScanner = new Scanner("conversion_history.csv")) {
          while (fileScanner.hasNextLine()) {
          System.out.println(fileScanner.nextLine());
                       }
                   }
               }
   

                    public static class ConversionRecord {
                    private double amount;
                    private String sourceCurrencyCode;
                    private String targetCurrencyCode;
                    private double convertedAmount;
                    private LocalDateTime dateTime;

                    public ConversionRecord(double amount, String sourceCurrencyCode, String targetCurrencyCode, double convertedAmount) {
                        this.amount = amount;
                        this.sourceCurrencyCode = sourceCurrencyCode;
                        this.targetCurrencyCode = targetCurrencyCode;
                        this.convertedAmount = convertedAmount;
                        this.dateTime = LocalDateTime.now();
                    }

                    public double getAmount() {
                        return amount;
                    }

                    public String getSourceCurrencyCode() {
                        return sourceCurrencyCode;
                    }

                    public String getTargetCurrencyCode() {
                        return targetCurrencyCode;
                    }

                    public double getConvertedAmount() {
                        return convertedAmount;
                    }

                    public LocalDateTime getDateTime() {
                        return dateTime;
                    }
                }


					
            }
