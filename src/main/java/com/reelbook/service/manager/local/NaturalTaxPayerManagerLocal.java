package com.reelbook.service.manager.local;

import java.util.List;

import javax.ejb.Local;

import com.reelbook.model.NaturalTaxPayer;
import com.reelbook.model.embeddable.Document;
import com.reelbook.server.ejb.BaseSimpleManager;
import com.reelbook.server.model.support.QueryHint;

@Local
public interface NaturalTaxPayerManagerLocal extends BaseTaxPayerManagerLocal<NaturalTaxPayer>, BaseSimpleManager<NaturalTaxPayer>
{
	/**
	 */
	public abstract List<NaturalTaxPayer> getNaturalTaxPayerByDocument(final Document document, final QueryHint queryHint);

	/**
	 */
	public abstract boolean getDuplicatedException(final Document document, final NaturalTaxPayer naturalTaxPayer);
}