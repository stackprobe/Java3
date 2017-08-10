package evergarden.schwarzer.shelves.shelf;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import evergarden.schwarzer.shelves.Shelf;
import evergarden.schwarzer.shelves.ShelvesDialog;

public class LabelledTextField extends Shelf {
	public String labelText = "未定義";
	public int labelWidth = 100;
	public boolean editable = true;

	@Override
	public Component getComponent() {
		if(panel == null) {
			createComponent();
		}
		return panel;
	}

	private JPanel panel;
	private JLabel label;
	private JTextField field;

	private void createComponent() {
		panel = new JPanel();
		ShelvesDialog.AncLayoutMgr layout = new ShelvesDialog.AncLayoutMgr(panel);
		panel.setLayout(layout);

		label = new JLabel();
		label.setText(labelText + "：");

		layout.add(
				label,
				0,
				0,
				labelWidth,
				0,
				true,
				true,
				false,
				true
				);

		field = new JTextField();
		field.setEditable(editable);

		layout.add(
				field,
				labelWidth,
				0,
				0,
				0,
				true,
				true,
				true,
				true
				);
	}

	@Override
	public void setValue(Object value) {
		field.setText((String)value);
	}

	@Override
	public Object getValue() {
		return field.getText();
	}
}
