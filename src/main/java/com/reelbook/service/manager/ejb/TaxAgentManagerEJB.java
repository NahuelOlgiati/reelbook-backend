package com.reelbook.service.manager.ejb;

import com.reelbook.model.msc.TaxAgent;
import com.reelbook.model.msc.TaxAgentBank;
import com.reelbook.model.msc.TaxAgentContact;
import com.reelbook.model.msc.TaxAgentPhone;
import com.reelbook.service.manager.local.TaxAgentManagerLocal;

public abstract class TaxAgentManagerEJB<P extends TaxAgentPhone, C extends TaxAgentContact, B extends TaxAgentBank, T extends TaxAgent<P, C, B>>
		extends AgentManagerEJB<T> implements TaxAgentManagerLocal<P, C, B, T>
{
}