package com.reelbook.service.manager.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.reelbook.core.exception.BaseException;
import com.reelbook.core.exception.ManagerException;
import com.reelbook.core.model.support.QueryHint;
import com.reelbook.core.service.manager.ejb.BasePersistenceManagerEJB;
import com.reelbook.core.service.util.PredicateBuilder;
import com.reelbook.core.service.util.QueryHintResult;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.DocumentType;
import com.reelbook.model.DocumentType_;
import com.reelbook.model.Profile;
import com.reelbook.model.SystemAgent;
import com.reelbook.model.SystemAgent_;
import com.reelbook.model.User;
import com.reelbook.model.User_;
import com.reelbook.model.embeddable.Document;
import com.reelbook.model.embeddable.Document_;
import com.reelbook.service.manager.local.BaseUserManagerLocal;

public abstract class BaseUserManagerEJB<T extends User> extends BasePersistenceManagerEJB<T>
		implements BaseUserManagerLocal<T> {
	// @EJB
	// private ProfileManagerLocal profileML;
	// @EJB
	// private UserManagerLocal userML;

	/**
	 */
	@Override
	protected void doBeforeValid(T model) throws BaseException {
		super.doBeforeValid(model);

		// final Profile basicProfile = profileML.getBasic();
		// if (CompareUtil.isEmpty(basicProfile))
		// {
		// throw new
		// ManagerException(DBSMsgHandler.getMsg(BaseUserManagerEJB.class,
		// "emptyBasicProfile"));
		// }
		//
		// final List<Profile> profiles = model.getProfiles();
		// if (!profiles.contains(basicProfile))
		// {
		// profiles.add(basicProfile);
		// }
	}

	/**
	 */
	@Override
	protected void doBeforeAdd(T model) throws BaseException {
		super.doBeforeAdd(model);

		// userML.validUserNameDuplication(model);
	}

	/**
	 */
	@Override
	protected void doBeforeDelete(T model) throws BaseException {
		super.doBeforeDelete(model);

		if (model.isUserNameReserved()) {
			// throw new
			// ValidationException(DBSMsgHandler.getMsg(BaseUserManagerEJB.class,
			// "canNotDeleteUserName"));
		}
	}

	/**
	 */
	@Override
	public T getCurrent() {
		T model = null;
		try {
			final CriteriaBuilder cb = em.getCriteriaBuilder();
			final PredicateBuilder pb = new PredicateBuilder(cb);
			final CriteriaQuery<T> cq = cb.createQuery(getModelClass());
			final Root<T> user = cq.from(getModelClass());
			final Path<String> userName = user.get(User_.userName);

			// Expressions.
			cq.where(pb.equal(userName, getPrincipal().getName()));

			// Gets data.
			model = getUnique(cq);
			if (!CompareUtil.isEmpty(model)) {
				model.initLazyElements();
			}
		} catch (Throwable t) {
			throw new EJBException(t.getMessage());
		}
		return model;
	}

	/**
	 */
	@Override
	public T get(final String userName) {
		T model = null;
		try {
			final CriteriaBuilder cb = em.getCriteriaBuilder();
			final PredicateBuilder pb = new PredicateBuilder(cb);
			final CriteriaQuery<T> cq = cb.createQuery(getModelClass());
			final Root<T> user = cq.from(getModelClass());
			final Path<String> genericUserName = user.get(User_.userName);

			// Expressions.
			cq.where(pb.equal(genericUserName, userName));

			// Gets data.
			model = getUnique(cq);
		} catch (Throwable t) {
			throw new EJBException(t.getMessage());
		}
		return model;
	}

	/**
	 */
	@Override
	public T getFULL(final String userName) {
		T model = get(userName);
		if (!CompareUtil.isEmpty(model)) {
			model.initLazyElements();
		}
		return model;
	}

	/**
	 */
	@Override
	public T get(final Document d) {
		T model = null;
		try {
			final CriteriaBuilder cb = em.getCriteriaBuilder();
			final PredicateBuilder pb = new PredicateBuilder(cb);
			final CriteriaQuery<T> cq = cb.createQuery(getModelClass());
			final Root<T> user = cq.from(getModelClass());
			final Path<SystemAgent> systemAgent = user.get(User_.systemAgent);
			final Path<Document> document = systemAgent.get("document");
			final Path<DocumentType> documentType = document.get(Document_.documentType);
			final Path<Long> documentTypeID = documentType.get(DocumentType_.documentTypeID);
			final Path<String> documentNumber = document.get(Document_.documentNumber);

			// Expressions.
			cq.where(cb.and(pb.equal(documentTypeID, d.getDocumentType().getID()),
					pb.equal(documentNumber, d.getDocumentNumber())));

			// Gets data.
			model = getUnique(cq);
		} catch (Throwable t) {
			throw new EJBException(t.getMessage());
		}
		return model;
	}

	/**
	 */
	@Override
	public List<T> getList(final Profile profile) {
		List<T> userList = null;
		try {
			final CriteriaBuilder cb = em.getCriteriaBuilder();
			final CriteriaQuery<T> cq = cb.createQuery(getModelClass());
			final Root<T> user = cq.from(getModelClass());
			final Join<T, Profile> pProfile = user.join(User_.profiles);

			// Expressions.
			cq.where(cb.equal(pProfile, profile));

			// Gets data.
			userList = getList(cq);
		} catch (Throwable t) {
			throw new EJBException(t.getMessage());
		}
		return userList;
	}

	/**
	 */
	@Override
	public QueryHintResult<T> getQueryHintResult(final Boolean excludeReserved, final Boolean active,
			final String description, final QueryHint queryHint) {
		QueryHintResult<T> queryHintResult = null;
		try {
			final CriteriaBuilder cb = em.getCriteriaBuilder();
			final PredicateBuilder pb = new PredicateBuilder(cb);
			final CriteriaQuery<T> cq = cb.createQuery(getModelClass());
			final Root<T> user = cq.from(getModelClass());
			final Path<Boolean> userActive = user.get(User_.active);
			final Path<String> userName = user.get(User_.userName);
			final Path<SystemAgent> systemAgent = user.get(User_.systemAgent);
			final Path<String> firstName = systemAgent.get(SystemAgent_.firstName);
			final Path<String> lastName = systemAgent.get(SystemAgent_.lastName);
			final Path<Document> document = systemAgent.get("document");
			final Path<String> documentNumber = document.get(Document_.documentNumber);

			// Expressions.
			final List<Predicate> predicateList = new ArrayList<Predicate>();
			predicateList.add(pb.equal(userActive, active));
			predicateList.add(cb.or(pb.like(userName, description), pb.like(lastName, description),
					pb.like(firstName, description), pb.like(documentNumber, description)));
			if (excludeReserved) {
				// predicateList.add(cb.not(pb.in(userName,
				// UserReservedEnum.getNames())));
			}
			cq.where(cb.and(predicateList.toArray(new Predicate[0])));

			// Order
			final List<Order> orderList = new ArrayList<Order>();
			orderList.add(cb.asc(lastName));
			orderList.add(cb.asc(firstName));
			cq.orderBy(orderList);

			// Gets data.
			queryHintResult = getQueryHintResult(cq, queryHint);
		} catch (Throwable t) {
			throw new EJBException(t.getMessage());
		}
		return queryHintResult;
	}

	/**
	 */
	@Override
	public QueryHintResult<T> getQueryHintResult(final Boolean active, final String description,
			final QueryHint queryHint) {
		return getQueryHintResult(Boolean.FALSE, active, description, queryHint);
	}

	/**
	 */
	@Override
	public QueryHintResult<T> getQueryHintResult(final String description, final QueryHint queryHint) {
		return getQueryHintResult(Boolean.FALSE, null, description, queryHint);
	}

	/**
	 * Use only on leaves
	 */
	@Override
	public void validSystemAgentDuplication(final Document d) throws ManagerException {
		final T user = get(d);
		if (!CompareUtil.isEmpty(user)) {
			// throw new
			// ManagerException(DBSMsgHandler.getMsg(BaseUserManagerEJB.class,
			// "systemAgentDuplicatedException"));
		}
	}
}