package reva.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RevaConfig {
    private Config config;
    
    public RevaConfig() {
        config = ConfigFactory.load();
    }
    
    public RevaConfig(String configName) {
        config = ConfigFactory.load(configName);
    }

    public Boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public String getString(String path) {
        try {
            return config.getString(path);
        } catch (ConfigException e) {
            return "";
        }
    }

    public Double getDouble(String path) {
        return config.getDouble(path);
    }

    public Long getLong(String path) {
        return config.getLong(path);
    }

    public Integer getInt(String path) {
        return config.getInt(path);
    }

    public TemporalAmount getTemporal(String path) {
        return config.getTemporal(path);
    }

    public Duration getDuration(String path) {
        return config.getDuration(path);
    }

    public List<Integer> getIntList(String path) {
        return config.getIntList(path);
    }

    public boolean getIsNull(String path) {
        return config.getIsNull(path);
    }

    public Number getNumber(String path) {
        return config.getNumber(path);
    }

    public Period getPeriod(String path) {
        return config.getPeriod(path);
    }

    public Optional<ConfigObject> getObject(String path) {
        try {
            return Optional.ofNullable(config.getObject(path));
        } catch (ConfigException e) {
            return Optional.empty();
        }
    }

    public List<String> getStringList(String path) {
        try {
            return config.getStringList(path);
        } catch (ConfigException e) {
            return Collections.emptyList();
        }
    }

    public <T> Optional<T> getBean(Class<T> clazz, String path) {
        try {
            return Optional.ofNullable(ConfigBeanFactory.create(config.getConfig(path), clazz));
        } catch (ConfigException e) {
            return Optional.empty();
        }
    }

    public List<String> getStringListSilent(String path) {
        try {
            return config.getStringList(path);
        } catch (ConfigException e) {
            return Collections.emptyList();
        }
    }

    public <T> Optional<T> getBeanSilent(Class<T> clazz, String path) {
        try {
            return Optional.ofNullable(ConfigBeanFactory.create(config.getConfig(path), clazz));
        } catch (ConfigException e) {
            return Optional.empty();
        }
    }

    public <T> List<T> getBeansListSilent(Class<T> clazz, String path) {
        try {
            List<? extends ConfigObject> objectList = config.getObjectList(path);
            List<T> beansList = new ArrayList<>();

            objectList.forEach(
                    configObject -> {
                        try {
                            beansList.add(ConfigBeanFactory.create(configObject.toConfig(), clazz));
                        } catch (ConfigException e) {
                            // Silently ignore
                        }
                    });

            return beansList;
        } catch (ConfigException e) {
            return Collections.emptyList();
        }
    }

    public boolean hasPathOrNull(String path) {
        return config.hasPathOrNull(path);
    }
}
