package dpt.wtf.serverbeat.broadcast.config;

import dpt.wtf.serverbeat.crypto.SecretGenerator;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ToString
public class BoadcastingConfiguration {

    @Value("${config.serverName}")
    private String serverName;

    @Value("${config.serverAddress}")
    private String serverAddress;

    @Value("${config.relayTarget}")
    private String relayTarget;

    @Value("${config.salt}")
    private String salt;

    private String secret = SecretGenerator.generate(serverName, salt);
}
