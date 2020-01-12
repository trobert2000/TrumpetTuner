package de.imbium.trumpettuner;

import javax.sound.sampled.*;

public class TrumpetTunerAudioHandler {

	private TrumpetTuner  TT;
	
	private AudioFormat format;
	
	public TrumpetTunerAudioHandler(TrumpetTuner tt) {	
			TT=tt;
			
			format= new AudioFormat(11025,16,1,true,true);
	}
	
	public void startRecording() {
		
		TargetDataLine line;
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, 
		    format); // format is an AudioFormat object
		if (AudioSystem.isLineSupported(Port.Info.MICROPHONE)) {
		    try {
		        line = (TargetDataLine) AudioSystem.getLine(
		            Port.Info.MICROPHONE);		
			    //line = (TargetDataLine) AudioSystem.getLine(info);
			    line.open(format);
			} catch (LineUnavailableException ex) {
			    // Handle the error ... 
			}
		}
	}
}
