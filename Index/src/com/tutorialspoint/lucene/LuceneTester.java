package com.tutorialspoint.lucene;

import java.io.IOException;
import java.util.Scanner;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class LuceneTester {
	
   String indexDir = "index/";
   String dataDir = "arquivos/";
   Indexer indexer;
   Searcher searcher;

   public static void main(String[] args) {
      LuceneTester tester;
      Scanner scan = new Scanner(System.in);
      try {
            Cria_arquivos arq = new Cria_arquivos();
            tester = new LuceneTester();
            
            System.out.println("Deseja deletar toas as consultas anteriores?");
            String sim = scan.nextLine();
            if(sim.equalsIgnoreCase("sim")){
                arq.deleta_arquivos();
            }
            
            System.out.println("Digite o endereço");
            String endereco = scan.nextLine();
            arq.insere_arquivo(endereco);
            System.out.println("criando o indice....");
            tester.createIndex();
            System.out.println("Entre com a chave de busca: ");
            String chave = scan.nextLine();
            tester.search(chave);
      } catch (IOException | ParseException e) {
      }
      
   }

   private void createIndex() throws IOException{
      indexer = new Indexer(indexDir);
      int numIndexed;
      long startTime = System.currentTimeMillis();	
      numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
      long endTime = System.currentTimeMillis();
      indexer.close();
      System.out.println(numIndexed+" File indexed, time taken: "
         +(endTime-startTime)+" ms");		
   }

   private void search(String searchQuery) throws IOException, ParseException{
      searcher = new Searcher(indexDir);
      long startTime = System.currentTimeMillis();
      TopDocs hits = searcher.search(searchQuery);
      long endTime = System.currentTimeMillis();
   
      System.out.println(hits.totalHits +
         " documents found. Time :" + (endTime - startTime));
      for(ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.getDocument(scoreDoc);
            
            String url = doc.get(LuceneConstants.FILE_NAME);
            
            String[] division;
            division = url.split(".html");
            
            System.out.println("File: "
            + division[0]);
      }
      searcher.close();
   }
}