package pl.edu.pg.eti.kask.javaee.example.library.club;

import pl.edu.pg.eti.kask.javaee.example.library.club.interceptor.ClubBinding;
import pl.edu.pg.eti.kask.javaee.example.library.club.model.Club;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Player;
import pl.edu.pg.eti.kask.javaee.example.library.player.qualifier.PlayerChange;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

class ClubServiceExtended {
    @PersistenceContext
    EntityManager em;

    @Inject
    HttpServletRequest securityContext;

    @Inject
    private Event<PlayerChange> playerEvent;

    @Transactional
    private void UpdatePlayer(Player player,Club club){
        player.setClub(club);
        em.merge(player);
        playerEvent.fire(new PlayerChange());
    }

    @Transactional
    private boolean UpdatePlayers(Club club){
        if(!club.getPlayers().isEmpty()){
            club.getPlayers().forEach(player->{
               UpdatePlayer(player,club);
            });
            return true;
        }
        return false;
    }

    @ClubBinding
    public Club findClub(int id) {
        return em.createNamedQuery(Club.Queries.FIND_BY_ID, Club.class)
                .setParameter("id", id)
                .setHint("javax.persistence.loadgraph",
                        em.getEntityGraph(Club.Graphs.WITH_PLAYERS_AND_MANAGER))
                .getSingleResult();
    }

    private boolean UpdateManager(Club club){
        if(club.getManager()!=null){
            club.getManager().setClub(club);
            em.merge(club.getManager());
            return true;
        }
        return false;
    }


    void saveNewClub(Club club){
        boolean needToMerge = UpdatePlayers(club);
        needToMerge = UpdateManager(club) || needToMerge;
        if (needToMerge) {
            em.merge(club);
        } else em.persist(club);
    }

    void saveExistingClub(Club club){
        UpdateManager(club);
        UpdatePlayers(club);
        em.merge(club);
    }


    void removeClubFromManager(Club club){
        if(club.getManager()!=null){
            club.getManager().setClub(null);
            em.merge(club.getManager());
            club.setManager(null);
        }
    }

    public boolean checkIfUserIsAdminOrClubManager(Club club){
        if(securityContext.isUserInRole(Manager.Roles.ADMIN)) return true;
        else if(club.getManager()!=null){
            return  club.getManager().getLogin()
                    .equals(securityContext.getUserPrincipal().getName());
        }
        return false;
    }
}
