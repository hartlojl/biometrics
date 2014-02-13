package settings.settingsvariables;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SettingsString extends SettingsVariable{

	private static final long serialVersionUID = 1L;
	String value; //lol
	
	public SettingsString(String name, String value) {
		this.name = name;
		this.value = value;
	}
	public SettingsString(String value) {
		this.name = value;
		this.value = value;
	}

	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	
	
	
	@Override
	protected JPanel thisJPanel() {
		JPanel panel = new JPanel();
//		panel.setLayout(new GridLayout(1,2));
//		panel.setLayout(new FlowLayout());
//		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		final JTextField textField = new JTextField();
		textField.setColumns(5);
		textField.getDocument().addDocumentListener(new DocumentListener(){
			
			@Override
			public void removeUpdate(DocumentEvent e){}			
			@Override
			public void insertUpdate(DocumentEvent e){}
			@Override
			public void changedUpdate(DocumentEvent e){
				setValue(textField.getText());
			}
			
		});
		
		panel.add(new JLabel(this.name));//, BorderLayout.WEST);
		panel.add(textField);//, BorderLayout.EAST);
		
		return panel;
	}

	@Override
	protected void init() {}
	
	
	
}
