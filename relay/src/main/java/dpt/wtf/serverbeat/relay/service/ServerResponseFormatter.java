package dpt.wtf.serverbeat.relay.service;

import dpt.wtf.serverbeat.relay.model.ServerEntry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerResponseFormatter {

    public String format(List<ServerEntry> servers) {

        StringBuilder sb = new StringBuilder();
        for (ServerEntry server : servers) {
            sb.append(String.format("[%s][%s][%s]\n",
                    server.getName(),
                    server.getIp(),
                    server.getState()
            ));
        }

        return sb.toString();
    }
}
