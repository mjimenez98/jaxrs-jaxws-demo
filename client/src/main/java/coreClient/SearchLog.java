package coreClient;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class SearchLog {
    private static String xmlGregorianCalendarConversionSuffix = ":00.100-04:00";

    private String from;
    private String to;
    private ChangeType type;

    public SearchLog() {
        from = null;
        to = null;
        type = null;
    }

    public SearchLog(String from, String to, ChangeType type) {
        this.from = from;
        this.to = to;
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public ChangeType getType() {
        return type;
    }

    public String getTo() {
        return to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setType(ChangeType type) {
        this.type = type;
    }

    public calls.wsdl.ChangeType getWsdlType() {
        if (type == null)
            return null;

        switch (type) {
            case CREATE:
                return calls.wsdl.ChangeType.CREATE;
            case UPDATE:
                return calls.wsdl.ChangeType.UPDATE;
            case DELETE:
                return calls.wsdl.ChangeType.DELETE;
            default:
                return null;
        }
    }

    public static XMLGregorianCalendar convertDateToWsdl(String date) {
        if (isInvalid(date))
            return null;

        try {
            String formattedDate = date + xmlGregorianCalendarConversionSuffix;
            DatatypeFactory factory = DatatypeFactory.newInstance();

            return factory.newXMLGregorianCalendar(formattedDate);
        } catch (DatatypeConfigurationException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static boolean isInvalid(String date) {
        return (date == null || date.isEmpty());
    }
}
