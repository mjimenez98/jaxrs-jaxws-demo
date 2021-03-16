package client;

import coreClient.*;
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

        List<LogEntry> logs = new ArrayList<>();
        logs.add(new LogEntry(new Date(), ChangeType.CREATE, "1"));
        logs.add(new LogEntry(new Date(), ChangeType.DELETE, "2"));

        model.addAttribute("logs", logs);

        return "logs";
    }
}
