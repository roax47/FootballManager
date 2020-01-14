package pl.edu.pg.eti.kask.javaee.example.library.club.view;

import lombok.Setter;
import pl.edu.pg.eti.kask.javaee.example.library.club.ClubService;
import pl.edu.pg.eti.kask.javaee.example.library.club.model.Club;
import pl.edu.pg.eti.kask.javaee.example.library.club.model.League;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;


@Named
@ViewScoped
public class ClubCreateEdit implements Serializable {

    /**
     * Injected club service.
     */
    private ClubService service;

    /**
     * All clubs
     */
    private List<Club> availableClubs;


    /**
     * Club to be displayed.
     */
    @Setter
    private Club club;

    public Club getClub() {
        if(club == null){
            club = new Club();
        }
        return club;
    }

    @Inject
    public ClubCreateEdit(ClubService service) {
        this.service = service;
    }

    @PostConstruct
    public void init() {
        setClub(new Club());
    }
    /**
     * @return all clubs
     */
    public Collection<Club> getAvailableClubs() {
        if (availableClubs == null) {
            if (service.checkIfUserIsAdmin())
                availableClubs = service.findAllClubs();
            else availableClubs = service.findClubByManager();
        }
        return availableClubs;
    }


    /**
     * @return all leagues
     */
    public Collection<League> getAvailableCovers() {
        return List.of(League.values());
    }

    /**
     * Saves currently viewed club
     *
     * @return navigation
     */
    public String saveClub() {
        service.saveClub(club);
        return "club_list?faces-redirect=true";
    }
}
