package com.botscrew.universitymanager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "`lector`")
@Entity
public class Lector {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "`firstname`", nullable = false)
    private String firstname;

    @Column(name = "`lastname`", nullable = false)
    private String lastname;

    @Enumerated(EnumType.STRING)
    @Column(name = "`degree`", nullable = false, unique = false)
    private Degree degree;

    @Column(name = "`salary`")
    private BigDecimal salary;

    @Column(name = "`date_of_birth`", nullable = false)
    private LocalDate dateOfBirth;

    @Transient
    private Integer age;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinTable(name = "lector_department",
            joinColumns = {
                    @JoinColumn(name = "lector_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "department_id", referencedColumnName = "id")
            }
    )
//    @JsonManagedReference
    private Set<Department> departments;

    public Integer getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lector)) return false;
        Lector lector = (Lector) o;
        return id.equals(lector.id)
                && firstname.equals(lector.firstname)
                && lastname.equals(lector.lastname)
                && degree == lector.degree
                && Objects.equals(salary, lector.salary)
                && dateOfBirth.equals(lector.dateOfBirth)
                && Objects.equals(age, lector.age)
                && Objects.equals(departments, lector.departments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                firstname,
                lastname,
                degree,
                salary,
                dateOfBirth,
                age,
                departments
        );
    }
}
