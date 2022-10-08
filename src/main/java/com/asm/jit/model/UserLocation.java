package com.asm.jit.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "user_location")
@Data
@ToString
public class UserLocation implements Serializable {

    private static final long serialVersionUID = 7259433852911762040L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "longitude", precision = 21, scale = 8)
    private BigDecimal longitude;

    @Column(name = "latitude", precision = 21, scale = 8)
    private BigDecimal latitude;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private Instant createdOn = Instant.now();

    @UpdateTimestamp
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate = Instant.now();
}
