package hu.smthy.unique_file_lister.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a unique file access operation, including the directory accessed, user, and timestamp.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UniqueFileAccess {
    private Long id;
    private String username;
    private Long timestamp;
    private String directory;
    private String extension;
}
