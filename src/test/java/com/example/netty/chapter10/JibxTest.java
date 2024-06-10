package com.example.netty.chapter10;

import java.io.StringReader;
import java.io.StringWriter;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.jupiter.api.Test;

import com.example.netty.pojo.Student;

/**
 * 
 * @author Carl
 * @date 2024年6月10日 下午8:35:29
 */
public class JibxTest {
	@Test
	void pojo2xml() {
		try {
			Student student = Student.builder().age(18).name("lucy").clazz("grade 2").build();
			IBindingFactory factory = BindingDirectory.getFactory(Student.class);
			StringWriter writer = new StringWriter();
			IMarshallingContext marshallingContext = factory.createMarshallingContext();
			marshallingContext.setIndent(2);
			marshallingContext.marshalDocument(student, "UTF-8", null, writer);
			System.out.println(writer.toString());
		} catch (JiBXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void xml2pojo() {
		String xml = """
				<?xml version="1.0" encoding="UTF-8"?>
				<student xmlns="http://example.com/netty/pojo" age="18">
				  <name>Lucy</name>
				  <clazz>class 2</clazz>
				</student>
								""";
		try {
			IBindingFactory factory = BindingDirectory.getFactory(Student.class);
			IUnmarshallingContext unmarshallingContext = factory.createUnmarshallingContext();
			Student student = (Student) unmarshallingContext.unmarshalDocument(new StringReader(xml));
			System.out.println(student);
		} catch (JiBXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
