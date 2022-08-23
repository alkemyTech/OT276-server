package com.alkemy.ong.core.model;

import java.util.Objects;

import javax.persistence.*;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.alkemy.ong.core.model.audit.Audit;
import com.alkemy.ong.core.model.audit.AuditListener;
import com.alkemy.ong.core.model.audit.Auditable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "comment")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE comment SET is_active=false WHERE comment_id=?")
@EntityListeners(AuditListener.class)
public class Comment implements Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@ToString.Exclude
	private User userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "new_id")
	@ToString.Exclude
	private New newId;
	
	@Column(nullable = false, columnDefinition = "text")
	private String body;
	
	@Embedded
	private Audit audit;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
}
