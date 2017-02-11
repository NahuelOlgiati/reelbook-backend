package com.reelbook.rest.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.reelbook.rest.annotation.GsonIgnore;

public class AnnotationExclusionStrategy implements ExclusionStrategy{

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		return f.getAnnotation(GsonIgnore.class) != null;
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

}
