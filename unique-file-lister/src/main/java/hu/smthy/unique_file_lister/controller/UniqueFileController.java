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
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(path = { "/getUnique", "/getUnique/{directory:.+}" }, produces = "application/json")
    @Operation(
            summary = "Recursively traverse the directory and find files with unique base name and count their occurrences.",
            description = "Successful attempts are recorded and can be recalled at /history.\n" +
                    "NOTE: Swagger-UI percent-encodes '/' in path parameters, so to test nested paths you may need to " +
                    "forge the request yourself, e.g.: GET /getUnique/usr/local. Sending a request to this endpoint without parameters will list all unique-files in the system (as the directory interpreted as '/'), it may take a long time.\n" +
                    "It is recommended to test with /getUnique/{directory}.",
            parameters = {
                    @Parameter(name="extension", description="only files with this extension"),
                    @Parameter(name="username", description="user initiating the request"),
                    @Parameter(
                            in = ParameterIn.PATH,
                            name = "directory",
                            description = "Directory to traverse, relative to root",
                            required = false,
                            schema = @Schema(type = "string", example = "usr/local")
                    ),
            },
            responses = {
                    @ApiResponse(responseCode="200", description="OK"),
                    @ApiResponse(responseCode="404", description="Directory not found"),
                    @ApiResponse(responseCode="400", description="Not a directory"),
                    @ApiResponse(responseCode="403", description="Access denied")
            }
    )
    public ResponseEntity<Map<String, Integer>> getUnique(
            @PathVariable(value = "directory", required = false) String directory,
            @RequestParam(required = false) String extension,
            @RequestParam(required = false) String username
    ) {

        Map<String, Integer> result;

        String path = (directory == null) ? "/" : "/" + directory;

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
