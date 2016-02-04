package a.m.a.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CragType.
 */
@Entity
@Table(name = "crag_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CragType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "indoor")
    private Boolean indoor;
    
    @Column(name = "outdoor")
    private Boolean outdoor;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIndoor() {
        return indoor;
    }
    
    public void setIndoor(Boolean indoor) {
        this.indoor = indoor;
    }

    public Boolean getOutdoor() {
        return outdoor;
    }
    
    public void setOutdoor(Boolean outdoor) {
        this.outdoor = outdoor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CragType cragType = (CragType) o;
        if(cragType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cragType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CragType{" +
            "id=" + id +
            ", indoor='" + indoor + "'" +
            ", outdoor='" + outdoor + "'" +
            '}';
    }
}
