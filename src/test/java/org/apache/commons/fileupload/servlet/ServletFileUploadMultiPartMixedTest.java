package org.apache.commons.fileupload.servlet;

import org.apache.commons.fileupload.Constants;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.MockHttpServletRequest;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ServletFileUploadMultiPartMixedTest {

	@Test
	public void testMultipartMixed() throws UnsupportedEncodingException, FileUploadException {
			String text = "-----1234\r\n" +
					"Content-Type: text/whatever\r\n"+
					"Content-ID: file\r\n"+
					"\r\n" +
					"This is the content of the file\n" +
					"\r\n" +
					"-----1234\r\n";

		byte[] bytes = text.getBytes("UTF-8");
		HttpServletRequest request = new MockHttpServletRequest(bytes, "multipart/mixed;boundary=-----1234;charset=UTF-8");

		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		fileItemFactory.setDefaultCharset("UTF-8");
		ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
		List<FileItem> fileItems = upload.parseRequest(request);
		Assert.assertNotNull(fileItems);
		Assert.assertEquals(1, fileItems.size());
		FileItem fileItem = fileItems.get(0);
		assertTrue(fileItem.getString(), fileItem.getString().contains("coñteñt"));
	}
}
