package webapi.Database;

import org.springframework.data.jpa.repository.JpaRepository;
import webapi.Data;

public interface DataRepository extends JpaRepository<Data, Long>
{
}
