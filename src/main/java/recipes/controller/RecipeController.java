package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.model.Recipe;
import recipes.model.User;
import recipes.service.RecipeService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
@Validated
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/{id}")
    public ResponseEntity getRecipe(@PathVariable @Min(value = 0) Long id) {
        return recipeService.getRecipe(id);
    }

    @GetMapping("/search")
    public ResponseEntity search(@RequestParam Map<String, String> params) {
        return recipeService.search(params);
    }

    @PostMapping("/new")
    public ResponseEntity<?> newRecipe(@AuthenticationPrincipal User user,
                                       @Valid @RequestBody      Recipe recipe) {
        return recipeService.save(recipe, user);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@AuthenticationPrincipal   User user,
                                 @PathVariable              Long id,
                                 @RequestBody @Valid        Recipe recipe) {
        return recipeService.update(id, recipe, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@AuthenticationPrincipal   User user,
                                 @PathVariable              Long id) {
        return recipeService.delete(id, user);
    }
}
