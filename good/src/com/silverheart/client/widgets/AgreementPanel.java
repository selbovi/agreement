package com.silverheart.client.widgets;

import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.silverheart.client.Good;
import com.silverheart.client.ServiceFactory;
import com.silverheart.client.indicator.BusyIndicator;
import com.silverheart.shared.dto.AgreementDTO;
import com.silverheart.shared.dto.ContragentDTO;
import com.silverheart.shared.dto.AgreementDTO.AgreementCols;
import com.silverheart.shared.dto.AgreementWholeDTO;

public class AgreementPanel extends Composite {

	private Good good;

	public AgreementPanel(final AgreementWholeDTO dto, Good good) {

		this.good = good;
		LayoutPanel dock = new LayoutPanel();

		Widget widget = drawContent(dto.getaDto());
		dock.add(widget);
		dock.setWidgetTopBottom(widget, 0, Unit.PX, 50, Unit.PX);
		dock.setWidgetLeftRight(widget, 0, Unit.PX, 200, Unit.PX);
		String agreementId = dto.getaDto()
				.getId();
		CommentaryPanel commentPanel = new CommentaryPanel(agreementId, dto.getComments());
		dock.add(commentPanel);
		dock.setWidgetTopHeight(commentPanel, 0, Unit.PX, 100, Unit.PCT);
		dock.setWidgetRightWidth(commentPanel, 0, Unit.PX, 200, Unit.PX);

		FilesPanel files = new FilesPanel(agreementId, dto.getFiles());
		dock.add(files);
		dock.setWidgetBottomHeight(files, 0, Unit.PX, 50, Unit.PCT);
		dock.setWidgetLeftRight(files, 0, Unit.PX, 200, Unit.PX);

		dock.setWidth("100%");
		dock.setHeight("100%");

		initWidget(dock);
	}
	
	private Widget drawContent(final AgreementDTO d){
		VerticalPanel vp = new VerticalPanel();
		vp.add(drawWidget(AgreementCols.ID, d.getId()));
		vp.add(drawWidget(AgreementCols.NAME, d.getName()));
		vp.add(drawWidget(AgreementCols.KONTRAGENT, d.getContragent()));
		vp.add(drawWidget(AgreementCols.STAGE, d.getStage()));
		vp.add(drawWidget(AgreementCols.DATE, Good.getDateStr(d.getDate())));
		
		Button edit = new Button("Редактировать", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				BusyIndicator.on();
				ServiceFactory.getService().getContragents(new AsyncCallback<List<ContragentDTO>>() {
					
					@Override
					public void onSuccess(List<ContragentDTO> result) {
						good.drawPanel(new EditAgreement(d, result, good));
						BusyIndicator.off();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						BusyIndicator.off();
						Window.alert("Чето случилось и нельзя теперь поредактировать");
					}
				});
			}
		});
		Button del = new Button("Удалить", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				ServiceFactory.getService().deleteAgreement(d.getId(), new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						good.drawTable();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Чето случилось и договор не удалился");
					}
				});
				
			}
		});
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(edit);
		hp.add(del);
		vp.add(hp);
		return vp;
	}
	
	private Widget drawWidget(String field, String val){
		HorizontalPanel hp = new HorizontalPanel();
		Label name = new Label(field);
		Label labVal = new Label(val);
		hp.add(name);
		hp.add(labVal);
		
		return hp;
	}

}
