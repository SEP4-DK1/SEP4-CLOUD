package webapi.Database;

import org.springframework.data.jpa.repository.JpaRepository;
import webapi.Target;

public interface TargetRepository extends JpaRepository<Target, Long>
{
}
