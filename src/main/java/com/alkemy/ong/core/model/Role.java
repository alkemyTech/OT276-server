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
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "role")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE role SET is_active=false WHERE role_id=?")
@EntityListeners(AuditListener.class)
public class Role implements Auditable, GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private String name;

    @Column
    private String description;

    @Embedded
    private Audit audit;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String getAuthority() {
        return this.name;
    }
}
