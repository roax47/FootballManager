package pl.edu.pg.eti.kask.javaee.example.library.manager.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "permissions")
@Builder
@NamedQuery(name = Permissions.Queries.FIND_PERMISSION,
        query = "select permission from Permissions permission where permission.role = :role  " +
                "and permission.operation = :operation")
public class Permissions {

    public static class Queries {
        public static final String FIND_PERMISSION = "Permissions.findPermission";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @Getter
    @Setter
    private String role;

    @Getter
    @Setter
    private String operation;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Perm permission;
}
