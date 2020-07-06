package poc.tcc.sgm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import poc.tcc.sgm.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
