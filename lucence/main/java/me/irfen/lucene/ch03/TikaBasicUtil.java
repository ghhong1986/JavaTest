package me.irfen.lucene.ch03;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ToXMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class TikaBasicUtil {

	public static String extractContent(File f) {
		// 1、创建一个parser
		Parser parser = new AutoDetectParser();
		InputStream is = null;
		try {
			Metadata metadata = new Metadata();
			metadata.set(Metadata.RESOURCE_NAME_KEY, f.getName());
			is = new FileInputStream(f);
			ContentHandler handler = new BodyContentHandler();
			ParseContext context = new ParseContext();
			context.set(Parser.class, parser);

			// 2、执行parser的parse()方法。
			parser.parse(is, handler, metadata, context);

			String returnString = handler.toString();
			return returnString;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "No Contents";
	}
	
	
	public static String extractToXml(String filePath) throws IOException, SAXException, TikaException{
        ContentHandler handler = new ToXMLContentHandler();
//        InputStream stream = TikaBasicUtil.class.getResourceAsStream(filePath);
        InputStream  stream  = new FileInputStream(new File(filePath));
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        metadata.add("filename", "王文京的一封信：从duang的那一声起");
        metadata.add("Author", "王文京");
        try {
            parser.parse(stream, handler, metadata);
            return handler.toString();
        } finally {
            stream.close();
        }
	}
}
