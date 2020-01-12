package de.imbium.trumpettuner;

import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;



public class TrumpetTuner extends JFrame {
	
	private JButton startButton,stopButton;
	private JTextField FreqTF,DataTF;
	
	private TrumpetTunerAL trumpetTunerAL; 
	
	private TestThread MyThread;
	private TrumpetTunerAudioHandler tah;
	
	final boolean shouldFill = true;
    final boolean shouldWeightX = true;
    final boolean RIGHT_TO_LEFT = false;
	
	public TrumpetTuner() {
		
		trumpetTunerAL = new TrumpetTunerAL(this);
		//creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
        System.out.println("Tag");
	}
	
	
	public void startTuning() {
		//MyThread = new TestThread(this);
		//MyThread.start();
		tah = new TrumpetTunerAudioHandler(this);
		tah.startRecording();
	}
	
	
	public void setData(int i) {
		String str = String.valueOf(i);
		DataTF.setText(str);
	}
	
	
	public void processActionEvent(ActionEvent event) {
		if(event.getSource().equals(startButton) ) {
			System.out.println("Start");
			startTuning();
		}
		if(event.getSource().equals(stopButton) ) {
			System.out.println("Stop");
		}
		
	}
	
	private void addComponentsToPane(Container pane) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        JButton button;
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		if (shouldFill) {
		//natural height, maximum width
		c.fill = GridBagConstraints.HORIZONTAL;
		}

		 
		startButton = new JButton("Start Processing");
		startButton.addActionListener(trumpetTunerAL);
		if (shouldWeightX) {
		c.weightx = 0.5;
		}
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		pane.add(startButton, c);

		stopButton = new JButton("Stop");
		stopButton.addActionListener(trumpetTunerAL);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		pane.add(stopButton, c);

		FreqTF = new JTextField("0.0 Hz");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(FreqTF, c);

		DataTF = new JTextField("0");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		pane.add(DataTF, c);
		

		button = new JButton("5");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       //reset to default
		c.weighty = 1.0;   //request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 1;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = 3;       //third row
		pane.add(button, c);
	}
	
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Trumpet Tuner 0.1");
        frame.setSize(600,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());
        
        //Display the window.        
        frame.pack();
        frame.setVisible(true);  
    }
    

	public static void main(String[] args) {
		TrumpetTuner t = new TrumpetTuner();
		t.startTuning();		
	}
}


class TrumpetTunerAL implements ActionListener {
	
	private TrumpetTuner TT;
	
	public TrumpetTunerAL(TrumpetTuner tt) {
		TT = tt;
	}
	
	public void actionPerformed(ActionEvent e) {
		TT.processActionEvent(e);
	}
	
}
