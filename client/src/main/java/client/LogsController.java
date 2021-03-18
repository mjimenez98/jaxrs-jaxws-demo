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

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@Controller
public class LogsController {
    @GetMapping("/logs")
    public String getLogs(@ModelAttribute SearchLog log, Model model) {
        model.addAttribute("search", new SearchLog());

        // Convert params to appropriate data type
        XMLGregorianCalendar from = SearchLog.convertDateToWsdl(log.getFrom());
        XMLGregorianCalendar to = SearchLog.convertDateToWsdl(log.getTo());
        calls.wsdl.ChangeType type = log.getWsdlType();

        // Make SOAP call
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SoapConfiguration.class);
        LogEntryClient logEntryClient = annotationConfigApplicationContext.getBean(LogEntryClient.class);
        List<LogEntry> logs = logEntryClient.getChangeLogs(from, to, type);

        model.addAttribute("logs", logs);

        return "logs";
    }
}
