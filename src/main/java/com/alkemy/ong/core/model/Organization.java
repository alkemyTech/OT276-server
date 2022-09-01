package com.alkemy.ong.core.model;

import com.alkemy.ong.core.model.audit.Audit;
import com.alkemy.ong.core.model.audit.AuditListener;
import com.alkemy.ong.core.model.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "organization")
@Where(clause = "is_active=true")
@SQLDelete(sql ="UPDATE organization SET is_active=false WHERE organization_id=? ")
@EntityListeners(AuditListener.class)
public class Organization implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="organization_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column
    private String address;

    @Column
    private int phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name="welcome_text", columnDefinition = "text")
    private String welcomeText;

    @Column(name = "about_us_text", columnDefinition = "text")
    private String aboutUsText;

    @Column(nullable = false, name="contact_text", columnDefinition = "text")
    private String contactText;

    @Column(name="facebook")
    private String facebookUrl;

    @Column(name="linkedin")
    private String linkedinUrl;

    @Column(name="instagram")
    private String instagramUrl;

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
