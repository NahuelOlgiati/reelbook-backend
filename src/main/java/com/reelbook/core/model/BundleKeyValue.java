package com.reelbook.core.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

@Entity
@Table(name = "adonis_common_bundlekeyvalue")
@SqlResultSetMapping(
		name = "bundleKeyValueMap",
		entities = {
				@EntityResult(
						entityClass = BundleKeyValue.class,
						fields = {@FieldResult(name = "key", column = "key"), @FieldResult(name = "value", column = "value")})})
@SuppressWarnings("serial")
public final class BundleKeyValue implements Serializable
{
	@Id
	private String key;

	@Column(length = 500)
	private String value;

	/**
	 */
	public BundleKeyValue()
	{
	}

	/**
	 */
	public final String getKey()
	{
		return key;
	}

	/**
	 */
	public final void setKey(String key)
	{
		this.key = key;
	}

	/**
	 */
	public final String getValue()
	{
		return value;
	}

	/**
	 */
	public final void setValue(String value)
	{
		this.value = value;
	}
}