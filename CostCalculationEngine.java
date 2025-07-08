package Professional_Cost_Calculator;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * ItemCost class for comprehensive cost calculation with professional features
 * 
 * Features:
 * - Professional cost calculation with tax and discount support
 * - Input validation and error handling
 * - Currency formatting for different locales
 * - Multiple calculation methods for flexibility
 * - Detailed cost breakdown and reporting
 * 
 * @author Muhammad Yamman Hammad
 * @version 2.0
 */
public class CostCalculationEngine {
    // Private fields for encapsulation
    private String itemName;
    private double costPerItem;
    private int quantity;
    private double deliveryCost;
    private double taxRate;
    private double discountRate;
    private double totalCost;
    private double subtotal;
    private double taxAmount;
    private double discountAmount;
    
    // Constants for validation and defaults
    private static final double DEFAULT_TAX_RATE = 0.08; // 8% default tax
    private static final double MIN_COST = 0.01;
    private static final int MIN_QUANTITY = 1;
    private static final double MAX_DISCOUNT_RATE = 0.5; // Maximum 50% discount
    
    // Currency formatter for professional display
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(new Locale("en", "PK"));
    
    /**
     * Default constructor with initialization
     */
    public CostCalculationEngine() {
        this.itemName = "";
        this.costPerItem = 0.0;
        this.quantity = 0;
        this.deliveryCost = 0.0;
        this.taxRate = DEFAULT_TAX_RATE;
        this.discountRate = 0.0;
        this.totalCost = 0.0;
        this.subtotal = 0.0;
        this.taxAmount = 0.0;
        this.discountAmount = 0.0;
    }
    
    /**
     * Parameterized constructor for complete item setup
     * 
     * @param itemName Name of the item
     * @param costPerItem Cost per individual item
     * @param quantity Number of items
     * @param deliveryCost Delivery charges
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public CostCalculationEngine(String itemName, double costPerItem, int quantity, double deliveryCost) {
        this();
        setItemDetails(itemName, costPerItem, quantity, deliveryCost);
    }
    
    /**
     * Sets item details with comprehensive validation
     * 
     * @param itemName Name of the item
     * @param costPerItem Cost per individual item
     * @param quantity Number of items
     * @param deliveryCost Delivery charges
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public void setItemDetails(String itemName, double costPerItem, int quantity, double deliveryCost) {
        validateAndSetItemName(itemName);
        validateAndSetCostPerItem(costPerItem);
        validateAndSetQuantity(quantity);
        validateAndSetDeliveryCost(deliveryCost);
    }
    
    /**
     * Basic cost calculation without delivery
     * 
     * @param costPerItem Cost per individual item
     * @param quantity Number of items
     * @throws IllegalArgumentException if parameters are invalid
     */
    public void calculateCost(double costPerItem, int quantity) {
        validateAndSetCostPerItem(costPerItem);
        validateAndSetQuantity(quantity);
        this.deliveryCost = 0.0;
        performCalculation();
    }
    
    /**
     * Cost calculation with delivery charges
     * 
     * @param costPerItem Cost per individual item
     * @param quantity Number of items
     * @param deliveryCost Delivery charges
     * @throws IllegalArgumentException if parameters are invalid
     */
    public void calculateCost(double costPerItem, int quantity, double deliveryCost) {
        validateAndSetCostPerItem(costPerItem);
        validateAndSetQuantity(quantity);
        validateAndSetDeliveryCost(deliveryCost);
        performCalculation();
    }
    
    /**
     * Advanced cost calculation with tax and discount
     * 
     * @param costPerItem Cost per individual item
     * @param quantity Number of items
     * @param deliveryCost Delivery charges
     * @param taxRate Tax rate (as decimal, e.g., 0.08 for 8%)
     * @param discountRate Discount rate (as decimal, e.g., 0.1 for 10%)
     * @throws IllegalArgumentException if parameters are invalid
     */
    public void calculateAdvancedCost(double costPerItem, int quantity, double deliveryCost, 
                                    double taxRate, double discountRate) {
        validateAndSetCostPerItem(costPerItem);
        validateAndSetQuantity(quantity);
        validateAndSetDeliveryCost(deliveryCost);
        validateAndSetTaxRate(taxRate);
        validateAndSetDiscountRate(discountRate);
        performCalculation();
    }
    
    /**
     * Performs the actual cost calculation with all factors
     */
    private void performCalculation() {
        // Calculate subtotal (items + delivery)
        this.subtotal = (this.costPerItem * this.quantity) + this.deliveryCost;
        
        // Apply discount if any
        this.discountAmount = this.subtotal * this.discountRate;
        double afterDiscount = this.subtotal - this.discountAmount;
        
        // Calculate tax on discounted amount
        this.taxAmount = afterDiscount * this.taxRate;
        
        // Calculate final total
        this.totalCost = afterDiscount + this.taxAmount;
    }
    
