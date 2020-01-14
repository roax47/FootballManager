package pl.edu.pg.eti.kask.javaee.example.library.club.model;

import lombok.*;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Player;
import pl.edu.pg.eti.kask.javaee.example.library.resource.model.Link;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@NoArgsConstructor
@EqualsAndHashCode(exclude = {"players", "links","manager"})
@ToString(exclude = {"players", "links","manager"})
@Entity
@Table(name = "clubs")
@NamedQuery(name = Club.Queries.FIND_ALL, query = "select club from Club club")
@NamedQuery(name = Club.Queries.FIND_CLUB_BY_MANAGER, query = "select club from Club club where club.manager.login like :login")
@NamedQuery(name = Club.Queries.COUNT, query = "select count(club) from Club club")
@NamedQuery(name = Club.Queries.FIND_BY_ID, query = "select club from Club club where club.manager.id like :id")
@NamedEntityGraph(name = Club.Graphs.WITH_PLAYERS_AND_MANAGER,
        attributeNodes = {@NamedAttributeNode("players"),
                          @NamedAttributeNode("manager")})
public class Club implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "Club.findAll";
        public static final String COUNT = "Club.count";
        public static final String FIND_CLUB_BY_MANAGER = "Club.findClubByManager";
        public static final String FIND_BY_ID = "Club.findClub";
    }
    public static class Graphs {
        public static final String WITH_PLAYERS_AND_MANAGER= "Club(players,manager)";
    }
    @Id
    @GeneratedValue
    @Getter
    private Integer id;

    @Getter
    @Setter
    @NotBlank(message = "Name can't be empty")
    @Size(max = 35,message = "name can be only 35 characters long")
    @Column(length = 35)
    private String name;

    @Getter
    @Setter
    @Column(name = "founding_date")
    @PastOrPresent
    @NotNull(message = "Club has to have a founding date")
    private LocalDate foundingDate;

    @Getter
    @Setter
    @NotBlank(message = "Stadium name can't be empty")
    @Size(max = 35,message = "Stadium name can be only 35 characters long")
    @Column(length = 35)
    private String stadiumName;

    @NotNull(message = "Club has to belong to a league")
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private League league;

    @JsonbTransient
    @OneToMany(mappedBy = "club",cascade = CascadeType.ALL)
    @Getter
    @Setter
    private List<Player> players = new ArrayList<>();

    @JsonbTransient
    @OneToOne(mappedBy = "club",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @Getter
    @Setter
    @NotNull(message = "Club has to have a manager")
    private Manager manager;

    public Club(String name,LocalDate foundingDate,String stadiumName,League league) {
        this.name = name;
        this.foundingDate = foundingDate;
        this.stadiumName = stadiumName;
        this.league = league;
    }


    @JsonbProperty("_links")
    @Transient
    @Getter
    @Setter
    private Map<String, Link> links = new HashMap<>();

}
