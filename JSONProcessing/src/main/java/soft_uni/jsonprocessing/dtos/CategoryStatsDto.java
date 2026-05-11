package soft_uni.jsonprocessing.dtos;

import java.math.BigDecimal;

public class CategoryStatsDto {
    private String name;
    private long productsCount;
    private double avbPrice;
    private BigDecimal revenue;

    public CategoryStatsDto(String name, long productsCount, double avbPrice, BigDecimal revenue) {
        this.name = name;
        this.productsCount = productsCount;
        this.avbPrice = avbPrice;
        this.revenue = revenue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(long productsCount) {
        this.productsCount = productsCount;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public double getAvbPrice() {
        return avbPrice;
    }

    public void setAvbPrice(double avbPrice) {
        this.avbPrice = avbPrice;
    }
}
