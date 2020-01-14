package pl.edu.pg.eti.kask.javaee.example.library.manager.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pl.edu.pg.eti.kask.javaee.example.library.club.model.Club;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Manager.class)
public abstract class Manager_ {

	public static volatile SingularAttribute<Manager, String> password;
	public static volatile SetAttribute<Manager, String> roles;
	public static volatile SingularAttribute<Manager, Club> club;
	public static volatile SingularAttribute<Manager, Integer> id;
	public static volatile SingularAttribute<Manager, String> login;

	public static final String PASSWORD = "password";
	public static final String ROLES = "roles";
	public static final String CLUB = "club";
	public static final String ID = "id";
	public static final String LOGIN = "login";

}

