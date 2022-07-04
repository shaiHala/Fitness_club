package univ.master.mql.memberservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import univ.master.mql.memberservice.entities.Client;
@Repository
@RepositoryRestResource(collectionResourceRel = "client", path = "client")
public interface ClientRepository extends JpaRepository<Client, Long> {

	public Client findBymembershipnum(String number);
	public int deleteByMembershipnum(String membership);
	public int deleteAllById(Long id);
	@Query(value = "SELECT membershipnum, status FROM client WHERE membershipnum  = :membership",nativeQuery = true)
	public StatusOnly findStatusByMembershipnum( @Param("membership") String membership);
	public static interface StatusOnly{
		String getMembership();
		boolean getStatus();
	}


}


