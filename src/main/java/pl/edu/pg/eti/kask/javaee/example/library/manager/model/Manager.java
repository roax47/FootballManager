package pl.edu.pg.eti.kask.javaee.example.library.manager.model;

import lombok.*;
import pl.edu.pg.eti.kask.javaee.example.library.club.model.Club;
import pl.edu.pg.eti.kask.javaee.example.library.resource.model.Link;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@NoArgsConstructor
@Data
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "links","roles"})
@ToString(exclude = {"links","roles"})
@Entity
@Table(name = "users")
@Builder
@NamedQuery(name = Manager.Queries.FIND_ALL, query = "select manager from Manager manager")
@NamedQuery(name = Manager.Queries.FIND_BY_LOGIN,
        query = "select manager from Manager manager where manager.login like :login")
@NamedEntityGraph(name = Manager.Graphs.WITH_CLUB,
        attributeNodes = {@NamedAttributeNode("club")})
public class Manager implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "Manager.findAll";
        public static final String FIND_BY_LOGIN = "Manager.findByLogin";
    }
    public static class Graphs {
        public static final String WITH_CLUB= "Manager(club)";
    }
    public static class Roles {

        /**
         * Administrator.
         */
        public static final String ADMIN = "ADMIN";

        /**
         * Just a user.
         */
        public static final String MANAGER = "MANAGER";

        public static final String ANONYMOUS = "ANONYMOUS";

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @Getter
    @Setter
    @Size(min = 3, max = 12,message = "Login has to be 3-12 characters long")
    @Column(length = 20,nullable = false, unique = true)
    private String login;

    @Getter
    @Setter
    @Size(min = 3, max = 255,message = "Password has to be 3-255 characters long")
    private String password;


    @JsonbTransient
    @Getter
    @Setter
    @OneToOne
    private Club club;

    public Manager(String login,String password) {
        this.login = login;
        this.password = password;
    }


    @JsonbProperty("_links")
    @Transient
    @Getter
    @Setter
    private Map<String, Link> links = new HashMap<>();


    @Getter
    @Setter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user"))
    @Column(name = "role")
    @Singular
    private Set<String> roles;

}
