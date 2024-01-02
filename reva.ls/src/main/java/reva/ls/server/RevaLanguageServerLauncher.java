package reva.ls.server;

import org.glassfish.tyrus.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RevaLanguageServerLauncher {

    private static final String DEFAULT_HOST = "0.0.0.0";

    private static final int DEFAULT_PORT = 5008;

    public static final String PORT_ARG = "-port";

    public static final String HOST_ARG = "-host";

    private static final Logger LOGGER = LoggerFactory.getLogger(RevaLanguageServerLauncher.class);

    public static void main(String[] args) {
        Server websocketServer = null;

        Map<String, String> argsMapping = getArgsMapping(args);

        try {
            websocketServer = new Server(getHost(argsMapping), getPort(argsMapping),
                    null, null,
                    LanguageServerApplicationConfig.class,
                    HealthCheckEndpoint.class);
            websocketServer.start();
            Thread.currentThread().join();
        } catch (Exception e) {
            LOGGER.error("Failed to launch the Reva websocket language server");
        } finally {
            if (websocketServer != null) {
                websocketServer.stop();
            }
            LOGGER.warn("Shutting down the Reva server");
        }
    }

    private static Map<String, String> getArgsMapping(String[] args) {
        Map<String, String> argsMapping = new HashMap<>();

        for (String arg : args) {
            String[] argComponents = arg.split("=");
            argsMapping.put(argComponents[0], argComponents.length == 2 ? argComponents[1] : null);
        }

        return argsMapping;
    }

    private static int getPort(Map<String, String> args) {
        Optional<String> portArg = getArgument(args, PORT_ARG);
        return portArg.map(Integer::parseInt).orElse(DEFAULT_PORT);
    }

    private static String getHost(Map<String, String> args) {
        Optional<String> portArg = getArgument(args, HOST_ARG);
        return portArg.orElse(DEFAULT_HOST);
    }

    private static Optional<String> getArgument(Map<String, String> args, String argName) {
        return Optional.ofNullable(args.get(argName));
    }
}
