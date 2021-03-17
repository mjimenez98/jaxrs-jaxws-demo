package client;

import calls.LogEntryClient;
import calls.SoapConfiguration;
import calls.wsdl.LogEntry;
import coreClient.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class LogsController {
    @GetMapping("/logs")
    public String getLogs(@ModelAttribute SearchLog log, Model model) {
        model.addAttribute("search", new SearchLog());

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SoapConfiguration.class);
        LogEntryClient logEntryClient = annotationConfigApplicationContext.getBean(LogEntryClient.class);
        List<LogEntry> logs = logEntryClient.getChangeLogs(null, null, null);
        model.addAttribute("logs", logs);

        return "logs";
    }
}
