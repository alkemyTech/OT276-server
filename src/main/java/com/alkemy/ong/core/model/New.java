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

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "new")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE new SET is_active=false WHERE new_id=?")
@EntityListeners(AuditListener.class)
public class New implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "new_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Embedded
    private Audit audit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        New neww = (New) o;
        return Objects.equals(id, neww.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
