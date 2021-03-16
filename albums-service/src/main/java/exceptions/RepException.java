package exceptions;

import javax.xml.ws.WebFault;
import javax.xml.ws.WebServiceException;
import java.io.Serializable;

@WebFault(name = "RepException", targetNamespace = "http://log-service.com")
public class RepException extends WebServiceException implements Serializable {
    public RepException(String message){
        super (message);
    }
    public RepException(String message, Throwable cause){
        super(message, cause);
    }
}

