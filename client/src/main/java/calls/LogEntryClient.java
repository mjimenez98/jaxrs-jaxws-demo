package calls;

import calls.wsdl.LogEntry;
import calls.wsdl.ObjectFactory;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import calls.wsdl.GetChangeLogs;
import calls.wsdl.GetChangeLogsResponse;

import javax.xml.bind.JAXBElement;
import java.util.List;

public class LogEntryClient extends WebServiceGatewaySupport {
    public List<LogEntry> getChangeLogs(String from, String to, String type) {
        ObjectFactory objectFactory = new ObjectFactory();
        GetChangeLogs getChangeLogsObject = objectFactory.createGetChangeLogs();
//        getChangeLogsObject.setFrom(from);
//        getChangeLogsObject.setTo(to);
//        getChangeLogsObject.setChangeType(type);

        JAXBElement<GetChangeLogs> request = objectFactory.createGetChangeLogs(getChangeLogsObject);

        JAXBElement<GetChangeLogsResponse> response = (JAXBElement<GetChangeLogsResponse>) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8090/logEntries", request);

        return response.getValue().getReturn();
    }
}
