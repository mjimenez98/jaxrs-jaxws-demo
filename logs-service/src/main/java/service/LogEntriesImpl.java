package service;

import core.ChangeType;
import core.LogEntry;
import exceptions.RepException;
import repo.AlbumManagerImpl;
import repo.AlbumManagerSingleton;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Collectors;

@WebService(name = "LogEntry", endpointInterface = "service.LogEntries")
public class LogEntriesImpl implements LogEntries {

    private static AlbumManagerImpl manager;
    private static boolean isManagerCreated = false;

    private void initialize() throws Exception {
        if (isManagerCreated)
            throw new Exception();
        isManagerCreated = true;
        AlbumManagerSingleton managerSingleton = AlbumManagerSingleton.INSTANCE;
        managerSingleton.setAlbumManagerImplementation("business.AlbumManagerImpl");
        manager = managerSingleton.getAlbumManagerImplementation();
    }

    //Sort log entries chronologically by timestamp
    @Override
    public ArrayList<LogEntry> getChangeLogs(Date from, Date to, ChangeType changeType) {
        try{
            ArrayList<LogEntry> logs = manager.getChangeLogs(from, to, changeType);
            return logs.stream().sorted(Comparator.comparing(LogEntry::getTimestamp)).collect(Collectors.toCollection(ArrayList::new));
        }
        catch(Exception e){
            throw new RepException(e.getMessage());
        }
    }

    @Override
    public void clearLogs() throws RepException {
        try {
            manager.clearLogs();
        }
        catch(Exception e){
            throw new RepException(e.getMessage());
        }
    }
}
