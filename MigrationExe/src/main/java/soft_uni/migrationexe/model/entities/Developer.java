package soft_uni.migrationexe.model.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "developers")
public class Developer extends BaseEntity {


    @Column(name = "full_name",nullable = false,length = 100)
    private String fullName;

    @Column(length = 100,nullable = false)
    private String email;

    @Column(name = "github_username",nullable = false,length = 50)
    private String gitHubUsername;

    @Column(name = "github_profile_url",length = 200,nullable = false)
    private String githubProfileUrl;

    @Column(name = "is_active",nullable = false)
    private boolean isActive;


    @ManyToMany(mappedBy = "developers")
    private Set<Technology> technologies;

    public Developer() {
        super();
        this.isActive = true;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGitHubUsername() {
        return gitHubUsername;
    }

    public void setGitHubUsername(String gitHubUsername) {
        this.gitHubUsername = gitHubUsername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGithubProfileUrl() {
        return githubProfileUrl;
    }

    public void setGithubProfileUrl(String githubProfileUrl) {
        this.githubProfileUrl = githubProfileUrl;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
