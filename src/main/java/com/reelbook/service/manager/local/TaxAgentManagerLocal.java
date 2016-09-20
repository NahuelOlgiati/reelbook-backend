package com.reelbook.service.manager.local;

import com.reelbook.model.msc.TaxAgent;
import com.reelbook.model.msc.TaxAgentBank;
import com.reelbook.model.msc.TaxAgentContact;
import com.reelbook.model.msc.TaxAgentPhone;

public interface TaxAgentManagerLocal<P extends TaxAgentPhone, C extends TaxAgentContact, B extends TaxAgentBank, T extends TaxAgent<P, C, B>>
		extends AgentManagerLocal<T>
{
}