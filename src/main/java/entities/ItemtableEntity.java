package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "itemtable", schema = "public", catalog = "hibernatetest")
public class ItemtableEntity {
    private int id;
    private int code;
    private String name;
    private Object logist;
    private Integer needed;
    private Integer central;
    private Integer vystavka;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code", nullable = false)
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "logist", nullable = true)
    public Object getLogist() {
        return logist;
    }

    public void setLogist(Object logist) {
        this.logist = logist;
    }

    @Basic
    @Column(name = "needed", nullable = true)
    public Integer getNeeded() {
        return needed;
    }

    public void setNeeded(Integer needed) {
        this.needed = needed;
    }

    @Basic
    @Column(name = "central", nullable = true)
    public Integer getCentral() {
        return central;
    }

    public void setCentral(Integer central) {
        this.central = central;
    }

    @Basic
    @Column(name = "vystavka", nullable = true)
    public Integer getVystavka() {
        return vystavka;
    }

    public void setVystavka(Integer vystavka) {
        this.vystavka = vystavka;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemtableEntity that = (ItemtableEntity) o;
        return id == that.id &&
                code == that.code &&
                Objects.equals(name, that.name) &&
                Objects.equals(logist, that.logist) &&
                Objects.equals(needed, that.needed) &&
                Objects.equals(central, that.central) &&
                Objects.equals(vystavka, that.vystavka);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, logist, needed, central, vystavka);
    }
}
