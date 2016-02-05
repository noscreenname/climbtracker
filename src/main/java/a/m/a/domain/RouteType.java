package a.m.a.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A RouteType.
 */
@Entity
@Table(name = "route_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RouteType implements Serializable {

    public static final int MAX_TYPE_LENGTH = 20;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = MAX_TYPE_LENGTH)
    @Column(name = "type", length = MAX_TYPE_LENGTH, nullable = false)
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RouteType routeType = (RouteType) o;
        if (routeType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, routeType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RouteType{" +
            "id=" + id +
            ", type='" + type + "'" +
            '}';
    }
}
