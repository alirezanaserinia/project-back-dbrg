package ir.ac.ut.project.service;

import ir.ac.ut.project.config.OperationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


@Service
public class OperationService {
    private final String scriptLocation;
    private final String scriptName;

    @Autowired
    public OperationService(OperationConfig properties) {
        this.scriptLocation = properties.getPyScriptPath();
        this.scriptName = properties.getPyScriptName();
    }

    public List<String> runPythonScript() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("python", this.scriptLocation + this.scriptName);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        List<String> results = readProcessOutput(process.getInputStream());

        return results;
    }

    private List<String> readProcessOutput(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }


}
