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

public interface PhotoGroupRepository extends JpaRepository<PhotoGroup, Long>{

    @Override
    PhotoGroup save(PhotoGroup photoGroup);

    Optional<PhotoGroup> findById(Long id);

    List<PhotoGroup> findAll();

    @Query("SELECT MAX(p.photoGroupId) FROM PhotoGroup p")
    Long findMaxPhotoGroupId();


}
