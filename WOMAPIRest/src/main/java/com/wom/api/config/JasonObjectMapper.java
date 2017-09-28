package com.wom.api.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JasonObjectMapper extends ObjectMapper{

	private static final long serialVersionUID = 1L;

	public JasonObjectMapper() {
	    this.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
	            .setVisibility(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.ANY)
	            .setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
	            .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
	            .setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);

	    this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	    }
}
