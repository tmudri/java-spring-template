package de.stuttgart.tmudri.templates.java.spring.adapters.output.storage.db.jpa.repository;

import de.stuttgart.tmudri.templates.java.spring.adapters.output.storage.db.jpa.entity.StatisticType;
import de.stuttgart.tmudri.templates.java.spring.adapters.output.storage.db.jpa.entity.StatusProviderStatisticsEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaStatusStatisticsStorageRepository
    extends JpaRepository<StatusProviderStatisticsEntity, String> {

  Optional<StatusProviderStatisticsEntity> findByType(StatisticType type);
}
