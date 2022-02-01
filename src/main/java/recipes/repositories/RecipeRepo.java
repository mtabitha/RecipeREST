package recipes.repositories;

import org.springframework.data.repository.CrudRepository;
import recipes.model.Recipe;

import java.util.List;


public interface RecipeRepo extends CrudRepository<Recipe, Long> {

    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);

}
