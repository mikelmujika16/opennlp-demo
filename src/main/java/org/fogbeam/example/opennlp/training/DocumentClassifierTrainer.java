
package org.fogbeam.example.opennlp.training;


import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;


public class DocumentClassifierTrainer
{
	public static void main( String[] args ) throws Exception
	{
		DoccatModel model = null;
		InputStream dataIn = null;
		try
		{
			dataIn = new FileInputStream( "training_data/en-doccat.train" );
			ObjectStream<String> lineStream = new PlainTextByLineStream(
					dataIn, "UTF-8" );
			ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(
					lineStream );
			model = DocumentCategorizerME.train( "en", sampleStream );
		}
		catch( IOException e )
		{
		}
		finally
		{
			if( dataIn != null )
			{
				try
				{
					dataIn.close();
				}
				catch( IOException e )
				{
				}
			}
		}
		OutputStream modelOut = null;
		String modelFile = "models/en-doccat.model";
		try
		{
			modelOut = new BufferedOutputStream( new FileOutputStream(
					modelFile ) );
			model.serialize( modelOut );
		}
		catch( IOException e )
		{
		}
		finally
		{
			if( modelOut != null )
			{
				try
				{
					modelOut.close();
				}
				catch( IOException e )
				{
				}
			}
		}
		
		
		System.out.println( "done" );
	}
}
