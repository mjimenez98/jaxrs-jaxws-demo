package calls;

import calls.wsdl.*;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

public class LogEntryClient extends WebServiceGatewaySupport {
    public List<LogEntry> getChangeLogs(XMLGregorianCalendar from, XMLGregorianCalendar to, ChangeType type) {
        ObjectFactory objectFactory = new ObjectFactory();
        GetChangeLogs getChangeLogsObject = objectFactory.createGetChangeLogs();
        getChangeLogsObject.setFrom(from);
        getChangeLogsObject.setTo(to);
        getChangeLogsObject.setChangeType(type);

        JAXBElement<GetChangeLogs> request = objectFactory.createGetChangeLogs(getChangeLogsObject);

        JAXBElement<GetChangeLogsResponse> response = (JAXBElement<GetChangeLogsResponse>) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8090/logEntries", request);

        return response.getValue().getReturn();
    }
}
