import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ReadFile extends JFrame{
	
	private JTextField addressBar;
	private JEditorPane display;
	
	//constructor
	public ReadFile() {
		super("My browser");
		
		addressBar = new JTextField("enter a URL!");
		addressBar.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							loadMyTrollStuff(event.getActionCommand());
						}
					}
		);
		add(addressBar, BorderLayout.NORTH);
		
		display = new JEditorPane();
		display.setEditable(false);
		display.addHyperlinkListener(
				new HyperlinkListener() {
					public void hyperlinkUpdate(HyperlinkEvent event) {
						if(event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
							loadMyTrollStuff(event.getURL().toString());
						}
					}
				}
		);
		add(new JScrollPane(display), BorderLayout.CENTER);
		setSize(500, 300);
		setVisible(true);
	}
	
	//load stuff to display on screen
	private void loadMyTrollStuff(String userText) {
		try {
			display.setPage(userText);
			addressBar.setText(userText);
		}
		catch(Exception e) {
			System.out.println("Error!");
		}
	}
}
