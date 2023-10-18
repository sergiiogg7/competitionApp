package backend.projects.competitionApp.repository;

import backend.projects.competitionApp.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
