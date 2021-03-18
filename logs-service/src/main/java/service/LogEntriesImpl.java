package service;

import core.ChangeType;
import core.LogEntry;
import db.LogGateway;
import exceptions.RepException;
import repo.AlbumManagerImpl;
import repo.AlbumManagerSingleton;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Collectors;

@WebService(endpointInterface = "service.LogEntries")
public class LogEntriesImpl implements LogEntries {
    private static AlbumManagerImpl manager;
    private static boolean isManagerCreated = false;

    private void initialize() throws Exception {
        if (isManagerCreated)
            throw new Exception();

        isManagerCreated = true;
        AlbumManagerSingleton managerSingleton = AlbumManagerSingleton.INSTANCE;
        managerSingleton.setAlbumManagerImplementation("repo.AlbumManagerImpl");
        manager = managerSingleton.getAlbumManagerImplementation();
    }

    // Sort log entries chronologically by timestamp
    @Override
    public LogEntry[] getChangeLogs(Date from, Date to, ChangeType changeType) {
        try {
            if (!isManagerCreated)
                initialize();

            ArrayList<LogEntry> logs = manager.getChangeLogs(from, to, changeType);

            return logs.toArray(new LogEntry[0]);
        }
        catch(Exception e) {
            throw new RepException(e.getMessage());
        }
    }

    @Override
    public void clearLogs() throws RepException {
        try {
            if (!isManagerCreated)
                initialize();

            manager.clearLogs();
        }
        catch(Exception e) {
            throw new RepException(e.getMessage());
        }
    }
}
