package hu.smthy.unique_file_lister.controller;

import hu.smthy.unique_file_lister.service.UniqueFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.util.Collections;
import java.util.Map;

@RestController
@Tag(name = "files", description = "Unique file counting operations")
public class UniqueFileController {

    private final UniqueFileService uniqueFileService;

    public UniqueFileController(UniqueFileService uniqueFileService) {
        this.uniqueFileService = uniqueFileService;
    }

    @GetMapping(path = "/getUnique/**", produces = "application/json")
    @Operation(
            summary = "Recursively traverse the directory and find files with unique base name and count their occurrences.",
            description = """
                    Successful attempts are recorded and can be recalled at /history.
                    NOTES: Swagger-UI percent-encodes '/' in path parameters, so to test nested paths you may need to 
                    forge the request yourself, e.g.: GET /getUnique/usr/bin.
                    Note that the implementation does not check for symbolic links, so they are also found.
                    Do not call this on a directory that has recursive symbolic links as it will stuck in an infinite loop.
                    It is recommended to test with /getUnique/{directory}.
                    Implementation may be added later for detecting symbolic link recursion.
                    """,
            parameters = {
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "directory",
                            description = "Directory to traverse, relative to root",
                            required = false,
                            schema = @Schema(type = "string", example = "bin")
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "extension",
                            description = "only files with this extension",
                            required = false,
                            schema = @Schema(type = "string", example = "conf")
                    ),
                    @Parameter(
                            in = ParameterIn.QUERY,
                            name = "username",
                            description = "user initiating the request",
                            required = false,
                            schema = @Schema(type = "string")
                    ),
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Directory not found"),
                    @ApiResponse(responseCode = "400", description = "Not a directory"),
                    @ApiResponse(responseCode = "403", description = "Access denied")
            }
    )
    public ResponseEntity<Map<String, Integer>> getUnique(
            @RequestParam(required = false) String extension,
            @RequestParam(required = false) String username,
            HttpServletRequest request
    ) {
        Map<String, Integer> result;

        String path = request.getRequestURI().substring("/getUnique".length());
        path = path.isEmpty() ? "/" : path;

        try{
            result = uniqueFileService.getUniqueFiles(path, username == null ? System.getProperty("user.name") : username, extension);
        }
        catch (FileNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyMap());
        }
        catch (NotDirectoryException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyMap());
        }
        catch(SecurityException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyMap());
        }
        return ResponseEntity.ok(result);
    }
}
