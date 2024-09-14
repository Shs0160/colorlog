package color_log.colorlog.repository;

import color_log.colorlog.domain.User;
import color_log.colorlog.domain.PhotoGroup;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long>{

    @Override
    User save(User user);
    @Query("SELECT MAX(u.UserId) FROM User u")
    Long findMaxUserId();



}
