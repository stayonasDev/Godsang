package godsang.backend.repository;

import godsang.backend.entity.IntakeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntakeHistoryRepository extends JpaRepository<IntakeHistory, Long> {
}
