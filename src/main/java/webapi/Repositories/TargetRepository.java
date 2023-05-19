package webapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import webapi.Domain.Target;

public interface TargetRepository extends JpaRepository<Target, Long>
{
}
