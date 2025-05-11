package hu.smthy.unique_file_lister.repository;


import hu.smthy.unique_file_lister.domain.UniqueFileAccessData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniqueFileAccessRepository extends CrudRepository<UniqueFileAccessData, Long> {
}
