package com.silverheart.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.silverheart.client.indicator.BusyIndicator;
import com.silverheart.client.widgets.CellTablePanel;
import com.silverheart.shared.dto.AgreementDTO;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Good implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */

	LayoutPanel dock;

	public void drawPanel(Widget component) {
		mainPanel.clear();
		mainPanel.add(component);
	}

	private SimplePanel mainPanel = new SimplePanel();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// ОТРИСОВКА главной панели
		{
			Timer t = new Timer() {
				@Override
				public void run() {
					DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById("papa-loading"));
				}
			};
			t.schedule(1000);
			dock = new LayoutPanel();
			dock.setWidth("100%");
			dock.setHeight("100%");

			HTML head = new HTML("Тут Будет шапка");
			//http://www.colourlovers.com/palette/919313/Papua_New_Guinea
			head.addStyleName("cofee");
			dock.add(head);
			dock.setWidgetLeftRight(head, 0, Unit.PX, 0, Unit.PX);
			dock.setWidgetTopBottom(head, 0, Unit.PX, 150, Unit.PX);

			HTML foot = new HTML("подвал");
			foot.addStyleName("cofee");
			dock.add(foot);
			dock.setWidgetLeftRight(foot, 0, Unit.PX, 0, Unit.PX);
			dock.setWidgetBottomHeight(foot, 0, Unit.PX, 100, Unit.PX);
			mainPanel.addStyleName("milk");
			dock.add(mainPanel);
			dock.setWidgetLeftRight(mainPanel, 0, Unit.PX, 0, Unit.PX);
			dock.setWidgetTopBottom(mainPanel, 150, Unit.PX, 100, Unit.PX);

			RootPanel.get("main").add(dock);

			drawTable();
		}

	}

	public void drawTable() {
		mainPanel.clear();
		BusyIndicator.on();
		ServiceFactory.getService().getAgreements(new AsyncCallback<List<AgreementDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				BusyIndicator.off();
				Window.alert("Чето случилось договора не отрисовались");

			}

			@Override
			public void onSuccess(List<AgreementDTO> result) {
				drawPanel(new CellTablePanel(result, Good.this));
				BusyIndicator.off();
			}

		});

	}


	public static String getDateStr(Date date) {
		if (date == null)
			return "-";
		return DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG).format(date);
	}
}
