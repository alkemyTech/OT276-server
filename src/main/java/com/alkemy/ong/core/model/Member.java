package com.alkemy.ong.core.model;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import com.alkemy.ong.core.model.audit.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "member")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE member SET is_active=false WHERE member_id=?")
@EntityListeners(AuditListener.class)
public class Member implements Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column (nullable = false)
    private String name;

    @Column
    private String facebookUrl;

    @Column
    private String instagramUrl;

    @Column
    private String linkedinUrl;

    @Column (nullable = false)
    private String image;

    @Column
    private String description;

    @Embedded
    private Audit audit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}