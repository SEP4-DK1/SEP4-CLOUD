package webapi.Database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import webapi.Data;

public interface DataRepository extends JpaRepository<Data, Long>
{
}