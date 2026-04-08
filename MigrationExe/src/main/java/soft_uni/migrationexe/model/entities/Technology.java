package soft_uni.migrationexe.model.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "technologies")
public class Technology extends BaseEntity {
    @Column(length = 100,nullable = false,unique = true)
    private String name;

    @Column(length = 50)
    private String category;



    @ManyToMany
    @JoinTable(
            name = "developers_technologies",
            joinColumns = @JoinColumn(name = "technology_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id")
    )
    private Set<Developer> developers;

    public Technology() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
