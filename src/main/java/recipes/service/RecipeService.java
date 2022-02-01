package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import recipes.model.Recipe;
import recipes.model.User;
import recipes.repositories.RecipeRepo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepo recipeRepo;

    public ResponseEntity getRecipe(Long id) {
        Optional<Recipe> opt = recipeRepo.findById(id);
        if (!opt.isPresent())
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok().body(opt.get());
    }

    public ResponseEntity delete(Long id, User user) {
        ResponseEntity resp = check(id, user);
        if (resp.getStatusCode().equals(HttpStatus.NO_CONTENT))
            recipeRepo.deleteById(id);
        return resp;
    }

    public ResponseEntity update(Long id, Recipe recipe, User user) {
        ResponseEntity resp = check(id, user);
        if (resp.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
            recipe.setId(id);
            recipe.setDate(LocalDateTime.now());
            recipeRepo.save(recipe);
        }
        return resp;
    }

    public ResponseEntity save(Recipe recipe, User user) {
        recipe.setUser(user);
        recipe = recipeRepo.save(recipe);
        Map<String, Long> resp = new HashMap<>();
        resp.put("id", recipe.getId());
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    public ResponseEntity search(Map<String, String> params) {
        if (params.size() != 1 || (!(params.containsKey("category")
                || params.containsKey("name"))))
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        List<Recipe> resp = null;
        Map.Entry<String,String> entry = params.entrySet().stream().findAny().get();
        resp = entry.getKey().equals("category")
                ? recipeRepo.findAllByCategoryIgnoreCaseOrderByDateDesc(entry.getValue())
                : recipeRepo.findAllByNameContainingIgnoreCaseOrderByDateDesc(entry.getValue());
        return  ResponseEntity.ok().body(resp);
    }

    private ResponseEntity check(Long id, User user) {
        Optional<Recipe> opt = recipeRepo.findById(id);
        if (!opt.isPresent())
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        User recipeUser = opt.get().getUser();
        if (recipeUser != null && !recipeUser.equals(user))
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
