package com.java.client;

import java.io.IOException;
import java.util.Arrays;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class Demo1 {

	public static void main(String[] args) throws IOException, ParseException {
		Directory index= new RAMDirectory();
		IndexWriter writer= new IndexWriter(index, new IndexWriterConfig());
		writer.addDocument(createDocument(writer,"John arya", 1));
		writer.addDocument(createDocument(writer, "Johnny shaurya",2));
		writer.addDocument(createDocument(writer, "Kenny shaurya",3));
		writer.addDocument(createDocument(writer, "Dev maurya",4));
		writer.close();
		
		IndexReader reader= DirectoryReader.open(index);
		IndexSearcher searcher=new IndexSearcher(reader);
		Query q=new QueryParser("name",new StandardAnalyzer()).parse("maurya");
		TopDocs docs=searcher.search(q, 10); 
		Arrays.stream(docs.scoreDocs).map(x-> x.doc).forEach(id-> {
			try {
				System.out.println(searcher.doc(id));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});;
	}

	private static Document createDocument(IndexWriter writer, String name, int rank) {
		Document d=new Document();
		d.add(new TextField("name",name, Field.Store.YES));
		d.add(new StoredField("rank", rank));
		return d;
	}

}
