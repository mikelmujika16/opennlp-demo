
package org.fogbeam.example.opennlp.training;


import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.postag.WordTagSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;


public class PartOfSpeechTaggerTrainer
{
	public static void main( String[] args )
	{
		POSModel model = null;
		InputStream dataIn = null;
		try
		{
			dataIn = new FileInputStream( "training_data/en-pos.train" );
			ObjectStream<String> lineStream = new PlainTextByLineStream(
					dataIn, "UTF-8" );
			ObjectStream<POSSample> sampleStream = new WordTagSampleStream(
					lineStream );
			model = POSTaggerME.train( "en", sampleStream,
					TrainingParameters.defaultParams(), null, null );
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
		String modelFile = "models/en-pos.model";
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
