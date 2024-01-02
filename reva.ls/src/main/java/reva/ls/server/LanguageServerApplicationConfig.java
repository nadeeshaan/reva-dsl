package reva.ls.server;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Set;

public class LanguageServerApplicationConfig implements ServerApplicationConfig {
    @Override
    public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> set) {
        ServerEndpointConfig config =
                ServerEndpointConfig.Builder.create(WebsocketLanguageServerEndpoint.class, "/").build();
        return Set.of(config);
    }

    @Override
    public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> set) {
        return set;
    }
}
