package org.automation.utilities;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;

public enum RequestType {
	
	OPTIONS(new HttpOptions()),
	GET(new HttpGet()),
	HEAD(new HttpHead()),
	PATCH(new HttpPatch()),
	POST(new HttpPost()),
	PUT(new HttpDelete()),
	TRACE(new HttpTrace());
	
	private final HttpRequestBase requestMethod;
	
	RequestType(HttpRequestBase requestMethod) {
		this.requestMethod = requestMethod;
	}
	
	public HttpRequestBase getRequestMethod() {
		return requestMethod;
	}

}
