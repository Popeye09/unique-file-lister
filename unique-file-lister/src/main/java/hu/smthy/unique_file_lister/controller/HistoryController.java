package hu.smthy.unique_file_lister.controller;

import hu.smthy.unique_file_lister.domain.UniqueFileAccess;
import hu.smthy.unique_file_lister.service.HistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping(path = "/history", produces = "application/json")
    public Iterable<UniqueFileAccess> history(){
        return historyService.readAll();
    }

}
