package pl.edu.pg.eti.kask.javaee.example.library.club;

import pl.edu.pg.eti.kask.javaee.example.library.club.interceptor.ClubBinding;
import pl.edu.pg.eti.kask.javaee.example.library.club.model.Club;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Player;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ClubService extends ClubServiceExtended{

    public ClubService() { }

    @ClubBinding
    public List<Player> findClubPlayers(Club club) {
        return em.createNamedQuery(Player.Queries.FIND_PLAYERS_BY_CLUB, Player.class)
                .setParameter("club", club)
                .setHint("javax.persistence.loadgraph", em.getEntityGraph(Player.Graphs.WITH_CLUB))
                .getResultList();
    }

    @ClubBinding
    public List<Club> findClubByManager(){
        return em.createNamedQuery(Club.Queries.FIND_CLUB_BY_MANAGER, Club.class)
                .setParameter("login",securityContext.getUserPrincipal().getName())
                .setHint("javax.persistence.loadgraph",
                                    em.getEntityGraph(Club.Graphs.WITH_PLAYERS_AND_MANAGER))
                .getResultList();
    }

    @ClubBinding
    public List<Club> findAllClubs() {
        return em.createNamedQuery(Club.Queries.FIND_ALL, Club.class)
                .setHint("javax.persistence.loadgraph",
                        em.getEntityGraph(Club.Graphs.WITH_PLAYERS_AND_MANAGER))
                .getResultList();
    }

    public boolean checkIfUserIsAdmin(){
       return securityContext.isUserInRole(Manager.Roles.ADMIN);
    }

    @ClubBinding
    public List<Club> findAllClubs(int offset, int limit) {
            return em.createNamedQuery(Club.Queries.FIND_ALL, Club.class)
                    .setFirstResult(offset)
                    .setHint("javax.persistence.loadgraph",
                            em.getEntityGraph(Club.Graphs.WITH_PLAYERS_AND_MANAGER))
                    .setMaxResults(limit)
                    .getResultList();
    }

    public long countClubs() {
        return em.createNamedQuery(Club.Queries.COUNT, Long.class).getSingleResult();
    }


    @Transactional
    @ClubBinding
    public void saveClub(Club club) {
        if (club.getId() == null) {
            saveNewClub(club);
        } else {
            saveExistingClub(club);
        }
    }

    @Transactional
    @ClubBinding
    public void removeClub(Club club) {
        removeClubFromManager(club);
        em.remove(em.merge(club));
    }
}
