package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "logisttable", schema = "public", catalog = "hibernatetest")
public class LogisttableEntity {
    private int id;
    private String name;
    private String secondname;
    private String email;
    private Integer phone;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "secondname", nullable = true, length = 100)
    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone", nullable = true)
    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogisttableEntity that = (LogisttableEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(secondname, that.secondname) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, secondname, email, phone);
    }
}
