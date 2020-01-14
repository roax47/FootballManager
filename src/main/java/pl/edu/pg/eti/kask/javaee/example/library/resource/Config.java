package pl.edu.pg.eti.kask.javaee.example.library.resource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Global REST prefix configuration.
 *
 * @author psysiu
 */
@ApplicationPath("/api")
public class Config extends Application {
}
