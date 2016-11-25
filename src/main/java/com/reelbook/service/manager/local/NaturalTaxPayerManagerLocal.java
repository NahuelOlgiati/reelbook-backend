package com.reelbook.service.manager.local;

import java.util.List;
import javax.ejb.Local;
import com.reelbook.core.model.support.QueryHint;
import com.reelbook.core.service.manager.local.BaseSimpleManager;
import com.reelbook.model.NaturalTaxPayer;
import com.reelbook.model.embeddable.Document;

@Local
public interface NaturalTaxPayerManagerLocal extends BaseTaxPayerManagerLocal<NaturalTaxPayer>, BaseSimpleManager<NaturalTaxPayer>
{

	public abstract List<NaturalTaxPayer> getNaturalTaxPayerByDocument(final Document document, final QueryHint queryHint);

	public abstract boolean getDuplicatedException(final Document document, final NaturalTaxPayer naturalTaxPayer);
}