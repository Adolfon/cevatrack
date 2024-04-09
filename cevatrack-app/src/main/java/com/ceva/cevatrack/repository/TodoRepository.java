package com.ceva.cevatrack.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ceva.framework.enums.SortOrder;
import com.ceva.framework.vo.PaginatedResultVO;
import com.ceva.framework.vo.SearchCriteriaVO;
import com.ceva.framework.vo.SortByVO;
import com.ceva.cevatrack.domain.Todo;
import com.ceva.cevatrack.domain.Todo_;
import com.ceva.cevatrack.vo.TodoVO;

/**
 * TodoRepository.
 *
 * @author mohammad.rasheed
 *
 */
@Repository(forEntity = Todo.class)
public abstract class TodoRepository extends AbstractFullEntityRepository<Todo, Long> {

  private static final Logger LOGGER = LoggerFactory.getLogger(TodoRepository.class);

  /**
   * this function used for getting pagination result based on criteria.
   *
   * @param criteria {@link SearchCriteriaVo}
   * @return PaginatedResult
   */
  private PaginatedResultVO<Todo> findAll(SearchCriteriaVO<TodoVO> criteria) {

    CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
    List<Order> orderList = new ArrayList<Order>();

    CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
    Root<Todo> root = countQuery.from(Todo.class);
    countQuery.select(criteriaBuilder.count(root));

    // Sorting
    List<SortByVO> sortList = criteria.getSortBy();
    if (sortList != null && !sortList.isEmpty()) {
      for (SortByVO sortItem : sortList) {
        String sortColumnName = sortItem.getColumnName();
        /** check if the column name is matching with the database column name; */
        try {
          Todo.class.getDeclaredField(sortColumnName);
        } catch (NoSuchFieldException e) {
          LOGGER.error("No cloumn found with the name: " + sortColumnName, e);
          return null;
        }
        if (SortOrder.ASCENDING == sortItem.getSortOrder()) {
          sortAscByClmNm(sortColumnName, orderList);
        } else if (SortOrder.DESCENDING == sortItem.getSortOrder()) {
          sortDescByClmNm(sortColumnName, orderList);
        }
      }
    }

    Long count;
    count = entityManager().createQuery(countQuery).getSingleResult();

    // Getting Todo list
    CriteriaQuery<Todo> criteriaQuery = criteriaBuilder.createQuery(Todo.class);
    criteriaQuery.from(Todo.class);
    criteriaQuery.orderBy(orderList);

    // Pagination
    TypedQuery<Todo> query = entityManager().createQuery(criteriaQuery);
    query.setFirstResult(criteria.getPageNumber() * criteria.getPageSize());
    query.setMaxResults(criteria.getPageSize());

    return new PaginatedResultVO<Todo>(query.getResultList(), count.intValue());
  }

  /**
   * To search todo's with pagination.
   *
   * @param searchCriteria todo search attributes
   * @return paginated list of todos
   */
  public PaginatedResultVO<Todo> searchTodosWithPagination(
      SearchCriteriaVO<TodoVO> searchCriteria) {
    PaginatedResultVO<Todo> result = null;
    TodoVO todo = searchCriteria.getFilter();
    if (todo != null) {
      CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
      CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
      Root<Todo> root = countQuery.from(Todo.class);
      countQuery.select(criteriaBuilder.count(root));

      List<Predicate> predicates = this.buildWhereClausePredicates(todo);
      Predicate[] predicatesArray = this.getPredicatesArray(predicates);

      countQuery.where(predicatesArray);
      final Long count = entityManager().createQuery(countQuery).getSingleResult();

      // Getting the List of Todos
      CriteriaQuery<Todo> criteriaQuery = this.buildCriteriaQuery(Todo.class);
      criteriaQuery.where(predicatesArray);
      criteriaQuery.orderBy(this.getOrderList(searchCriteria));

      // Pagination
      TypedQuery<Todo> query = entityManager().createQuery(criteriaQuery);
      query.setFirstResult(searchCriteria.getPageNumber() * searchCriteria.getPageSize());
      query.setMaxResults(searchCriteria.getPageSize());
      result = new PaginatedResultVO<Todo>(query.getResultList(), count.intValue());
      return result;
    } else {
      result = findAll(searchCriteria);
    }

    return result;
  }

  private CriteriaQuery buildCriteriaQuery(Class queryClass) {
    CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
    CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(queryClass);
    criteriaQuery.from(Todo.class);
    return criteriaQuery;
  }

  private Predicate[] getPredicatesArray(List<Predicate> predicates) {
    return predicates.toArray(new Predicate[predicates.size()]);
  }

  private List<Predicate> buildWhereClausePredicates(TodoVO todo) {
    return this.buildWhereClausePredicates(todo.getTitle(), todo.getDescription(),
        todo.getPriority(), todo.getStartdate(), todo.getEnddate(), todo.getOwner());
  }

