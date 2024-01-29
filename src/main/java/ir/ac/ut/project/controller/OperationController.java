package ir.ac.ut.project.controller;

import ir.ac.ut.project.service.OperationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/operation")
public class OperationController {
    private OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping
    public ResponseEntity<?> handleOperation() {
        try {
            List<String> outputLines = this.operationService.runPythonScript();
            return ResponseEntity.ok(outputLines);
        } catch (IOException | InterruptedException exp) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exp);
        }
    }
}
