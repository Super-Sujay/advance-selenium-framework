package org.automation.utilities;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;

/**
 * 
 * To handle HTTP Request Types.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 08/31/2020
 *
 */
public enum RequestType {

	OPTIONS(new HttpOptions()), GET(new HttpGet()), HEAD(new HttpHead()), PATCH(new HttpPatch()), POST(new HttpPost()),
	PUT(new HttpDelete()), TRACE(new HttpTrace());

	private final HttpRequestBase requestMethod;

	/**
	 * Create Request Type instance.
	 * 
	 * @param requestMethod request method type
	 */
	RequestType(HttpRequestBase requestMethod) {
		this.requestMethod = requestMethod;
	}

	/**
	 * Get the HTTP Request Method.
	 * 
	 * @return HTTP request method
	 */
	public HttpRequestBase getRequestMethod() {
		return requestMethod;
	}

}
