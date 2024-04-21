package com.example.BFF1;

import com.example.BFF1.WebBff.BffController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class LogService {
    private static final Logger logger = Logger.getLogger(BffController.class.toString());

    private final LogRepository logRepository;


    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }


    public void logAction(String action, String method, String url) {
        Log log = new Log();
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        log.setMethod(method);
        log.setUrl(url);
        logRepository.save(log);
    }




}
