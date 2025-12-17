package dpt.wtf.serverbeat.relay.service;

import dpt.wtf.serverbeat.model.BroadcastMessage;
import dpt.wtf.serverbeat.relay.enums.ServerState;
import dpt.wtf.serverbeat.relay.model.ServerEntry;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ServerManager {

    private List<ServerEntry> servers;

    public List<ServerEntry> getServers() {
        return servers.stream()
                .filter(s -> !ServerState.DEAD.equals(s.getState()))
                .sorted(Comparator.comparing(ServerEntry::getLastPing))
                .toList();
    }

    public void addServer(BroadcastMessage server) {

        ServerEntry newEntry = new ServerEntry();
        newEntry.setName(server.getServerName());
        newEntry.setIp(server.getServerAddress());
        newEntry.setLastPing(LocalDateTime.now());

        evaluate(newEntry);
        this.servers.add(newEntry);
    }

    public void evaluate(ServerEntry entry) {
        servers.forEach(s -> s.setState(determineState(s)));
    }

    @Scheduled(fixedRate = 1000)
    private void update() {
        servers.forEach(this::evaluate);
        collectCorpses();
    }

    private void collectCorpses() {
        servers.forEach(s -> {
            if (ServerState.DEAD.equals(s.getState())) {
                servers.remove(s);
            }
        });
    }

    private ServerState determineState(ServerEntry server) {

        LocalDateTime time = server.getLastPing();
        LocalDateTime now = LocalDateTime.now();
        int diffSeconds = time.getSecond() - now.getSecond();

        final int TWO_MINUTES = 120;
        final int FOUR_MINUTES = 240;
        final int EIGHT_MINUTES = 480;
        final int TEN_MINUTES = 600;
        final int TWENTY_MINUTES = 1200;

        if (diffSeconds < TWO_MINUTES) {
            return ServerState.PERFECT;
        } else if (diffSeconds < FOUR_MINUTES) {
            return ServerState.GOOD;
        } else if (diffSeconds < EIGHT_MINUTES) {
            return ServerState.MEDIOCRE;
        } else if (diffSeconds < TEN_MINUTES) {
            return ServerState.BAD;
        } else if (diffSeconds < TWENTY_MINUTES) {
            return ServerState.UNKNOWN;
        } else {
            return ServerState.DEAD;
        }
    }
}
