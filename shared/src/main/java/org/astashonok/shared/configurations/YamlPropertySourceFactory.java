package org.astashonok.shared.configurations;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.NonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class YamlPropertySourceFactory implements PropertySourceFactory {

    @NonNull
    @Override
    public PropertySource<?> createPropertySource(String name, @NonNull EncodedResource resource)
            throws IOException {

        if (!resource.getResource().exists()) {
            throw new FileNotFoundException();
        }

        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resource.getResource());
        factory.afterPropertiesSet();

        return new PropertiesPropertySource(
                ObjectUtils.defaultIfNull(name, resource.getResource().getFilename()),
                Objects.requireNonNull(factory.getObject())
        );
    }
}

