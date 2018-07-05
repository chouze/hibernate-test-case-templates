package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
  @Test
  public void hhh123Test() throws Exception {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();

    //Setup our initial objects
    Owner owner = new Owner();
    Owned owned = new Owned();
    owner.setOwned(owned);
    entityManager.persist(owner);

    Owner dbOwner = entityManager.find(Owner.class, owner.getId());
    assertNotNull(dbOwner);
    assertNotNull(dbOwner.getOwned());

    entityManager.getTransaction().commit();
    entityManager.close();

    //new transaction to replace owned
    entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();

    dbOwner = entityManager.find(Owner.class, owner.getId());
    assertNotNull(dbOwner);
    assertEquals(owner.getId(), dbOwner.getId());

    //This test will pass if getOwned() is called here
    //assertNotNull(dbOwner.getOwned());

    Owned newOwned = new Owned();
    dbOwner.setOwned(newOwned);

    //we can call getOwned() all we want now,
    // but it won't change anything since we've already called setOwned
    assertNotNull(dbOwner.getOwned());

    entityManager.getTransaction().commit();
    entityManager.close();

    //new transaction to verify the database has the correct owned object
    entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    dbOwner = entityManager.find(Owner.class, owner.getId());

    assertNotNull(dbOwner);
    assertNotNull(dbOwner.getOwned());
    assertEquals(newOwned.getId(), dbOwner.getOwned().getId());
    assertNotEquals(owned.getId(), dbOwner.getOwned().getId());
  }
}
