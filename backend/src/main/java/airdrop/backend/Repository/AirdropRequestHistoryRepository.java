package airdrop.backend.Repository;

import airdrop.backend.entity.AirdropRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirdropRequestHistoryRepository extends JpaRepository<AirdropRequestHistory, Integer> {

    List<AirdropRequestHistory> findAllByUserId(int id);

    @Modifying
    @Query("update AirdropRequestHistory h set h.status = status where h.id = id")
    void updateStatus(@Param("id") int id, @Param("status") String status);

    @Modifying
    @Query("update AirdropRequestHistory h set h.successCount = ?2 where h.id = ?1")
    void updateSuccessNum(@Param("id") int id, @Param("successCount") int successCount);

}
