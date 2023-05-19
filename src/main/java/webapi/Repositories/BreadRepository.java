package webapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import webapi.Domain.BreadProfile;

public interface BreadRepository extends JpaRepository<BreadProfile, Long>
{
}
