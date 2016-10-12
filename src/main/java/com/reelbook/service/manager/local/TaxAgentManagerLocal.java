package com.reelbook.service.manager.local;

import com.reelbook.model.msc.TaxAgent;
import com.reelbook.model.msc.TaxAgentContact;
import com.reelbook.model.msc.TaxAgentPhone;

public interface TaxAgentManagerLocal<P extends TaxAgentPhone, C extends TaxAgentContact, T extends TaxAgent<P, C>> extends AgentManagerLocal<T>
{
}