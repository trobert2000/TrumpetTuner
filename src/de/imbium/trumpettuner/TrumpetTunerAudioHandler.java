package de.imbium.trumpettuner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.*;

public class TrumpetTunerAudioHandler {

	private static final int BUF_SIZE = 2048;
	private TrumpetTuner  TT;
	private boolean recordFlag = true;
	
	private TargetDataLine line;
	
	private AudioFormat format;
	
	private ArrayList<byte[]> ChunkList = new ArrayList<byte[]>();
	
	public TrumpetTunerAudioHandler(TrumpetTuner tt) {	
			TT=tt;
			
			format= new AudioFormat(11025,16,1,true,true);
	}
	
	
	public void analyzeFile() {
		// path of the wav file
		File wavFile = new File("D:/java/stimmton.wav");
		
		try {
			AudioFileFormat fileformat = AudioSystem.getAudioFileFormat(wavFile);
			AudioInputStream ais = AudioSystem.getAudioInputStream(wavFile);
			
			ChunkList = new ArrayList<byte[]>();
			int num_chunks = (int)(ais.available() / BUF_SIZE);
			
			while(ais.available() >= BUF_SIZE) {
				byte[] chunk = ais.readNBytes(BUF_SIZE);
				ChunkList.add(chunk);
			}
			
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Chunks "+	ChunkList.size());    
		
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
			    	
			    	buf = new byte[BUF_SIZE];
			    	
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
