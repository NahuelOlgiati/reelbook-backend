package com.reelbook.service.manager.local;

import com.reelbook.model.TaxPayer;
import com.reelbook.model.TaxPayerContact;
import com.reelbook.model.TaxPayerPhone;

public interface BaseTaxPayerManagerLocal<T extends TaxPayer> extends TaxAgentManagerLocal<TaxPayerPhone, TaxPayerContact, T>
{
}