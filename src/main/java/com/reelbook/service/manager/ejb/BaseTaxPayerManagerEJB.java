package com.reelbook.service.manager.ejb;

import com.reelbook.model.TaxPayer;
import com.reelbook.model.TaxPayerContact;
import com.reelbook.model.TaxPayerPhone;
import com.reelbook.service.manager.local.BaseTaxPayerManagerLocal;


public abstract class BaseTaxPayerManagerEJB<T extends TaxPayer> extends TaxAgentManagerEJB<TaxPayerPhone, TaxPayerContact, T>
		implements BaseTaxPayerManagerLocal<T>
{
}