package facades;

import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private MovieFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //TODO Remove/Change this before use
    public long getMovieCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long MovieCount = (long) em.createQuery("SELECT COUNT(m) FROM Movie m").getSingleResult();
            return MovieCount;
        } finally {
            em.close();
        }

    }

    public List<Movie> getAllMovies() {
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("Movie.getAll").getResultList();
    }

    public Movie getMovieById(long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Movie.class, id);
    }
    public List<Movie> getMoviesByName(String name) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Movie> tq = em.createNamedQuery("Movie.getByName", Movie.class);
        tq.setParameter("name", "%" + name + "%");
        return tq.getResultList();
    }

    public Movie createMovie(Movie movie) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return movie;
    }
    public void populateMovies() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(new Movie(1932, "Shrek", new String[]{"Jepser Hansen", "Henrik Poulsen", "Freddy Fredag"}));
            em.persist(new Movie(1933, "Intersteller", new String[]{"Matt Damon", "Pia Kal", "Freddy Fredag"}));
            em.persist(new Movie(1933, "Run", new String[]{"Karsten Nielsen", "Pia Kal", "Freddy Fredag"}));
            em.persist(new Movie(1934, "Avengers", new String[]{"Iron Man", "Pia Kal", "Freddy Fredag"}));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