  private List<Predicate> buildWhereClausePredicates(String title, String description,
      Integer priority, Date startDate, Date endDate, String owner) {
    List<Predicate> predicates = new ArrayList<Predicate>();
    if (StringUtils.isNotEmpty(title)) {
      predicates.add(this.buildPredicateForAttributeStr(Todo_.title, title));
    }
    if (StringUtils.isNotEmpty(description)) {
      predicates.add(this.buildPredicateForAttributeStr(Todo_.description, description));
    }
    if (priority != null && priority > 0 && priority <= 3) {
      predicates.add(this.buildPredicateForAttributeInt(Todo_.priority, priority));
    }
    if (startDate != null && endDate != null) {
      predicates.add(this.buildPredicateForBetweenDates(Todo_.duedate, startDate, endDate));
    }

    if (StringUtils.isNotEmpty(owner)) {
      predicates.add(this.buildPredicateForAttributeStr(Todo_.owner, owner));
    }
    return predicates;
  }

  private Predicate buildPredicateForAttributeStr(SingularAttribute attr, String valueToCompare) {
    CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
    Root<Todo> root = this.getRoot(Todo.class);

    return criteriaBuilder.like(criteriaBuilder.lower(root.get(attr)),
        valueToCompare.toLowerCase() + "%");
  }

  private Predicate buildPredicateForAttributeInt(SingularAttribute attr, Integer priority) {

    CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
    Root<Todo> root = this.getRoot(Todo.class);
    return criteriaBuilder.equal(root.get(attr), priority);
  }

  private Predicate buildPredicateForBetweenDates(SingularAttribute attr, Date startdate,
      Date enddate) {
    CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
    Root<Todo> root = this.getRoot(Todo.class);
    return criteriaBuilder.between(root.get(attr), startdate, enddate);
  }

  private Root<Todo> getRoot(Class queryClass) {
    CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
    CriteriaQuery<Todo> criteriaQuery = criteriaBuilder.createQuery(queryClass);
    return criteriaQuery.from(Todo.class);
  }

  private List<Order> getOrderList(SearchCriteriaVO<TodoVO> searchCriteria) {
    List<Order> orderList = new ArrayList<Order>();

    // Sorting
    List<SortByVO> sortList = searchCriteria.getSortBy();
    if (sortList != null && !sortList.isEmpty()) {
      for (SortByVO sortItem : sortList) {
        String sortColumnName = sortItem.getColumnName();
        /** check if the column name is matching with the database column name; */
        try {
          Todo.class.getDeclaredField(sortColumnName);
        } catch (NoSuchFieldException e) {
          LOGGER.error("No cloumn found with the name: " + sortColumnName, e);
          return null;
        }
        if (SortOrder.ASCENDING == sortItem.getSortOrder()) {
          orderList = sortAscByClmNm(sortColumnName, orderList);

        } else if (SortOrder.DESCENDING == sortItem.getSortOrder()) {
          orderList = sortDescByClmNm(sortColumnName, orderList);

        }
      }
    }

    return orderList;
  }

  private List<Order> sortAscByClmNm(String sortColumnName, List<Order> orderList) {

    CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
    CriteriaQuery<Long> countQuery = this.buildCriteriaQuery(Long.class);
    Root<Todo> root = countQuery.from(Todo.class);
    switch (sortColumnName) {
      case "id":
        orderList.add(criteriaBuilder.asc(root.get(Todo_.id)));
        break;
      case "title":
        orderList.add(criteriaBuilder.asc(root.get(Todo_.title)));
        break;
      case "priority":
        orderList.add(criteriaBuilder.asc(root.get(Todo_.priority)));
        break;
      case "duedate":
        orderList.add(criteriaBuilder.asc(root.get(Todo_.duedate)));
        break;
      case "owner":
        orderList.add(criteriaBuilder.asc(root.get(Todo_.owner)));
        break;
      default:
        orderList.add(criteriaBuilder.asc(root.get(Todo_.id)));
        break;
    }

    return orderList;
  }

  private List<Order> sortDescByClmNm(String sortColumnName, List<Order> orderList) {
    CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
    CriteriaQuery<Long> countQuery = this.buildCriteriaQuery(Long.class);
    Root<Todo> root = countQuery.from(Todo.class);
    switch (sortColumnName) {
      case "id":
        orderList.add(criteriaBuilder.desc(root.get(Todo_.id)));
        break;
      case "title":
        orderList.add(criteriaBuilder.desc(root.get(Todo_.title)));
        break;
      case "priority":
        orderList.add(criteriaBuilder.desc(root.get(Todo_.priority)));
        break;
      case "duedate":
        orderList.add(criteriaBuilder.desc(root.get(Todo_.duedate)));
        break;
      case "owner":
        orderList.add(criteriaBuilder.desc(root.get(Todo_.owner)));
        break;
      default:
        orderList.add(criteriaBuilder.desc(root.get(Todo_.id)));
        break;
    }
    return orderList;
  }
}
