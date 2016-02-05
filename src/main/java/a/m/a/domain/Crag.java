package a.m.a.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Crag.
 */
@Entity
@Table(name = "crag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Crag implements Serializable {

    public static final int MAX_ADDRESS_LENGTH = 100;
    public static final int MAX_DESCRIPTION_LENGTH = 500;
    public static final int MAX_NAME_LENGTH = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = MAX_NAME_LENGTH)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Pattern(regexp = "/^(-?\\d{1,2}\\.\\d{6}),(-?\\d{1,2}\\.\\d{6})$/")
    @Column(name = "location")
    private String location;

    @Size(max = MAX_ADDRESS_LENGTH)
    @Column(name = "address", length = MAX_ADDRESS_LENGTH)
    private String address;

    @Size(max = MAX_DESCRIPTION_LENGTH)
    @Column(name = "description", length = MAX_DESCRIPTION_LENGTH)
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
        if (crag.id == null || id == null) {
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
