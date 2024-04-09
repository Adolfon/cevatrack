package com.ceva.cevatrack.repository;

import java.util.List;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ceva.cevatrack.domain.ClausedConsignmentDetail44;
import com.ceva.cevatrack.domain.Consignment44;
import com.ceva.cevatrack.domain.ConsignmentDetail44;
import com.ceva.cevatrack.domain.Driver44;
import com.ceva.cevatrack.domain.Vehicle44;
import com.ceva.cevatrack.wsmicrolise.getconsignment.ClausedConsignmentDetail;

@Repository(forEntity = Consignment44.class)
public abstract class P44_Claused_Details_Repository
    extends AbstractFullEntityRepository<ClausedConsignmentDetail44, String> {
  private static final Logger LOG = LoggerFactory.getLogger(P44_Claused_Details_Repository.class);

  public ClausedConsignmentDetail44 findByRef(String ref) {
    return findBy(ref);
  }

  public List<ClausedConsignmentDetail44> getClausedConsignmentDetails(String cdRef) {
    LOG.info("-- Claused consignment details consignments reference passed: " + cdRef);
    @SuppressWarnings("unchecked")
    List<ClausedConsignmentDetail44> ccdetails = entityManager()
        .createQuery(
            "SELECT ccd FROM ClausedConsignmentDetail44 ccd WHERE ccd.consignmentDetailRef = :p")
        .setParameter("p", cdRef).getResultList();
    if (ccdetails == null) {
      return null;
    }
    LOG.info("-- Claused consignment details found: " + ccdetails.size());//
    return ccdetails;
  }
}
