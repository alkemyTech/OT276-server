package com.alkemy.ong.core.model;

import java.util.Objects;

import javax.persistence.*;

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
@Table(name = "testimonial")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE testimonial SET is_active=false WHERE testimonial_id=?")
@EntityListeners(AuditListener.class)
public class Testimonial implements Auditable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "testimonial_id")
	Long id;
	
	@Column(nullable = false)
	String name;
	
	@Column
	String image;
	
	@Column
	String content;

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
		Testimonial other = (Testimonial) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
}