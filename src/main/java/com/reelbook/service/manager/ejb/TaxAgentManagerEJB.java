package com.reelbook.service.manager.ejb;

import com.reelbook.model.TaxAgent;
import com.reelbook.model.TaxAgentBank;
import com.reelbook.model.TaxAgentContact;
import com.reelbook.model.TaxAgentPhone;
import com.reelbook.service.manager.local.TaxAgentManagerLocal;

public abstract class TaxAgentManagerEJB<P extends TaxAgentPhone, C extends TaxAgentContact, B extends TaxAgentBank, T extends TaxAgent<P, C, B>>
		extends AgentManagerEJB<T> implements TaxAgentManagerLocal<P, C, B, T>
{
}