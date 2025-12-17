package dpt.wtf.serverbeat.relay.model;

import dpt.wtf.serverbeat.relay.enums.ServerState;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class ServerEntry {

    private String name;
    private String ip;
    private ServerState state;
    private LocalDateTime lastPing;
}
