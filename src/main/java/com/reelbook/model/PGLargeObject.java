package com.reelbook.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import com.reelbook.core.exception.ValidationException;
import com.reelbook.core.model.BaseModel;

@Entity
@Table(name = "pg_largeobject")
@SqlResultSetMapping(
		name = "pg_largeobject_map",
		entities = {
				@EntityResult(
						entityClass = PGLargeObject.class,
						fields = {
								@FieldResult(name = "loID", column = "loID"),
								@FieldResult(name = "pageNo", column = "pageNo"),
								@FieldResult(name = "content", column = "content")})})
@SuppressWarnings("serial")
public class PGLargeObject extends BaseModel
{
	@Basic
	private Long loID;

	@Basic
	private Integer pageNo;

	@Id
	@Basic
	private byte[] data;

	public PGLargeObject()
	{
		this.loID = 0l;
		this.pageNo = null;
		this.data = null;
	}

	@Override
	public Long getID()
	{
		return loID;
	}

	@Override
	public void setID(Long id)
	{
		this.loID = id;
	}

	public Integer getPageNo()
	{
		return pageNo;
	}

	public void setPageNo(Integer pageNo)
	{
		this.pageNo = pageNo;
	}

	public byte[] getData()
	{
		return data;
	}

	public void setData(byte[] data)
	{
		this.data = data;
	}

	@Override
	public void valid() throws ValidationException
	{
	}
}