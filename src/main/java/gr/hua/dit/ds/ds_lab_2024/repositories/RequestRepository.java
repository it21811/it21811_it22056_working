package gr.hua.dit.ds.ds_lab_2024.repositories;

import gr.hua.dit.ds.ds_lab_2024.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    @Query("SELECT r.property.user.id FROM Request r WHERE r.requestid = :requestId")
    Integer findOwnerIdByRequestId(@Param("requestId") Integer requestId);

}
