package tiberiu.assignment2.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import tiberiu.assignment2.model.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);



}
