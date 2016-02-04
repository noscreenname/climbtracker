package a.m.a.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A GradeSystem.
 */
@Entity
@Table(name = "grade_system")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GradeSystem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;
    
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GradeSystem gradeSystem = (GradeSystem) o;
        if(gradeSystem.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, gradeSystem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GradeSystem{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
