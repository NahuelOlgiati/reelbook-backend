package com.reelbook.core.service.manager.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.reelbook.core.exception.BaseException;
import com.reelbook.core.exception.ManagerException;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.BaseModel;
import com.reelbook.core.msg.enumeration.DatabaseMsgEnum;
import com.reelbook.core.service.manager.local.BasePersistenceManager;
import com.reelbook.core.service.manager.local.BaseValidationManager;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.service.msg.DBSMsgHandler;

public abstract class BasePersistenceManagerEJB<T extends BaseModel> extends BaseManagerEJB<T> implements BasePersistenceManager<T>
{
	protected BaseValidationManager vm;

	/**
	 */
	@PostConstruct
	private final void initValidationManager()
	{
		InitialContext initialContext;
		try
		{
			initialContext = new InitialContext();
			vm = (BaseValidationManager) initialContext.lookup("java:global/ejb/ValidationManagerEJB");
		}
		catch (NamingException e)
		{
		}
	}

	/**
	 */
	@Override
	public T save(final T model) throws ManagerException
	{
		try
		{
			if (model.isNew())
			{
				doBeforeValid(model);
				doValid(model);
				doBeforeAdd(model);
				doBeforeAddUpdate(model);
				doAdd(model);
				doAfterAdd(model);
			}
			else
			{
				doBeforeValid(model);
				doValid(model);
				doBeforeUpdate(model);
				doBeforeAddUpdate(model);
				doUpdate(model);
				doAfterUpdate(model);
			}
			doAfterAddUpdate(model);
			doAudit();
		}
		catch (BaseException b)
		{
			throw new ManagerException(b.getMessages());
		}
		catch (Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return model;
	}

	/**
	 */
	@Override
	public T delete(final Long modelID) throws ManagerException
	{
		T model = null;
		try
		{
			if (CompareUtil.isEmpty(model = get(modelID)))
			{
				throw new ValidationException(DBSMsgHandler.getMsg(DatabaseMsgEnum.RECORD_NOT_FOUND));
			}
			doBeforeDelete(model);
			doDelete(model);
			doAfterDelete(model);
			doAudit();
		}
		catch (BaseException b)
		{
			throw new ManagerException(b.getMessages());
		}
		catch (Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return model;
	}

	/**
	 */
	protected void doBeforeValid(final T model) throws BaseException
	{
	}

	/**
	 */
	private final void doValid(final T model) throws BaseException
	{
		if (CompareUtil.isEmpty(vm))
		{
			model.valid();
		}
		else
		{
			vm.valid(model);
		}
	}

	/**
	 */
	private final void doAdd(final T model) throws BaseException
	{
		em.persist(model);
	}

	/**
	 */
	private final T doUpdate(final T model) throws BaseException
	{
		return em.merge(model);
	}

	/**
	 */
	private final void doDelete(final T model) throws BaseException
	{
		em.remove(model);
	}

	/**
	 */
	protected void doBeforeAdd(final T model) throws BaseException
	{
	}

	/**
	 */
	protected void doBeforeUpdate(final T model) throws BaseException
	{
	}

	/**
	 */
	protected void doBeforeAddUpdate(final T model) throws BaseException
	{
	}

	/**
	 */
	protected void doBeforeDelete(final T model) throws BaseException
	{
	}

	/**
	 */
	protected void doAfterAdd(final T model) throws BaseException
	{
	}

	/**
	 */
	protected void doAfterUpdate(final T model) throws BaseException
	{
	}

	/**
	 */
	protected void doAfterAddUpdate(final T model) throws BaseException
	{
	}

	/**
	 */
	protected void doAfterDelete(final T model) throws BaseException
	{
	}
}