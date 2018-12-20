package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "grouptable", schema = "public", catalog = "hibernatetest")
public class GrouptableEntity {
    private int id;
    private String name;
    private int logistable_id;


    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "logist_id")
    public int getLogistable_id() {
        return logistable_id;
    }

    public void setLogistable_id(int logistable_id) {
        this.logistable_id = logistable_id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrouptableEntity that = (GrouptableEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
