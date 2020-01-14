package pl.edu.pg.eti.kask.javaee.example.library.player.qualifier;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;

@ApplicationScoped
public class PlayerChangedListener {

    @Inject @Push
    private PushContext push;

    public void sendRefresh(@Observes PlayerChange player) {
        push.send("updateTable");
    }
}

