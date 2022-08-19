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
@Table(name = "slide")
@Where(clause = "is_active=true")
@SQLDelete(sql ="UPDATE slide SET is_active=false WHERE slide_id=? ")
@EntityListeners(AuditListener.class)
public class Slide implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="slide_id")
    private long id;

    @Column(name="image_url", nullable = false)
    private String imageUrl;

    @Column(columnDefinition = "text", nullable = false)
    private String text;

    @Column(name="o_rder", nullable = false)
    private Integer order;

    @ManyToOne
    @JoinColumn(name="organization_id")
    private Organization organization;

    @Embedded
    private Audit audit;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slide slide = (Slide) o;
        return Objects.equals(id, slide.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
