package soft_uni.jsonprocessing.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import org.springframework.stereotype.Component;

import java.util.Set;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(nullable = false,length = 15)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products;

    public Category() {}

    public Category(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
