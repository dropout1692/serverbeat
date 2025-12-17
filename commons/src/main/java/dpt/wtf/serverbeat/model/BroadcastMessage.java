package dpt.wtf.serverbeat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class BroadcastMessage {

    private String serverName;
    private String serverAddress;
    private String secret;
}
