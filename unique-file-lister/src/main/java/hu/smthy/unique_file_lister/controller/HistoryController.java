package hu.smthy.unique_file_lister.controller;

import hu.smthy.unique_file_lister.domain.UniqueFileAccess;
import hu.smthy.unique_file_lister.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for retrieving the history of unique file access operations.
 * Provides an endpoint to fetch all past unique-file-access records.
 */
@RestController
@Tag(name = "history", description = "Retrieve past unique-file-access records")
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    /**
     * Handles GET requests to /history to retrieve all unique file access records.
     *
     * <p>This method returns a collection of all previously recorded unique-file-access operations,
     * including details such as the directory path, username, extension filter, and timestamp.
     *
     * <p>The response is a JSON array of UniqueFileAccess objects.
     *
     * <p>HTTP Status Codes:
     * <ul>
     * <li><b>200 OK</b>: Successfully retrieved the history list.</li>
     * </ul>
     *
     * <p>Example Request:
     * <pre>
     * GET /history
     * </pre>
     *
     * <p>Example Response:
     * <pre>
     * [
     *   {
     *     "directory": "/usr/bin",
     *     "username": "user1",
     *     "extension": null,
     *     "timestamp": 1633024800000
     *   },
     *   {
     *     "directory": "/etc",
     *     "username": "user2",
     *     "extension": "conf",
     *     "timestamp": 1633024900000
     *   }
     * ]
     * </pre>
     *
     * @return an iterable collection of {@link UniqueFileAccess} objects
     */
    @GetMapping(path = "/history", produces = "application/json")
    @Operation(
            summary = "List all unique-file-access events",
            description = "Returns a collection of all previously recorded unique-file-access operations, including directory path, username, extension filter, and timestamp.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved history list",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = UniqueFileAccess.class,
                                            type = "array"
                                    )
                            )
                    )
            }
    )
    public Iterable<UniqueFileAccess> history(){
        return historyService.readAll();
    }
}
