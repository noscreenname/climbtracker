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
 * A Route.
 */
@Entity
@Table(name = "route")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Route implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "open_date")
    private LocalDate openDate;
    
    @Column(name = "is_open")
    private Boolean isOpen;
    
    @Size(max = 500)
    @Column(name = "description", length = 500)
    private String description;
    
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "route_route_types",
               joinColumns = @JoinColumn(name="routes_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="route_typess_id", referencedColumnName="ID"))
    private Set<RouteType> routeTypess = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "crag_id")
    private Crag crag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }
    
    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }
    
    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public Set<RouteType> getRouteTypess() {
        return routeTypess;
    }

    public void setRouteTypess(Set<RouteType> routeTypes) {
        this.routeTypess = routeTypes;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Crag getCrag() {
        return crag;
    }

    public void setCrag(Crag crag) {
        this.crag = crag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Route route = (Route) o;
        if(route.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, route.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Route{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", openDate='" + openDate + "'" +
            ", isOpen='" + isOpen + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
