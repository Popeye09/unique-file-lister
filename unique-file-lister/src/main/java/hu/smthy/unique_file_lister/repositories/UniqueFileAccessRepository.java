package hu.smthy.unique_file_lister.repositories;


import hu.smthy.unique_file_lister.domain.UniqueFileAccess;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniqueFileAccessRepository extends CrudRepository<UniqueFileAccess, Long> {
}
