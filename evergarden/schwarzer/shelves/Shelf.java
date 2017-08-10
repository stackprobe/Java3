package evergarden.schwarzer.shelves;

import java.awt.Component;

public abstract class Shelf {
	public int height = 50;
	public String className = "DummyShelf";

	// ここへ追加...

	// ---- init @ ShelvesDialog.init() ----

	public IntRect rect;
	public Column parent;
	public int index;

	// ----

	public abstract Component getComponent();
	public abstract void setValue(Object value);
	public abstract Object getValue();
}
