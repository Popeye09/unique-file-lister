package hu.smthy.unique_file_lister.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "history")
public class UniqueFileAccessData {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "unique_file_access_id_sequence"
    )
    private Long id;
    private String username;
    private Long timestamp;
    private String directory;
    private String extension;
}
