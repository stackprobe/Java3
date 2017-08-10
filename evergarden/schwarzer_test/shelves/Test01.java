package evergarden.schwarzer_test.shelves;

import evergarden.schwarzer.shelves.Button;
import evergarden.schwarzer.shelves.Column;
import evergarden.schwarzer.shelves.Form;
import evergarden.schwarzer.shelves.Header;
import evergarden.schwarzer.shelves.Shelf;
import evergarden.schwarzer.shelves.ShelvesDialog;
import evergarden.schwarzer.shelves.ShelvesManager;
import evergarden.schwarzer.shelves.Tab;
import evergarden.schwarzer.shelves.shelf.DummyShelf;
import evergarden.schwarzer.shelves.shelf.LabelledComboBox;
import evergarden.schwarzer.shelves.shelf.LabelledTextField;

public class Test01 {
	public static void main(String[] args) {
		try {
			test01();

			System.out.println("OK!");
		}
		catch(Throwable e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private static void test01() {
		ShelvesManager mgr = new ShelvesManager() {
			@Override
			public Form getForm() {
				Form form = new Form();

				form.header = new Header();
				form.header.buttons.add(new Button());
				form.header.buttons.add(new Button());
				form.header.buttons.add(new Button());
				form.header.align = "left";

				form.footer = new Header();
				form.footer.buttons.add(new Button());
				form.footer.buttons.add(new Button());
				form.footer.buttons.add(new Button());
				form.footer.buttons.add(new Button());
				form.footer.buttons.add(new Button());
				form.footer.align = "right";

				{
					Tab tab = new Tab();

					{
						Column column = new Column();

						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());

						tab.columns.add(column);
					}

					{
						Column column = new Column();

						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());

						tab.columns.add(column);
					}

					form.tabs.add(tab);
				}

				{
					Tab tab = new Tab();

					{
						Column column = new Column();

						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());

						tab.columns.add(column);
					}

					{
						Column column = new Column();

						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());

						tab.columns.add(column);
					}

					{
						Column column = new Column();

						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());
						column.shelves.add(new DummyShelf());

						tab.columns.add(column);
					}

					form.tabs.add(tab);
				}

				{
					Tab tab = new Tab();

					{
						Column column = new Column();

						column.shelves.add(new LabelledTextField());
						column.shelves.add(new LabelledTextField());
						column.shelves.add(new LabelledTextField());
						column.shelves.add(new LabelledTextField());
						column.shelves.add(new LabelledTextField());
						column.shelves.add(new LabelledTextField());
						column.shelves.add(new LabelledTextField());
						column.shelves.add(new LabelledTextField());
						column.shelves.add(new LabelledTextField());

						tab.columns.add(column);
					}

					{
						Column column = new Column();

						column.shelves.add(new LabelledTextField());
						column.shelves.add(new LabelledTextField());
						column.shelves.add(new LabelledTextField());
						column.shelves.add(new LabelledTextField());
						column.shelves.add(new LabelledTextField());
						column.shelves.add(new LabelledTextField());
						column.shelves.add(new LabelledTextField());

						tab.columns.add(column);
					}

					{
						Column column = new Column();

						column.shelves.add(new LabelledComboBox());
						column.shelves.add(new LabelledTextField());
						column.shelves.add(new LabelledComboBox());
						column.shelves.add(new LabelledTextField());
						column.shelves.add(new LabelledComboBox());

						tab.columns.add(column);
					}

					for(Column column : tab.columns) {
						for(Shelf shelf : column.shelves) {
							shelf.height = 26;
						}
					}

					form.tabs.add(tab);
				}

				return form;
			}

			@Override
			public void load() {
				// noop
			}
		};

		ShelvesDialog dlg = new ShelvesDialog(null, mgr);

		dlg.perform();
	}
}
