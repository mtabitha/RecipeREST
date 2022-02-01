package recipes.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import recipes.model.User;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Long> {

    UserDetails findByEmail(String username);

}
