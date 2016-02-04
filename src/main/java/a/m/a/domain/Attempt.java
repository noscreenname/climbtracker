package a.m.a.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Attempt.
 */
@Entity
@Table(name = "attempt")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Attempt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;
    
    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;
    
    @NotNull
    @Min(value = 0)
    @Column(name = "nb_fail", nullable = false)
    private Integer nbFail;
    
    @NotNull
    @Min(value = 0)
    @Column(name = "nb_success", nullable = false)
    private Integer nbSuccess;
    
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNbFail() {
        return nbFail;
    }
    
    public void setNbFail(Integer nbFail) {
        this.nbFail = nbFail;
    }

    public Integer getNbSuccess() {
        return nbSuccess;
    }
    
    public void setNbSuccess(Integer nbSuccess) {
        this.nbSuccess = nbSuccess;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attempt attempt = (Attempt) o;
        if(attempt.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, attempt.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Attempt{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", description='" + description + "'" +
            ", nbFail='" + nbFail + "'" +
            ", nbSuccess='" + nbSuccess + "'" +
            '}';
    }
}
