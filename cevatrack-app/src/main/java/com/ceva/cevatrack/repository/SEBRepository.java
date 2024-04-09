package com.ceva.cevatrack.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import com.ceva.cevatrack.domain.SEBOrder;

@Repository(forEntity = SEBOrder.class)
public abstract class SEBRepository extends AbstractFullEntityRepository<SEBOrder, String> {
  private static final Logger LOG = LoggerFactory.getLogger(SEBRepository.class);

  public SEBOrder searchById(SEBOrder lb) {
    return (SEBOrder) findBy(lb);
  }

  public abstract Optional<SEBOrder> findByRef(String ref);

  public SEBOrder searchDropByRef(String ref) {
    LOG.info(" -- searchDropByRef method --");
    try {
      SEBOrder drop = (SEBOrder) entityManager()
          .createQuery("SELECT d FROM SEBOrder d WHERE d.ml_order_ref = :p").setParameter("p", ref)
          .getSingleResult();
      LOG.info(" -- Result of the query: " + drop);
      return drop;
    } catch (Exception e) {
      LOG.error(
          " -- Error executing drop record search with ref: " + ref + " ,cause: " + e.getMessage(),
          e);
      return null;
    }
  }

  /*
   * ^ Search journeys for a given date, hours parameter is not yet implemented
   */
  public List<SEBOrder> searchJourneysByDateHours(Date date, int hours) {
    LOG.info(" -- searchJourneysByDateHours method --");
    try {
      @SuppressWarnings("unchecked")
      List<SEBOrder> journeys = (List<SEBOrder>) entityManager()
          .createQuery(
              "SELECT j FROM SEBOrder j WHERE j.seb_date = :p ORDER BY j.route_id, j.drop_number")
          .setParameter("p", date).getResultList();
      LOG.info(" -- Result of the query: " + journeys.size() + " ,for date: " + date);
      journeys.forEach(c -> LOG.info(" \n Journey: " + c));
      return journeys;
    } catch (Exception e) {
      LOG.error(
          " -- Error executing journeys search with date: " + date + " ,cause: " + e.getMessage(),
          e);
      return null;
    }
  }

}
