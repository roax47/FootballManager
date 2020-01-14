package pl.edu.pg.eti.kask.javaee.example.library.player.model;


import lombok.*;
import pl.edu.pg.eti.kask.javaee.example.library.club.model.Club;
import pl.edu.pg.eti.kask.javaee.example.library.resource.model.Link;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@NoArgsConstructor
@EqualsAndHashCode(exclude = {"links","club"})
@ToString(exclude = {"links","club"})
@Entity
@Table(name = "players")
@NamedQuery(name = Player.Queries.FIND_ALL, query = "select player from Player player")
@NamedQuery(name = Player.Queries.FIND_PLAYERS_BY_CLUB,
        query = "select player from Player player where player.club like :club")
@NamedQuery(name = Player.Queries.FIND_PLAYERS_BY_LASTNAME,
        query = "select player from Player player where player.lastName like :lastName")
@NamedQuery(name = Player.Queries.FIND_PLAYERS_BY_MANAGER,
        query = "select player from Player player where player.club.manager.login = :login")
@NamedQuery(name = Player.Queries.FIND_SORTED_PLAYERS_BY_MODIFIED_DATE,
        query = "select player from Player player WHERE player.modificationDate > :date " +
                "ORDER BY player.modificationDate desc")
@NamedEntityGraph(name = Player.Graphs.WITH_CLUB,
        attributeNodes = {@NamedAttributeNode("club")})
public class Player implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "Player.findAll";
        public static final String FIND_PLAYERS_BY_CLUB = "Player.findByClub";
        public static final String FIND_PLAYERS_BY_LASTNAME = "Player.findByLastName";
        public static final String FIND_PLAYERS_BY_MANAGER = "Player.findByManager";
        public static final String FIND_SORTED_PLAYERS_BY_MODIFIED_DATE = "Player.findSortedByModifiedDate";
    }

    public static class Graphs {
        public static final String WITH_CLUB = "Player(Club)";
    }


    @Id
    @GeneratedValue
    @Getter
    private Integer id;

    @Getter
    @Setter
    @NotBlank(message = "First name cannot be empty")
    @Size(max = 35,message = "First name can be only 35 characters long")
    @Column(length = 35)
    private String firstName;


    @Getter
    @Setter
    @NotBlank(message = "Last name cannot be empty")
    @Size(max = 35,message = "Last name can be only 35 characters long")
    @Column(length = 35)
    private String lastName;

    @JsonbTransient
    @ManyToOne
    @JoinColumn(name = "club")
    @Getter
    @Setter
    private Club club;

    @Getter
    @Setter
    @PastOrPresent
    @Column(name = "birthday_date")
    @NotNull(message = "Player has to have a birthday date")
    private LocalDate birthdayDate;


    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Player has to have preffered postion")
    private Position position;


    @Getter
    @Setter
    @Column(name = "number")
    @NotNull(message = "Player has to have a number")
    @Min(value = 1,message ="Number has to be in range 1-99" )
    @Max(value = 99,message ="Number has to be in range 1-99" )
    private Integer number;


    @Getter
    @Setter
    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modification_date")
    private Date modificationDate;

    public Player(String firstName, String lastName,LocalDate birthdayDate,Integer number,Position position,Date modificationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdayDate = birthdayDate;
        this.number = number;
        this.position = position;
        this.modificationDate = modificationDate;
    }

    @JsonbProperty("_links")
    @Transient
    @Getter
    @Setter
    private Map<String, Link> links = new HashMap<>();
}
