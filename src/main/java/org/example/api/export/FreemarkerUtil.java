package org.example.api.export;


import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

/**
 * 类描述：导出Freemarker word文档
 *
 * @author ：guozepeng
 * @version: 1.0  导出工具
 * <p>
 */
@Component
public class FreemarkerUtil {


	/**
	 * 导出Freemarker word文档
	 *
	 * @param dataMap
	 * @param response
	 * @throws IOException
	 */
	public void createFreemarkerDoc(Map<?, ?> dataMap, HttpServletResponse response, String fileName) {
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
		configuration.setDefaultEncoding("UTF-8"); // 设置编码
		configuration.setClassForTemplateLoading(this.getClass(), "/doctemplate");
		File file = null;
		InputStream fin = null;
		ServletOutputStream out = null;
		try {
			Template freemarkerTemplate = configuration.getTemplate(fileName);
			// 调用工具类的createDoc方法生成Word文档
			file = createDoc(dataMap, freemarkerTemplate, fileName);
			fin = new FileInputStream(file);
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/msword");
			response.setHeader("Content-Disposition", "attachment;filename="
					.concat(String.valueOf(URLEncoder.encode(UUID.randomUUID().toString() + ".doc", "UTF-8"))));
			out = response.getOutputStream();
			byte[] buffer = new byte[512]; // 缓冲区
			int bytesToRead = -1;
			while ((bytesToRead = fin.read(buffer)) != -1) {
				out.write(buffer, 0, bytesToRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (file != null) {
				file.delete();
			}
		}
	}


	/**
	 * 生成临时文件
	 *
	 * @param dataMap
	 * @param template
	 * @return
	 * @throws IOException
	 */
	private static File createDoc(Map<?, ?> dataMap, Template template, String fileName) throws IOException {
		File file = File.createTempFile("raven", fileName);
		Template t = template;
		try {
			Writer w = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
			t.process(dataMap, w);
			w.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return file;
	}

}