    /**
     * Generates a detailed cost breakdown report
     * 
     * @return Formatted string with complete cost analysis
     */
    public String generateDetailedReport() {
        StringBuilder report = new StringBuilder();
        report.append("\n").append("=".repeat(50)).append("\n");
        report.append("               DETAILED COST BREAKDOWN\n");
        report.append("=".repeat(50)).append("\n");
        
        if (!itemName.isEmpty()) {
            report.append(String.format("Item Name           : %s%n", itemName));
        }
        report.append(String.format("Cost Per Item       : %s%n", CURRENCY_FORMAT.format(costPerItem)));
        report.append(String.format("Quantity            : %d items%n", quantity));
        report.append(String.format("Items Subtotal      : %s%n", CURRENCY_FORMAT.format(costPerItem * quantity)));
        
        if (deliveryCost > 0) {
            report.append(String.format("Delivery Charges    : %s%n", CURRENCY_FORMAT.format(deliveryCost)));
        }
        
        report.append(String.format("Subtotal            : %s%n", CURRENCY_FORMAT.format(subtotal)));
        
        if (discountRate > 0) {
            report.append(String.format("Discount (%.1f%%)     : -%s%n", 
                                      discountRate * 100, CURRENCY_FORMAT.format(discountAmount)));
        }
        
        if (taxRate > 0) {
            report.append(String.format("Tax (%.1f%%)          : %s%n", 
                                      taxRate * 100, CURRENCY_FORMAT.format(taxAmount)));
        }
        
        report.append("-".repeat(50)).append("\n");
        report.append(String.format("TOTAL COST          : %s%n", CURRENCY_FORMAT.format(totalCost)));
        report.append("=".repeat(50)).append("\n");
        
        return report.toString();
    }
    
    /**
     * Generates a simple cost summary
     * 
     * @return Formatted string with basic cost information
     */
    public String generateSummary() {
        return String.format("Total Cost: %s (Items: %d × %s + Delivery: %s)", 
                           CURRENCY_FORMAT.format(totalCost),
                           quantity,
                           CURRENCY_FORMAT.format(costPerItem),
                           CURRENCY_FORMAT.format(deliveryCost));
    }
    
    // Validation methods
    private void validateAndSetItemName(String itemName) {
        if (itemName != null && !itemName.trim().isEmpty()) {
            this.itemName = itemName.trim();
        } else {
            this.itemName = "Item";
        }
    }
    
    private void validateAndSetCostPerItem(double costPerItem) {
        if (costPerItem < MIN_COST) {
            throw new IllegalArgumentException(
                String.format("Cost per item must be at least %s", CURRENCY_FORMAT.format(MIN_COST)));
        }
        this.costPerItem = costPerItem;
    }
    
    private void validateAndSetQuantity(int quantity) {
        if (quantity < MIN_QUANTITY) {
            throw new IllegalArgumentException(
                String.format("Quantity must be at least %d", MIN_QUANTITY));
        }
        this.quantity = quantity;
    }
    
    private void validateAndSetDeliveryCost(double deliveryCost) {
        if (deliveryCost < 0) {
            throw new IllegalArgumentException("Delivery cost cannot be negative");
        }
        this.deliveryCost = deliveryCost;
    }
    
    private void validateAndSetTaxRate(double taxRate) {
        if (taxRate < 0 || taxRate > 1) {
            throw new IllegalArgumentException("Tax rate must be between 0 and 1 (0% to 100%)");
        }
        this.taxRate = taxRate;
    }
    
    private void validateAndSetDiscountRate(double discountRate) {
        if (discountRate < 0 || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException(
                String.format("Discount rate must be between 0 and %.0f%%", MAX_DISCOUNT_RATE * 100));
        }
        this.discountRate = discountRate;
    }
    
    // Getter methods for accessing calculated values
    public double getTotalCost() { return totalCost; }
    public double getSubtotal() { return subtotal; }
    public double getTaxAmount() { return taxAmount; }
    public double getDiscountAmount() { return discountAmount; }
    public String getItemName() { return itemName; }
    public double getCostPerItem() { return costPerItem; }
    public int getQuantity() { return quantity; }
    public double getDeliveryCost() { return deliveryCost; }
    public double getTaxRate() { return taxRate; }
    public double getDiscountRate() { return discountRate; }
    
    // Setter methods for tax and discount rates
    public void setTaxRate(double taxRate) {
        validateAndSetTaxRate(taxRate);
    }
    
    public void setDiscountRate(double discountRate) {
        validateAndSetDiscountRate(discountRate);
    }
    
    /**
     * Applies a percentage discount to the current calculation
     * 
     * @param discountPercentage Discount percentage (e.g., 10 for 10%)
     */
    public void applyDiscount(double discountPercentage) {
        setDiscountRate(discountPercentage / 100.0);
        performCalculation();
    }
    
    /**
     * Applies a tax rate to the current calculation
     * 
     * @param taxPercentage Tax percentage (e.g., 8 for 8%)
     */
    public void applyTax(double taxPercentage) {
        setTaxRate(taxPercentage / 100.0);
        performCalculation();
    }
}