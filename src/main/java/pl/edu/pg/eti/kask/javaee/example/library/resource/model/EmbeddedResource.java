package pl.edu.pg.eti.kask.javaee.example.library.resource.model;

import lombok.*;

import javax.json.bind.annotation.JsonbProperty;
import java.util.HashMap;
import java.util.Map;

/**
 * Representation of embedded resource for HAL. Pure JAX-RS is not supporting HAL directly at this moment.
 *
 * @author psysiu
 */
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class EmbeddedResource<V> {

    /**
     * HATEOAS links.
     */
    @Singular
    @JsonbProperty("_links")
    private Map<String, Link> links = new HashMap<>();

    /**
     * Embedded resource, i.e. collection.
     */
    @Singular("embedded")
    @JsonbProperty("_embedded")
    private Map<String, V> embedded;

    @Builder
    private EmbeddedResource(Map<String, Link> links, Map<String, V> embedded) {
        if (embedded.size() > 1) {
            throw new IllegalArgumentException("There can be only one embedded object.");
        }
        this.links = links;
        this.embedded = embedded;
    }
}
