package com.reelbook.service.manager.local;

import com.reelbook.model.TaxAgent;
import com.reelbook.model.TaxAgentBank;
import com.reelbook.model.TaxAgentContact;
import com.reelbook.model.TaxAgentPhone;

public interface TaxAgentManagerLocal<P extends TaxAgentPhone, C extends TaxAgentContact, B extends TaxAgentBank, T extends TaxAgent<P, C, B>>
		extends AgentManagerLocal<T>
{
}