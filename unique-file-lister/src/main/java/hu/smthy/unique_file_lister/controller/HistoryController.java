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

@RestController
@Tag(name = "history", description = "Retrieve past unique-file-access records")
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

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
