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
    PhotoGroup save(PhotoGroup photoGroup);//save하면 회원이 저장소에 저장

    Optional<PhotoGroup> findById(Long id);//아이디로 회원을 찾음

    List<PhotoGroup> findAll();//모든 저장된 회원 리스트를 반환

    @Query("SELECT MAX(p.photoGroupId) FROM PhotoGroup p")
    Long findMaxPhotoGroupId();


}
