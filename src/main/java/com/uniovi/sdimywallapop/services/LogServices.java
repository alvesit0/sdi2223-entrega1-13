package com.uniovi.sdimywallapop.services;

import com.uniovi.sdimywallapop.entities.Log;
import com.uniovi.sdimywallapop.entities.User;
import com.uniovi.sdimywallapop.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogServices {

    @Autowired
    private LogRepository logRepository;

    @PostConstruct
    public void init() {

    }

    public List<Log> getLogs() {
        List<Log> logs = new ArrayList<Log>();
        logRepository.findAll().forEach(logs::add);
        return logs;
    }


    public void addLog(Log log) {
        logRepository.save(log);
    }

}
