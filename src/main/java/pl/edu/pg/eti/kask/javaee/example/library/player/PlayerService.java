package pl.edu.pg.eti.kask.javaee.example.library.player;

import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;
import pl.edu.pg.eti.kask.javaee.example.library.player.interceptor.PlayerBinding;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Player;
import pl.edu.pg.eti.kask.javaee.example.library.player.qualifier.PlayerChange;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class PlayerService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private Event<PlayerChange> playerEvent;


    @Inject
    private HttpServletRequest securityContext;

    public PlayerService() { }

    @PlayerBinding
    public List<Player> findLastModifiedPlayers() {
        Date test = new Date(System.currentTimeMillis() - 5*60 * 1000);
        return em.createNamedQuery(Player.Queries.FIND_SORTED_PLAYERS_BY_MODIFIED_DATE, Player.class)
                .setParameter("date",test)
                .setHint("javax.persistence.loadgraph", em.getEntityGraph(Player.Graphs.WITH_CLUB))
                .getResultList();
    }

    @PlayerBinding
    public List<Player> findAllPlayers() {
        return em.createNamedQuery(Player.Queries.FIND_ALL, Player.class)
                .setHint("javax.persistence.loadgraph", em.getEntityGraph(Player.Graphs.WITH_CLUB))
                .getResultList();
    }

    @PlayerBinding
    public List<Player> findPlayersByLastName(String lastName) {
            return em.createNamedQuery(Player.Queries.FIND_PLAYERS_BY_LASTNAME, Player.class)
                    .setHint("javax.persistence.loadgraph", em.getEntityGraph(Player.Graphs.WITH_CLUB))
                    .setParameter("lastName", lastName)
                    .getResultList();
    }

    @PlayerBinding
    public List<Player> findPlayersByManager() {
            return em.createNamedQuery(Player.Queries.FIND_PLAYERS_BY_MANAGER, Player.class)
                    .setHint("javax.persistence.loadgraph", em.getEntityGraph(Player.Graphs.WITH_CLUB))
                    .setParameter("login", securityContext.getUserPrincipal().getName())
                    .getResultList();
    }


    @PlayerBinding
    public Player findPlayer(int id) {
            return em.find(Player.class, id);
    }


    @PlayerBinding
    @Transactional
    public void savePlayer(Player player) {

        player.setModificationDate(new Date());
        if (player.getId() == null) {
            em.persist(player);

        } else {
            em.merge(player);
        }
        playerEvent.fire(new PlayerChange(player));
    }

    public boolean checkIfUserIsAdmin(){
        return securityContext.isUserInRole(Manager.Roles.ADMIN);
    }

    public boolean checkIfUserIsAdminOrClubManager(Player player){
        if(securityContext.isUserInRole(Manager.Roles.ADMIN)) return true;
        else if(player.getClub() == null) return true;
        else if( player.getClub().getManager()!=null){
            return  player.getClub().getManager().getLogin()
                    .equals(securityContext.getUserPrincipal().getName());
        }
        return false;
    }

    @Transactional
    @PlayerBinding
    public void removePlayer(Player player) {
        em.remove(em.merge(player));
    }
}
