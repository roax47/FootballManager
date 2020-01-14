package pl.edu.pg.eti.kask.javaee.example.library.manager.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Permissions.class)
public abstract class Permissions_ {

	public static volatile SingularAttribute<Permissions, String> role;
	public static volatile SingularAttribute<Permissions, Perm> permission;
	public static volatile SingularAttribute<Permissions, Integer> id;
	public static volatile SingularAttribute<Permissions, String> operation;

	public static final String ROLE = "role";
	public static final String PERMISSION = "permission";
	public static final String ID = "id";
	public static final String OPERATION = "operation";

}

