package ir.ac.ut.project.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class OperationConfig {
    @Value("${app.pythonScriptPath}")
    private String pyScriptPath;

    @Value("${app.pythonScriptName}")
    private String pyScriptName;
}
