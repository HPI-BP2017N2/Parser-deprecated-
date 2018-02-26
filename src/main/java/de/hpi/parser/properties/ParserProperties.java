package de.hpi.parser.properties;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties("parser")
@Getter @Setter
public class ParserProperties {

    @NotBlank
    private String root, rulesCallbackRoute;

}
