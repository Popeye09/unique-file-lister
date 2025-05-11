package hu.smthy.unique_file_lister.domain;


import jakarta.persistence.*;

@Entity
@Table(name = "history")
public class UniqueFileAccess {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "unique_file_access_id_sequence"
    )
    private Long id;
    private String user;
    private Long timestamp;
    private String directory;
    private String extension;

    public UniqueFileAccess(Long id, String user, Long timestamp, String directory, String extension){
        this.id = id;
        this.user = user;
        this.timestamp = timestamp;
        this.directory = directory;
        this.extension = extension;
    }

    public Long getId() {
        return id;
    }
}
