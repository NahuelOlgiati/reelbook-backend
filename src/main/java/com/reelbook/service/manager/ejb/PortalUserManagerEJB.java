package com.reelbook.service.manager.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.reelbook.core.exception.BaseException;
import com.reelbook.core.exception.ManagerException;
import com.reelbook.core.service.util.PredicateBuilder;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.PortalUser;
import com.reelbook.model.TaxPayer;
import com.reelbook.model.embeddable.Document;
import com.reelbook.model.enumeration.TaxPayerTypeEnum;
import com.reelbook.service.manager.local.PortalUserManagerLocal;


@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class PortalUserManagerEJB extends BaseUserManagerEJB<PortalUser> implements PortalUserManagerLocal
{
//	@EJB
//	private LegalTaxPayerManagerLocal legalTaxPayerML;
//	@EJB
//	private NaturalTaxPayerManagerLocal naturalTaxPayerML;
//	@EJB
//	private MailManagerLocal mailML;
//	@EJB
//	private OsirisTaxPortalParameterManagerLocal parameterML;
//	@EJB
//	private SystemAgentManagerLocal systemAgentML;
//	@EJB
//	private ProfileManagerLocal profileML;

	/**
	 */
	@Override
	public Class<PortalUser> getModelClass()
	{
		return PortalUser.class;
	}

	/**
	 */
	@Override
	protected void doBeforeAdd(PortalUser model) throws BaseException
	{
		super.doBeforeAdd(model);
		validSystemAgentDuplication(model.getSystemAgent().getDocument());
	}

	/**
	 */
	@Override
	public PortalUser register(PortalUser portalUser, final Document document, final TaxPayerTypeEnum taxPayerType) throws ManagerException
	{
//		checkPortalProfile(portalUser);
//		checkPortalUserTaxPayer(portalUser, document, taxPayerType);
//		mailML.emailAddressValid(portalUser.getEmailAddress().trim());
//
//		SystemAgent sa = systemAgentML.getOrCreate(document, getFirstName(portalUser.getTaxPayer(), taxPayerType),
//				getLastName(portalUser.getTaxPayer(), taxPayerType));
//		portalUser.setSystemAgent(sa);
//
//		String password = RandomStringUtils.randomAlphanumeric(8);
//		portalUser.setUserPassword(password);
//		try
//		{
//			PortalUser pU = getPortalUser(document, taxPayerType);
//			if (pU != null && !pU.getValidated())
//			{
//				pU.setUserPassword(password);
//				pU.setUserName(portalUser.getUserName());
//				pU.setEmailAddress(portalUser.getEmailAddress());
//				portalUser = pU;
//			}
//
//			save(portalUser);
//			em.flush();
//
//			List<String> toList = new ArrayList<String>();
//			toList.add(portalUser.getEmailAddress());
//
//			String fullName = portalUser.getTaxPayer().getFullName();
//			String userName = portalUser.getUserName();
//			String mailTemplate = parameterML.getParameter().getRegisterMailTemplate();
//			String subject = parameterML.getParameter().getRegisterMailSubject();
//			String mailText = "";
//			MailQP qp = new MailQP(subject, toList, mailText, fullName, userName, password, getLoginURL(), mailTemplate);
//			mailML.sendMail(qp);
//
//			JSFUtil.info(DBSMsgHandler.getMsg(PortalUserManagerEJB.class, "emailSent"));
//		}
//		catch (BaseException v)
//		{
//			throw new ManagerException(v.getMessages());
//		}
//		catch (Throwable t)
//		{
//			throw new ManagerException(t.getMessage());
//		}

		return portalUser;
	}

	/**
	 */
	@Override
	public void changeMailAddress(PortalUser portalUser, final String newEmailAddress) throws ManagerException
	{
//		mailML.emailAddressValid(newEmailAddress.trim());
//
//		SystemAgent sa = systemAgentML.getOrCreate(portalUser.getSystemAgent().getDocument(), portalUser.getSystemAgent().getFirstName(), portalUser
//				.getSystemAgent().getLastName());
//		portalUser.setSystemAgent(sa);
//
//		String password = RandomStringUtils.randomAlphanumeric(8);
//		portalUser.setUserPassword(password);
//		try
//		{
//			PortalUser full = getFULL(portalUser.getID());
//			full.setUserPassword(password);
//			full.setEmailAddress(newEmailAddress);
//			save(full);
//			em.flush();
//
//			List<String> toList = new ArrayList<String>();
//			toList.add(full.getEmailAddress());
//			String fullName = full.getTaxPayer().getFullName();
//			String userName = full.getUserName();
//			String mailTemplate = parameterML.getParameter().getForgotMailTemplate();
//			String subject = parameterML.getParameter().getForgotMailSubject();
//			String mailText = "";
//			MailQP qp = new MailQP(subject, toList, mailText, fullName, userName, password, getLoginURL(), mailTemplate);
//			mailML.sendMail(qp);
//
//			JSFUtil.info(DBSMsgHandler.getMsg(PortalUserManagerEJB.class, "emailSent"));
//		}
//		catch (BaseException v)
//		{
//			throw new ManagerException(v.getMessages());
//		}
//		catch (Throwable t)
//		{
//			throw new ManagerException(t.getMessage());
//		}
	}

	/**
	 */
	private void checkPortalProfile(final PortalUser portalUser) throws ManagerException
	{
//		List<Profile> profiles = portalUser.getProfiles();
//		Profile portalProfile = profileML.getPortal();
//
//		if (CompareUtil.isEmpty(portalProfile))
//		{
//			throw new ManagerException(DBSMsgHandler.getMsg(PortalUserManagerEJB.class, "emptyPortalProfile"));
//		}
//
//		profiles.add(portalProfile);
	}

	/**
	 */
	private void checkPortalUserTaxPayer(final PortalUser portalUser, final Document document, final TaxPayerTypeEnum taxPayerType)
			throws ManagerException
	{
//		TaxPayer taxPayer = null;
//		taxPayer = taxPayerType.equals(TaxPayerTypeEnum.NATURAL) ? naturalTaxPayerML.get(document) : legalTaxPayerML.get(document);
//
//		if (CompareUtil.isEmpty(taxPayer))
//		{
//			throw new ManagerException(DBSMsgHandler.getMsg(PortalUserManagerEJB.class, "taxPayerInvalid"));
//		}
//
//		if (duplicatedValidatedTaxPayer(portalUser, taxPayer))
//		{
//			throw new ManagerException(DBSMsgHandler.getMsg(getClass(), "duplicatedTaxPayer"));
//		}
//
//		portalUser.setTaxPayer(taxPayer);
	}

	/**
	 */
	private Boolean duplicatedValidatedTaxPayer(PortalUser model, TaxPayer taxPayer) throws ManagerException, EJBException
	{
		Boolean result = false;
		try
		{
			final CriteriaBuilder cb = em.getCriteriaBuilder();
			final PredicateBuilder pb = new PredicateBuilder(cb);
			final CriteriaQuery<PortalUser> cq = cb.createQuery(getModelClass());
			final Root<PortalUser> portalUser = cq.from(getModelClass());
//			final Path<TaxPayer> tp = portalUser.get(PortalUser_.taxPayer);

			// Expressions.
//			cq.where(pb.equal(tp, taxPayer));

			// Gets data.
			PortalUser pU = getUnique(cq);

			if (pU != null)
			{
				if (pU.getValidated() == null)
				{
					result = true;
				}
				else
				{
					if (pU.getValidated())
					{
						result = true;
					}
				}
			}
		}
		catch (Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return result;
	}

	/**
	 */
	private PortalUser getPortalUser(final Document document, final TaxPayerTypeEnum taxPayerType) throws ManagerException, EJBException
	{
		PortalUser result = null;
//		try
//		{
//			TaxPayer taxPayer = null;
//			taxPayer = taxPayerType.equals(TaxPayerTypeEnum.NATURAL) ? naturalTaxPayerML.get(document) : legalTaxPayerML.get(document);
//
//			final CriteriaBuilder cb = em.getCriteriaBuilder();
//			final PredicateBuilder pb = new PredicateBuilder(cb);
//			final CriteriaQuery<PortalUser> cq = cb.createQuery(getModelClass());
//			final Root<PortalUser> portalUser = cq.from(getModelClass());
//			final Path<TaxPayer> tp = portalUser.get(PortalUser_.taxPayer);
//
//			// Expressions.
//			cq.where(pb.equal(tp, taxPayer));
//
//			// Gets data.
//			List<PortalUser> modelList = getList(cq);
//
//			if (modelList.size() > 0)
//			{
//				result = modelList.get(0);
//			}
//		}
//		catch (Throwable t)
//		{
//			throw new EJBException(t.getMessage());
//		}
		return result;
	}

	/**
	 */
	private <T extends TaxPayer> String getFirstName(T taxPayer, TaxPayerTypeEnum taxPayerType)
	{
		String firstName = "";
//		firstName = taxPayerType.equals(TaxPayerTypeEnum.NATURAL) ? (((NaturalTaxPayer) taxPayer).getFirstName()) : (((LegalTaxPayer) taxPayer)
//				.getFancyName());
		return firstName;
	}

	/**
	 */
	private <T extends TaxPayer> String getLastName(T taxPayer, TaxPayerTypeEnum taxPayerType)
	{
		String firstName = "";
//		firstName = taxPayerType.equals(TaxPayerTypeEnum.NATURAL) ? (((NaturalTaxPayer) taxPayer).getLastName()) : (((LegalTaxPayer) taxPayer)
//				.getCorporateName());
		return firstName;
	}

	/**
	 */
	@Override
	public void sendPassword(final Document document) throws ManagerException
	{
		PortalUser portalUser = get(document);

		if (CompareUtil.isEmpty(portalUser))
		{
//			throw new ManagerException(DBSMsgHandler.getMsg(PortalUserManagerEJB.class, "portalUserInvalid"));
		}

		List<String> toList = new ArrayList<String>();
		toList.add(portalUser.getEmailAddress());

//		String password = RandomStringUtils.randomAlphanumeric(8);
//		portalUser.setUserPassword(password);
//		save(portalUser);
//		em.flush();
//
//		String fullName = portalUser.getTaxPayer().getFullName();
//		String userName = portalUser.getUserName();
//		String mailTemplate = parameterML.getParameter().getForgotMailTemplate();
//		String subject = parameterML.getParameter().getForgotMailSubject();
//		String mailText = "";
//		MailQP qp = new MailQP(subject, toList, mailText, fullName, userName, password, getLoginURL(), mailTemplate);
//		mailML.sendMail(qp);
//
//		JSFUtil.info(DBSMsgHandler.getMsg(PortalUserManagerEJB.class, "passwordSent"));
	}

	/**
	 */
	private final String getLoginURL()
	{
//		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		String url = req.getRequestURL().toString();
//		String uri = req.getContextPath().toString();
//		return url.substring(0, url.indexOf(uri) + uri.length()) + parameterML.getParameter().getHomeURI();
		return "";
	}
}