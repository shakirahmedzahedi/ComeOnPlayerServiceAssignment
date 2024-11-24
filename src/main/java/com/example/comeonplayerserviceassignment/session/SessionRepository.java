package com.example.comeonplayerserviceassignment.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, String>
{
    @Query("SELECT s FROM SessionEntity s WHERE s.player.id = :playerId AND s.active = true")
    Optional<SessionEntity> findActiveSessionByPlayerId(@Param("playerId") Long playerId);
}
