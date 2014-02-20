package com.silverheart.client.widgets;

import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.silverheart.client.Good;
import com.silverheart.client.ServiceFactory;
import com.silverheart.shared.dto.AgreementDTO;
import com.silverheart.shared.dto.AgreementDTO.AgreementCols;
import com.silverheart.shared.dto.AgreementDTO.Stages;
import com.silverheart.shared.dto.AgreementWholeDTO;
import com.silverheart.shared.dto.ContragentDTO;

public class EditAgreement extends Composite {

	boolean isNewAgreement;
	private Good good;

	public EditAgreement(AgreementDTO dto, List<ContragentDTO> contragents, final Good good) {
		this.good = good;
		if (dto == null) {
			dto = new AgreementDTO();
			isNewAgreement = true;
		}
		VerticalPanel vp = new VerticalPanel();
		final Tbox1 id = new Tbox1(AgreementCols.ID, dto.getId());
		final Tbox1 name = new Tbox1(AgreementCols.NAME, dto.getName());
		final TlistKont kontragents = new TlistKont(AgreementCols.NAME, contragents, dto.getContragent());
		final TListStages st = new TListStages(AgreementCols.STAGE, dto.getStage());
		final Dbox1 date = new Dbox1(AgreementCols.DATE, dto.getDate());

		Button save = new Button("Сохранить", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				AgreementDTO newdto = new AgreementDTO();
				newdto.setId(id.getValue());
				newdto.setName(name.getValue());
				newdto.setContragent(kontragents.getValue());
				newdto.setStage(st.getValue());
				newdto.setDate(date.getValue());
				if (isNewAgreement) {
					
					ServiceFactory.getService().createAgreement(newdto, new AsyncCallback<AgreementWholeDTO>() {
						
						@Override
						public void onSuccess(AgreementWholeDTO result) {
							good.drawPanel(new AgreementPanel(result, good));
							
						}
						
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Чето случилось договор не отрисовался");
						}
					});
				} else {
					ServiceFactory.getService().updateAgreement(newdto, new AsyncCallback<AgreementWholeDTO>() {
						
						@Override
						public void onSuccess(AgreementWholeDTO result) {
							good.drawPanel(new AgreementPanel(result, good));
							
						}
						
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Чето случилось договор не обновился в базе");
						}
					});
				}

			}
		});

		vp.add(id);
		vp.add(name);
		vp.add(kontragents);
		vp.add(st);
		vp.add(date);
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(save);
		vp.add(hp);
		

		initWidget(vp);
	}

	public class TListStages extends HorizontalPanel {
		private ListBox tb;

		public TListStages(String fieldName, String stage) {
			Label name = new Label(fieldName);
			tb = new ListBox();
			for (Stages s : Stages.values()) {
				tb.addItem(s.stage);
			}
			if (stage != null && stage.length() > 0) {
				int itemCount = tb.getItemCount();
				for (int i = 0; i < itemCount; i++) {
					String value = tb.getValue(i);
					if (value.equals(stage)) {
						tb.setSelectedIndex(i);
						break;
					}
				}
			}
			add(name);
			add(tb);
			setWidth("100%");
			setHeight("100%");
		}

		public String getValue() {
			return tb.getItemText(tb.getSelectedIndex());
		}

	}

	public class TlistKont extends HorizontalPanel {
		private ListBox tb;

		public TlistKont(String fieldName, List<ContragentDTO> contragents, String contragent) {
			Label name = new Label(fieldName);
			tb = new ListBox();
			for (ContragentDTO s : contragents) {
				tb.addItem(s.getName());
			}
			if (contragent != null && contragent.length() > 0) {
				int itemCount = tb.getItemCount();
				for (int i = 0; i < itemCount; i++) {
					String value = tb.getValue(i);
					if (value.equals(contragent)) {
						tb.setSelectedIndex(i);
						break;
					}
				}
			}

			add(name);
			add(tb);
			setWidth("100%");
			setHeight("100%");
		}

		public String getValue() {
			return tb.getItemText(tb.getSelectedIndex());
		}

	}

	public class Tbox1 extends HorizontalPanel {

		private TextBox tb;

		public Tbox1(String fieldName, String value) {
			Label name = new Label(fieldName);
			tb = new TextBox();
			tb.setText(value);
			add(name);
			add(tb);
			setWidth("100%");
			setHeight("100%");
		}

		public String getValue() {
			return tb.getText();
		}
	}

	public class Dbox1 extends HorizontalPanel {

		private DateBox tb;

		public Dbox1(String fieldName, Date value) {
			Label name = new Label(fieldName);
			tb = new DateBox();
			tb.setValue(value);
			add(name);
			add(tb);
			setWidth("100%");
			setHeight("100%");

		}

		public Date getValue() {
			return tb.getValue();
		}

	}

}
