package soft_uni.migrationexe.model.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import soft_uni.migrationexe.model.enums.ProjectStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
public class Project extends BaseEntity {



    @Column(length = 100,nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(name = "max_team_size")
    private int maxTeamSize;

    @Column(name = "is_active",nullable = false)
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("NOT_STARTED")
    @Column(nullable = false)
    private ProjectStatus status = ProjectStatus.NOT_STARTED;

    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;

    public Project() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public int getMaxTeamSize() {
        return maxTeamSize;
    }

    public void setMaxTeamSize(int maxTeamSize) {
        this.maxTeamSize = maxTeamSize;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
