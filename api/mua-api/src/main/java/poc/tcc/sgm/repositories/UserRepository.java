package poc.tcc.sgm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import poc.tcc.sgm.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByUserName(String userName);
	
	public Optional<User> findByEmail(String email);
	
	public Optional<User> findByDocumentNumber(String documentNumber);

}
