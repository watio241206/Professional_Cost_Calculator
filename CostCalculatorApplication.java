package Professional_Cost_Calculator;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Professional Cost Calculator Application
 * 
 * A professional cost calculation system with advanced features including:
 * - Input validation and error handling
 * - Tax and discount calculations
 * - Professional formatting and reporting
 * - Interactive user interface with menu system
 * - Multiple calculation modes
 * 
 * @author Muhammad Yamman Hammad
 * @version 2.0
 */
public class CostCalculatorApplication {
    private static Scanner input = new Scanner(System.in);
    private static final String APP_TITLE = "ADVANCED COST CALCULATOR";
    private static final String VERSION = "v2.0";
    
    public static void main(String[] args) {
        displayWelcome();
        
        boolean continueCalculating = true;
        while (continueCalculating) {
            try {
                performCostCalculation();
                continueCalculating = askToContinue();
            } catch (Exception e) {
                System.err.println("❌ Error: " + e.getMessage());
                System.out.println("Please try again with valid inputs.\n");
            }
        }
        
        displayGoodbye();
        input.close();
    }
    
    /**
     * Displays welcome message and application information
     */
    private static void displayWelcome() {
        System.out.println("=".repeat(60));
        System.out.println("              " + APP_TITLE + " " + VERSION);
        System.out.println("=".repeat(60));
        System.out.println("🛒 Professional Cost Calculation System");
        System.out.println("✨ Features: Tax Calculation | Discounts | Detailed Reports");
        System.out.println("=".repeat(60));
        System.out.println();
    }
    
