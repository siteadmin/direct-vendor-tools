package org.sitenv.directvendortools.web.repositories;

import java.util.List;

import org.sitenv.directvendortools.web.entities.DirectTransportTestingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterServiceRepository extends JpaRepository<DirectTransportTestingService, Long> {
	
	List<DirectTransportTestingService> findByUserEmailAddress(String emailAddress);
	
	DirectTransportTestingService findByDirectEmailAddress(String directSystemEmailAddress);
}
