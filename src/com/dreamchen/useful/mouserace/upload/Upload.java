package com.dreamchen.useful.mouserace.upload;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.dreamchen.useful.mouserace.attach.AttachInfo;
import com.dreamchen.useful.mouserace.attach.AttachInfoCollection;


public class Upload {
	
	/**
	 * 单个附件上传
	 * @param attachInfo
	 * @param url
	 * @return 返回结果，是服务器决定
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String singleUpload(AttachInfo attachInfo,String url) throws ClientProtocolException, IOException
	{
		String result = null;

		HttpClient httpClient = new DefaultHttpClient();
		HttpContext httpContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(url);

			CustomMultipartEntity multipartContent = new CustomMultipartEntity(null);

			FileBody fileBody = new FileBody(new File(attachInfo.getUri()));
			multipartContent.addPart("file", fileBody);

			httpPost.setEntity(multipartContent);
			HttpResponse response = httpClient.execute(httpPost, httpContext);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				HttpEntity outputEntity = response.getEntity();
				result = EntityUtils.toString(outputEntity);
				{
					if (result == null)
					{
						throw new RuntimeException(result);
					}
				}
			}
		
		return result;
	}
	
	/**
	 * 
	 * @param collection
	 * @param url
	 * @return 返回结果由服务器而定
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public String MultiUpload(AttachInfoCollection collection,String url) throws ClientProtocolException, IOException
	{
		String result = null;

		HttpClient httpClient = new DefaultHttpClient();
		HttpContext httpContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(url);

			CustomMultipartEntity multipartContent = new CustomMultipartEntity(null);
			/**
			 *  上传多个文件
			 *  图文上传
			 *  multipartContent.addPart("text", "content");
			 *  StringBody stringBody = new StringBody("test");
			 *  支持多样式
			 */
			for (AttachInfo attachInfo : collection) {
				FileBody fileBody = new FileBody(new File(attachInfo.getUri()));
				multipartContent.addPart("file", fileBody);
			}
			
			httpPost.setEntity(multipartContent);
			HttpResponse response = httpClient.execute(httpPost, httpContext);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				HttpEntity outputEntity = response.getEntity();
				result = EntityUtils.toString(outputEntity);
				{
					if (result == null)
					{
						throw new RuntimeException(result);
					}
				}
			}
		
		return result;
	}
}
