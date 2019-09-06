package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Movie;
import utils.EMF_Creator;
import facades.MovieFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("movie")
public class MovieResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/startcode",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final MovieFacade FACADE = MovieFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieCount() {
        long count = FACADE.getMovieCount();
        return "{\"count\":" + count + "}";
    }
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAll() {
    List<Movie> movies = FACADE.getAllMovies();
        System.out.println(movies);
        return GSON.toJson(movies);
    }
    @Path("populate")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String populate() {
        FACADE.populateMovies();
        return "{\"msg\":\"done!\"}";
    }
//
//    @PUT
//    @Path("/{id}")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public String MovieByID(Movie entity, @PathParam("id") int id) {
//        Movie emp = FACADE.getMovieById(id);
//        return GSON.toJson(emp);
//    }

    @Path("/name/{name}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String MovieByName(Movie entity, @PathParam("name") String name) {
        List<Movie> movies = FACADE.getMoviesByName(name);
        return GSON.toJson(movies);
    }
}
