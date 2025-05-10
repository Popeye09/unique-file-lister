package hu.smthy.unique_file_lister.domain;


import jakarta.persistence.*;

import java.nio.file.Path;


@Entity
@Table(name = "UniqueFileAccess")
public class UniqueFileAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unique_file_access_id_sequence")
    private Long id;
    private String user;
    private Long timestamp;
    private Path directory;
}
