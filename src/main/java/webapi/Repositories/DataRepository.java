package webapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import webapi.Domain.Data;

public interface DataRepository extends JpaRepository<Data, Long>
{
}
