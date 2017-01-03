package com.reelbook.service.manager.ejb;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import com.reelbook.core.exception.ManagerException;
import com.reelbook.core.service.manager.ejb.BasePersistenceManagerEJB;
import com.reelbook.core.service.util.PredicateBuilder;
import com.reelbook.core.service.util.QueryHintResult;
import com.reelbook.core.util.CompareUtil;
import com.reelbook.model.AudioVisual;
import com.reelbook.model.AudioVisual_;
import com.reelbook.model.DocumentType;
import com.reelbook.model.User;
import com.reelbook.model.Video;
import com.reelbook.service.manager.local.AudioVisualManagerLocal;
import com.reelbook.service.manager.local.UserManagerLocal;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AudioVisualManagerEJB extends BasePersistenceManagerEJB<AudioVisual> implements AudioVisualManagerLocal
{
	@EJB
	private UserManagerLocal userML;
	
	@Override
	public Class<AudioVisual> getModelClass()
	{
		return AudioVisual.class;
	}
	
	public AudioVisual getByUser(Long userID){
		AudioVisual response = null;
		try
		{
			final CriteriaBuilder cb = em.getCriteriaBuilder();
			final PredicateBuilder pb = new PredicateBuilder(cb);
			final CriteriaQuery<AudioVisual> cq = cb.createQuery(getModelClass());
			final Root<AudioVisual> audioVisual = cq.from(getModelClass());
			final Path<Long> pUserID = audioVisual.get(AudioVisual_.userID);

			// Expessions.
			cq.where(cb.equal(pUserID, userID));

			// Gets data.
			response = getUnique(cq);
		}
		catch (Throwable t)
		{
			throw new EJBException(t.getMessage());
		}
		return response;
	}

	@Override
	public Video addVideo(Long userID, Video video) throws ManagerException
	{
		AudioVisual audioVisual = getByUser(userID);
		if (CompareUtil.isEmpty(audioVisual))
		{
			audioVisual = save(new AudioVisual(userID));
		}
		
		audioVisual.setVideo(video);
		
		User user = userML.get(userID);
		user.setAudioVisualID(audioVisual.getID());
		return video;
	}
}