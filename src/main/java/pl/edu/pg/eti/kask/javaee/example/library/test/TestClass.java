package pl.edu.pg.eti.kask.javaee.example.library.test;

import pl.edu.pg.eti.kask.javaee.example.library.player.model.NumberSorter;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Player;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Player_;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Position;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class TestClass {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void initTest() throws Exception{
        clearDb();
        ArrayList<Player> testPlayers = new ArrayList<>() ;
        testPlayers.add(new Player("Test","Test", LocalDate.now(),
                3, Position.DEFENDER, new Date(System.currentTimeMillis())));
        testPlayers.add(new Player("Test","Test", LocalDate.now(),
                2, Position.DEFENDER, new Date(System.currentTimeMillis())));
        testPlayers.add(new Player("Test","Test", LocalDate.now(),
                1, Position.DEFENDER, new Date(System.currentTimeMillis())));
        testPlayersCreateAndRead(testPlayers);
        testPlayersUpdate(testPlayers);
        testPlayersDelete(testPlayers);
        testPlayersSort(testPlayers);
        testPlayersFilter(testPlayers);
        clearDb();
    }


    private void testPlayersCreateAndRead(ArrayList<Player> testPlayers) throws Exception{
        for(Player testPlayer:testPlayers) {
            em.persist(testPlayer);
        }
        List<Player> resultList = em.createNamedQuery(Player.Queries.FIND_ALL, Player.class).getResultList();
        if(testPlayers.size()!=resultList.size()) throw new Exception("Create and read test failed");
        for (Player player : resultList) {
            if (player.getId() == null || !player.getLastName().equals("Test"))
                throw new Exception("Create and read test failed");
        }
    }

    private  void testPlayersUpdate(ArrayList<Player> testPlayers) throws Exception{
        for(Player testPlayer:testPlayers) {
            testPlayer.setLastName("TestUpdate");
            em.merge(testPlayer);
        }
        List<Player> resultList = em.createNamedQuery(Player.Queries.FIND_ALL, Player.class).getResultList();
        if(testPlayers.size()!=resultList.size()) throw new Exception("Update test failed");
        for (Player player : resultList) {
            if (player.getId() == null || !player.getLastName().equals("TestUpdate"))
                throw new Exception("Update test failed");
        }
    }

    private void testPlayersDelete(ArrayList<Player> testPlayers) throws Exception{
        for (Player player : testPlayers) {
            em.remove(player);
        }
        List<Player> resultList = em.createNamedQuery(Player.Queries.FIND_ALL, Player.class).getResultList();
        if(resultList.size() != 0) throw new Exception("Delete test failed");
    }
    private void testPlayersSort(ArrayList<Player> testPlayers) throws Exception{
        for(Player testPlayer:testPlayers) {
            em.merge(testPlayer);
        }
        List<Player> resultList = em.createNamedQuery(Player.Queries.FIND_ALL, Player.class).getResultList();
        if(testPlayers.size()!=resultList.size()) throw new Exception("Sort test failed");

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Player> query = builder.createQuery(Player.class);
        Root<Player> root = query.from(Player.class);
        query.orderBy(builder.asc(root.get(Player_.number)));
        query.select(root);
        resultList = em.createQuery(query).getResultList();
        if(resultList.size()!=testPlayers.size()) throw new Exception("Sort test failed");

        testPlayers.sort(new NumberSorter());
        for(int i=0;i<testPlayers.size();i++){
            if(!testPlayers.get(i).getNumber().equals(resultList.get(i).getNumber()))
                throw new Exception("Sort test failed");
        }
    }

    private void testPlayersFilter(ArrayList<Player> testPlayers) throws Exception{
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Player> query = builder.createQuery(Player.class);
        Root<Player> root = query.from(Player.class);
        query.where(builder.equal(root.get(Player_.number), 2));
        query.select(root);
        List<Player> resultList = em.createQuery(query).getResultList();
        if(resultList.size()!=1) throw new Exception("Filter test failed");
        testPlayers.removeIf(player -> player.getNumber()!=2);
        if(!resultList.get(0).getNumber().equals(testPlayers.get(0).getNumber()))
            throw new Exception("Filter test failed");

    }
    private void clearDb(){
        List<Player> resultList = em.createNamedQuery(Player.Queries.FIND_ALL, Player.class).getResultList();
        for(Player player:resultList) {
            em.remove(player);
        }
    }
}
