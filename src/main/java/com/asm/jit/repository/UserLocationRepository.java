package com.asm.jit.repository;

import com.asm.jit.model.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {

    Optional<UserLocation> findByUserId(String userId);

    boolean existsByLongitudeAndLatitude(BigDecimal longitude, BigDecimal latitude);

    @Query("select u from UserLocation u where u.createdOn = (select max(createdOn) from UserLocation where userId = :userId)")
    Optional<UserLocation> findLatestByUserId(@Param("userId") String userId);

    @Query("select u from UserLocation u where createdOn between :from and :to")
    List<UserLocation> findByDateAndTimeBetween(@Param("from") Instant from, @Param("to") Instant to);
}
