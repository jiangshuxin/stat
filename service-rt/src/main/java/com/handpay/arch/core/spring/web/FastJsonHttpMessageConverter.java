package com.handpay.arch.core.spring.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

public class FastJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    public final static Charset UTF8    = Charset.forName("UTF-8");

    private Charset             charset = UTF8;

    private SerializerFeature[] serializerFeature;
    
    public FastJsonHttpMessageConverter() {
		super();
		
		SerializeConfig.getGlobalInstance().put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
		SerializeConfig.getGlobalInstance().put(Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
		SerializeConfig.getGlobalInstance().put(java.sql.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return true;
	}

    @Override
    protected Object readInternal(Class<? extends Object> clazz,
			HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        InputStream in = inputMessage.getBody();

        byte[] buf = new byte[1024];
        for (;;) {
            int len = in.read(buf);
            if (len == -1) {
                break;
            }

            if (len > 0) {
                baos.write(buf, 0, len);
            }
        }

        byte[] bytes = baos.toByteArray();
        if (charset == UTF8) {
            return JSON.parseObject(bytes, clazz);
        } else {
            return JSON.parseObject(bytes, 0, bytes.length, charset.newDecoder(), clazz);
        }
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException,
                                                                             HttpMessageNotWritableException {

        OutputStream out = outputMessage.getBody();
        byte[] bytes;
        
        if (charset == UTF8) {
            if (serializerFeature != null) {
                bytes = JSON.toJSONBytes(obj, serializerFeature);
            } else {
                bytes = JSON.toJSONBytes(obj);
            }
            
        } else {
            String text;
            if (serializerFeature != null) {
                text = JSON.toJSONString(obj, serializerFeature);
            } else {
                text = JSON.toJSONString(obj);
            }
            bytes = text.getBytes(charset);
        }
        
        out.write(bytes);
    }

	public SerializerFeature[] getSerializerFeature() {
		return serializerFeature;
	}

	public void setSerializerFeature(SerializerFeature[] serializerFeature) {
		this.serializerFeature = serializerFeature;
	}
}
