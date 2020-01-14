package pl.edu.pg.eti.kask.javaee.example.library.club.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Player;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Club.class)
public abstract class Club_ {

	public static volatile SingularAttribute<Club, LocalDate> foundingDate;
	public static volatile SingularAttribute<Club, Manager> manager;
	public static volatile ListAttribute<Club, Player> players;
	public static volatile SingularAttribute<Club, League> league;
	public static volatile SingularAttribute<Club, String> name;
	public static volatile SingularAttribute<Club, String> stadiumName;
	public static volatile SingularAttribute<Club, Integer> id;

	public static final String FOUNDING_DATE = "foundingDate";
	public static final String MANAGER = "manager";
	public static final String PLAYERS = "players";
	public static final String LEAGUE = "league";
	public static final String NAME = "name";
	public static final String STADIUM_NAME = "stadiumName";
	public static final String ID = "id";

}

