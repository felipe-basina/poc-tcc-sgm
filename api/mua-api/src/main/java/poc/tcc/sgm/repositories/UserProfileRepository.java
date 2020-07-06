package poc.tcc.sgm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import poc.tcc.sgm.models.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

}
