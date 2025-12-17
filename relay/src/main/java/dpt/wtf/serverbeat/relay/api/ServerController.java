package dpt.wtf.serverbeat.relay.api;

import dpt.wtf.serverbeat.model.BroadcastMessage;
import dpt.wtf.serverbeat.relay.model.ServerEntry;
import dpt.wtf.serverbeat.relay.service.ServerManager;
import dpt.wtf.serverbeat.relay.service.ServerResponseFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/broadcast")
public class ServerController {

    private final ServerManager manager;
    private final ServerResponseFormatter formatter;
    private final ServerManager serverManager;

    public ServerController(
            ServerManager manager,
            ServerResponseFormatter formatter,
            ServerManager serverManager) {
        this.manager = manager;
        this.formatter = formatter;
        this.serverManager = serverManager;
    }

    @PostMapping("/")
    public void broadCastServer(
            @RequestBody BroadcastMessage broadcast
    ) {
        serverManager.addServer(broadcast);
    }

    @GetMapping("/")
    public ResponseEntity<String> getServers() {

        List<ServerEntry> servers = serverManager.getServers();
        return ResponseEntity.ok(formatter.format(servers));
    }
}
