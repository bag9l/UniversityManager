package com.botscrew.universitymanager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "`department`")
@Entity
public class Department {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "`name`")
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Lector head;

    @ManyToMany(mappedBy = "departments", fetch = FetchType.LAZY)
    private Set<Lector> lectors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return id.equals(that.id)
                && name.equals(that.name)
                && Objects.equals(lectors, that.lectors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lectors);
    }
}
