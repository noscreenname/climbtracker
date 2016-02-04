package a.m.a.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Crag.
 */
@Entity
@Table(name = "crag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Crag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @Pattern(regexp = "/^(-?\\d{1,2}\\.\\d{6}),(-?\\d{1,2}\\.\\d{6})$/")
    @Column(name = "location")
    private String location;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "description")
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "default_grade_system_id")
    private GradeSystem defaultGradeSystem;

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

    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public GradeSystem getDefaultGradeSystem() {
        return defaultGradeSystem;
    }

    public void setDefaultGradeSystem(GradeSystem gradeSystem) {
        this.defaultGradeSystem = gradeSystem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crag crag = (Crag) o;
        if(crag.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, crag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Crag{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", location='" + location + "'" +
            ", address='" + address + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
