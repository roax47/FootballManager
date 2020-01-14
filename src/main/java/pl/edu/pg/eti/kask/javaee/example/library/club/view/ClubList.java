package pl.edu.pg.eti.kask.javaee.example.library.club.view;

import pl.edu.pg.eti.kask.javaee.example.library.club.ClubService;
import pl.edu.pg.eti.kask.javaee.example.library.club.model.Club;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ClubList {

    private ClubService service;

    private List<Club> clubs;

    @Inject
    public ClubList(ClubService service) {
        this.service = service;
    }

    public List<Club> getClubs() {
        if (clubs == null) {
            clubs = service.findAllClubs();
        }
        return clubs;
    }

    public Boolean checkIfUserIsClubManagerOrAdmin(Club club){
        return service.checkIfUserIsAdminOrClubManager(club);
    }

    public String removeClub(Club club) {
        service.removeClub(club);
        return "club_list?faces-redirect=true";
    }
}
