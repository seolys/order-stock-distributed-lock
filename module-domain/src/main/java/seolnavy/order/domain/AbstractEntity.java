package seolnavy.order.domain;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

	@CreationTimestamp
	@Column(name = "CREATED_AT", nullable = false)
	private ZonedDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "UPDATED_AT", nullable = false)
	private ZonedDateTime updatedAt;
}
