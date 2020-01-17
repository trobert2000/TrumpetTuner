package de.imbium.trumpettuner;

import java.io.File;
import java.io.IOException;
import java.nio.*;
import java.util.ArrayList;

import javax.sound.sampled.*;

public class TrumpetTunerAudioHandler {

	public static final int BUF_SIZE = 2048;
	public static final int BUF_SIZE_8 = 8 * BUF_SIZE;
	
	private TrumpetTuner  TT;
	private boolean recordFlag = true;
	
	private TargetDataLine line;
	
	private AudioFormat format,f2;
	
	private ArrayList<byte[]> ChunkList = new ArrayList<byte[]>();
	
	public TrumpetTunerAudioHandler(TrumpetTuner tt) {	
			TT=tt;
			
			format= new AudioFormat(11025,16,2,true,true);
			// make Mono and big_endian For File analysis
			f2 = new AudioFormat(44100,16,1,true,true);
	}
	
	public void analyzeFile() {
		// path of the wav file
		File wavFile = new File("D:/java/stimmton.wav");
		
		try {
			AudioFileFormat fileformat = AudioSystem.getAudioFileFormat(wavFile);
			System.out.println("fformat: "+fileformat);
			
			// convert file to 44100Hz,Mono,16bit,big_endian
			AudioInputStream ais = AudioSystem.getAudioInputStream(wavFile);//AudioSystem.getAudioInputStream(f2,AudioSystem.getAudioInputStream(wavFile));
			
			ChunkList = new ArrayList<byte[]>();
			
			// 8 byte for a double so read 8 times the BUF_SIZE
			int num_chunks = (int)(ais.available() / BUF_SIZE_8);
			
			while(ais.available() >= BUF_SIZE_8) {
				// 8 byte for a double so read 8 times the BUF_SIZE
				byte[] chunk = ais.readNBytes(BUF_SIZE_8);
				ChunkList.add(chunk);
			}
			
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}
	
	public void startRecording() {
		
		// path of the wav file
		File wavFile = new File("D:/java/RecordAudio.wav");

		// format of audio file
		AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
		
		
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, 
		    format); // format is an AudioFormat object
		//if (AudioSystem.isLineSupported(Port.Info.MICROPHONE)) {
		    try {
		        //line = (TargetDataLine) AudioSystem.getLine(
		        //    Port.Info.MICROPHONE);	
		    	
		    	
			    line = (TargetDataLine) AudioSystem.getLine(info);
			    line.open(format);
			    
			    //AudioInputStream ais = new AudioInputStream(line);
			    
			    System.out.println("Start Recording...");
			    line.start();
			    int bytesRead = 0;
			    byte[] buf = new byte[BUF_SIZE];
			    
			    while(recordFlag && (bytesRead = line.read(buf, 0, BUF_SIZE)) != -1) {
			    	
			    	System.out.println(buf.length + " "+line.available()+ " "+ bytesRead);//recordFlag);
			    	// start recording
					//AudioSystem.write(ais, fileType, wavFile);
			    	
			    	if(bytesRead == BUF_SIZE) {
			    		ChunkList.add(buf);
			    	}
			    }
			    
			    System.out.println("Chunks "+	ChunkList.size());    
			  
			} catch (LineUnavailableException ex) {// | IOException ex) {
			    // Handle the error ... 
				ex.printStackTrace();
			}
		    
		//}
	}
	
	public void stopRecording() {
		line.stop();
		line.close();
		recordFlag = false;
		System.out.println("Stop Recording...");

	}
	

}
