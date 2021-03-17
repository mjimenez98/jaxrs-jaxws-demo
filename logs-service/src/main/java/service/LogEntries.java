package service;

import core.ChangeType;
import core.LogEntry;
import exceptions.RepException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Date;

@WebService
@SOAPBinding
public interface LogEntries {
    @WebMethod(operationName = "getChangeLogs")
    LogEntry[] getChangeLogs(@WebParam(name="from") Date from, @WebParam(name="to") Date to,
                                      @WebParam(name="changeType") ChangeType changeType);
    @WebMethod(operationName = "clearLogs")
    void clearLogs() throws RepException;
}
