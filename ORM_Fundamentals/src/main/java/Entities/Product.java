package Entities;

import orm.Annotations.Column;
import orm.Annotations.Entity;
import orm.Annotations.Id;

@Entity(name="products")
public class Product {
    @Id
    @Column(name="id")
    private int id;

    @Column(name="label")
    private String label;

    @Column(name="price")
    private double price;


    public Product(String label, double price) {
        this.label = label;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
