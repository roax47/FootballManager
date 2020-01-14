package pl.edu.pg.eti.kask.javaee.example.library.player.model;

import java.time.LocalDate;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pl.edu.pg.eti.kask.javaee.example.library.club.model.Club;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Player.class)
public abstract class Player_ {

	public static volatile SingularAttribute<Player, String> firstName;
	public static volatile SingularAttribute<Player, String> lastName;
	public static volatile SingularAttribute<Player, Integer> number;
	public static volatile SingularAttribute<Player, Date> modificationDate;
	public static volatile SingularAttribute<Player, LocalDate> birthdayDate;
	public static volatile SingularAttribute<Player, Club> club;
	public static volatile SingularAttribute<Player, Integer> id;
	public static volatile SingularAttribute<Player, Position> position;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String NUMBER = "number";
	public static final String MODIFICATION_DATE = "modificationDate";
	public static final String BIRTHDAY_DATE = "birthdayDate";
	public static final String CLUB = "club";
	public static final String ID = "id";
	public static final String POSITION = "position";

}

