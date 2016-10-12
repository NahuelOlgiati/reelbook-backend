package com.reelbook.service.manager.ejb;

import com.reelbook.model.msc.TaxAgent;
import com.reelbook.model.msc.TaxAgentContact;
import com.reelbook.model.msc.TaxAgentPhone;
import com.reelbook.service.manager.local.TaxAgentManagerLocal;

public abstract class TaxAgentManagerEJB<P extends TaxAgentPhone, C extends TaxAgentContact, T extends TaxAgent<P, C>> extends AgentManagerEJB<T>
		implements TaxAgentManagerLocal<P, C, T>
{
}