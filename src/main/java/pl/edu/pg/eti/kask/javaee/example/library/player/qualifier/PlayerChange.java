package pl.edu.pg.eti.kask.javaee.example.library.player.qualifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Player;

import javax.enterprise.context.ApplicationScoped;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApplicationScoped
public class PlayerChange {
    Player player;
}
