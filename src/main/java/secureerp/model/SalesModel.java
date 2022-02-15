package secureerp.model;

import java.time.LocalDate;

public class SalesModel {
    private String id;
    private String customerId;
    private String product;
    private float price;
    private LocalDate transactionDate;

    public String[] toTableRow() {
        return new String[]{
                id, customerId, product, String.valueOf(price), String.valueOf(transactionDate)
        };
    }

    public SalesModel(String id, String customerId, String product, float price, LocalDate transactionDate) {
        this.id = id;
        this.customerId = customerId;
        this.product = product;
        this.price = price;
        this.transactionDate = transactionDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}

