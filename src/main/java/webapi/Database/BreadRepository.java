package webapi.Database;

import org.springframework.data.jpa.repository.JpaRepository;
import webapi.BreadProfile;

public interface BreadRepository extends JpaRepository<BreadProfile, Long>
{
}
