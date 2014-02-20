package com.silverheart.client.indicator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author <a href="mailto:bs@nuxeo.com">Bogdan Stefanescu</a>
 * 
 */
public class BusyIndicator {

	private static BusyIndicator indicator;

	public static BusyIndicator getInstance() {
		if (indicator == null) {
			indicator = new BusyIndicator();
		}
		return indicator;

	}

	protected Widget target;

	protected Element busy;

	public BusyIndicator() {
		this(null);
	}

	public BusyIndicator(Widget target) {
		this.target = target;
	}

	protected Element createBusy() {
		Element busy = DOM.createDiv();
		DOM.setStyleAttribute((com.google.gwt.user.client.Element) busy, "backgroundRepeat", "no-repeat");
		DOM.setStyleAttribute((com.google.gwt.user.client.Element) busy, "backgroundAttachment", "fixed");
		DOM.setStyleAttribute((com.google.gwt.user.client.Element) busy, "backgroundPosition", "center");
		DOM.setStyleAttribute((com.google.gwt.user.client.Element) busy, "backgroundImage",
		"url("+GWT.getModuleBaseURL()+"load.gif"+")");
		Style style = busy.getStyle();
		style.setPosition(Position.ABSOLUTE);
		style.setBackgroundColor("white");
		style.setOpacity(0.2);
		style.setZIndex(100000);
		if (target == null) {
			Element body = Document.get().getBody();
			style.setLeft(0, Unit.PX);
			style.setTop(0, Unit.PX);
			/*style.setWidth(body.getOffsetWidth(), Unit.PX);
			style.setHeight(body.getOffsetHeight(), Unit.PX);*/
			style.setWidth(100, Unit.PCT);
			style.setHeight(100, Unit.PCT);
		} else {
			style.setLeft(target.getAbsoluteLeft(), Unit.PX);
			style.setTop(target.getAbsoluteTop(), Unit.PX);
			style.setWidth(target.getOffsetWidth(), Unit.PX);
			style.setHeight(target.getOffsetHeight(), Unit.PX);
		}
		return busy;
	}

	public void show() {
		busy = createBusy();
		Document.get().getBody().appendChild(busy);
	}

	public void remove() {
		if (busy != null) {
			busy.removeFromParent();
			busy = null;
		}
	}

	public static void on() {
		getInstance().show();
	}

	public static void off() {
		getInstance().remove();
	}
}