    /**
     * Main cost calculation workflow
     */
    private static void performCostCalculation() {
        // Display calculation mode options
        int calculationMode = getCalculationMode();
        
        // Get basic item information
        String itemName = getItemName();
        double costPerItem = getCostPerItem();
        int quantity = getQuantity();
        double deliveryCost = getDeliveryCost();
        
        // Create ItemCost object
        CostCalculationEngine calculator = new CostCalculationEngine();
        
        try {
            switch (calculationMode) {
                case 1:
                    performBasicCalculation(calculator, itemName, costPerItem, quantity, deliveryCost);
                    break;
                case 2:
                    performAdvancedCalculation(calculator, itemName, costPerItem, quantity, deliveryCost);
                    break;
                case 3:
                    performCustomCalculation(calculator, itemName, costPerItem, quantity, deliveryCost);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid calculation mode selected");
            }
            
            // Display results
            displayResults(calculator);
            
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Validation Error: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Gets calculation mode from user
     */
    private static int getCalculationMode() {
        System.out.println("📊 Select Calculation Mode:");
        System.out.println("   1. Basic Calculation (Items + Delivery)");
        System.out.println("   2. Standard Calculation (with 8% Tax)");
        System.out.println("   3. Advanced Calculation (Custom Tax & Discount)");
        System.out.println();
        
        int mode = getValidatedIntInput("Choose mode (1-3): ", 1, 3);
        System.out.println();
        return mode;
    }
    
    /**
     * Performs basic calculation without tax
     */
    private static void performBasicCalculation(CostCalculationEngine calculator, String itemName, 
                                              double costPerItem, int quantity, double deliveryCost) {
        calculator.setItemDetails(itemName, costPerItem, quantity, deliveryCost);
        
        if (deliveryCost == 0) {
            calculator.calculateCost(costPerItem, quantity);
        } else {
            calculator.calculateCost(costPerItem, quantity, deliveryCost);
        }
        
        System.out.println("💡 Basic calculation completed (no tax applied)");
    }
    
    /**
     * Performs standard calculation with default 8% tax
     */
    private static void performAdvancedCalculation(CostCalculationEngine calculator, String itemName,
                                                 double costPerItem, int quantity, double deliveryCost) {
        calculator.setItemDetails(itemName, costPerItem, quantity, deliveryCost);
        calculator.calculateAdvancedCost(costPerItem, quantity, deliveryCost, 0.08, 0.0);
        
        System.out.println("💡 Standard calculation completed (8% tax applied)");
    }
    
    /**
     * Performs custom calculation with user-defined tax and discount
     */
    private static void performCustomCalculation(CostCalculationEngine calculator, String itemName,
                                               double costPerItem, int quantity, double deliveryCost) {
        // Get custom tax rate
        double taxRate = getValidatedDoubleInput("Enter tax rate (0-25%): ", 0.0, 25.0) / 100.0;
        
        // Get custom discount rate
        double discountRate = getValidatedDoubleInput("Enter discount rate (0-50%): ", 0.0, 50.0) / 100.0;
        
        calculator.setItemDetails(itemName, costPerItem, quantity, deliveryCost);
        calculator.calculateAdvancedCost(costPerItem, quantity, deliveryCost, taxRate, discountRate);
        
        System.out.printf("💡 Custom calculation completed (%.1f%% tax, %.1f%% discount applied)%n", 
                         taxRate * 100, discountRate * 100);
    }
    
    /**
     * Gets item name from user
     */
    private static String getItemName() {
        System.out.print("🏷️  Item Name: ");
        String name = input.nextLine();
        return name.trim().isEmpty() ? "Item" : name.trim();
    }
    
    /**
     * Gets cost per item with validation
     */
    private static double getCostPerItem() {
        return getValidatedDoubleInput("💰 Cost Per Item (Rs.): ", 0.01, Double.MAX_VALUE);
    }
    
    /**
     * Gets quantity with validation
     */
    private static int getQuantity() {
        return getValidatedIntInput("📦 Quantity: ", 1, Integer.MAX_VALUE);
    }
    
    /**
     * Gets delivery cost with validation
     */
    private static double getDeliveryCost() {
        return getValidatedDoubleInput("🚚 Delivery Cost (Rs., 0 for free): ", 0.0, Double.MAX_VALUE);
    }
    
    /**
     * Validates and gets integer input within range
     */
    private static int getValidatedIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = input.nextInt();
                input.nextLine(); // Clear buffer
                
                if (value < min || value > max) {
                    System.out.printf("❌ Please enter a value between %d and %d.%n", min, max);
                    continue;
                }
                return value;
            } catch (InputMismatchException e) {
                System.out.println("❌ Please enter a valid integer number.");
                input.nextLine(); // Clear invalid input
            }
        }
    }
    
    /**
     * Validates and gets double input within range
     */
    private static double getValidatedDoubleInput(String prompt, double min, double max) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = input.nextDouble();
                input.nextLine(); // Clear buffer
                
                if (value < min || value > max) {
                    System.out.printf("❌ Please enter a value between %.2f and %.2f.%n", min, max);
                    continue;
                }
                return value;
            } catch (InputMismatchException e) {
                System.out.println("❌ Please enter a valid decimal number.");
                input.nextLine(); // Clear invalid input
            }
        }
    }
    
    /**
     * Displays calculation results
     */
    private static void displayResults(CostCalculationEngine calculator) {
        System.out.println();
        System.out.println("✅ Calculation completed successfully!");
        
        // Display detailed report
        System.out.println(calculator.generateDetailedReport());
        
        // Display quick summary
        System.out.println("📋 Quick Summary:");
        System.out.println("   " + calculator.generateSummary());
        System.out.println();
    }
    
    /**
     * Asks user if they want to continue with another calculation
     */
    private static boolean askToContinue() {
        System.out.print("🔄 Would you like to perform another calculation? (y/n): ");
        String response = input.nextLine().trim().toLowerCase();
        
        if (response.startsWith("y")) {
            System.out.println();
            return true;
        } else if (response.startsWith("n")) {
            return false;
        } else {
            System.out.println("Please enter 'y' for yes or 'n' for no.");
            return askToContinue();
        }
    }
    
    /**
     * Displays goodbye message
     */
    private static void displayGoodbye() {
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("           Thank you for using " + APP_TITLE + "!");
        System.out.println("=".repeat(60));
        System.out.println("💝 Have a great day!");
        System.out.println("🔧 Developed by Muhammad Yamman Hammad");
        System.out.println();
    }
}