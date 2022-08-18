package com.alkemy.ong.core.model;

import com.alkemy.ong.core.model.audit.Audit;
import com.alkemy.ong.core.model.audit.AuditListener;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="organization")
@Where(clause = "is_active=true")
@SQLDelete(sql ="UPDATE organization SET is_active=false WHERE organization_id=? ")
@EntityListeners(AuditListener.class)
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="organization_id")
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="image", nullable = false)
    private String image;

    @Column(name ="address", nullable = true)
    private String address;

    @Column(name="phone", nullable = true )
    private int phone;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name="welcomeText", nullable = false)
    private String welcomeText;

    @Column(name="aboutUsTExt", nullable = true)
    private String aboutUs;

    @Column(name="is_active", nullable = false)
    private boolean isActive;

    @Column(name="created_at", nullable = false)
    private LocalDate createAt;

    @Column(name="updated_at", nullable = true)
    private LocalDate updatedAt;

    @Embedded
    private Audit audit;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization organization = (Organization) o;
        return Objects.equals(id, organization.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }




}
