package com.silverheart.client.widgets;

import java.util.Comparator;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.silverheart.client.Good;
import com.silverheart.client.ServiceFactory;
import com.silverheart.client.indicator.BusyIndicator;
import com.silverheart.client.table.FilterListDataProvider;
import com.silverheart.shared.dto.AgreementDTO;
import com.silverheart.shared.dto.AgreementDTO.AgreementCols;
import com.silverheart.shared.dto.AgreementWholeDTO;
import com.silverheart.shared.dto.ContragentDTO;

public class CellTablePanel extends VerticalPanel {

	private TextBox idTb;
	private TextBox nameTb;
	private TextBox contTb;
	private List<AgreementDTO> result;
	private Good good;

	public CellTablePanel(List<AgreementDTO> result, Good good) {
		this.result = result;
		this.good = good;
		addFilters();

		addTable();
		setWidth("100%");
		setHeight("100%");

	}

	private void addTable() {
		CellTable<AgreementDTO> cellTable = new CellTable<AgreementDTO>();
		TextColumn<AgreementDTO> id = new TextColumn<AgreementDTO>() {

			@Override
			public String getValue(AgreementDTO a) {
				return a.getId();
			}
		};
		TextColumn<AgreementDTO> name = new TextColumn<AgreementDTO>() {

			@Override
			public String getValue(AgreementDTO a) {
				return a.getName();
			}
		};
		TextColumn<AgreementDTO> contragent = new TextColumn<AgreementDTO>() {

			@Override
			public String getValue(AgreementDTO a) {
				return a.getContragent();
			}
		};
		TextColumn<AgreementDTO> stage = new TextColumn<AgreementDTO>() {

			@Override
			public String getValue(AgreementDTO a) {
				return a.getStage();
			}
		};
		TextColumn<AgreementDTO> date = new TextColumn<AgreementDTO>() {

			@Override
			public String getValue(AgreementDTO a) {
				return Good.getDateStr(a.getDate());
			}
		};
		cellTable.addColumn(id, AgreementCols.ID);
		cellTable.addColumn(name, AgreementCols.NAME);
		name.setSortable(true);
		cellTable.addColumn(contragent, AgreementCols.KONTRAGENT);
		cellTable.addColumn(stage, AgreementCols.STAGE);
		cellTable.addColumn(date, AgreementCols.DATE);
		date.setSortable(true);

		// cellTable.setRowData(0, result);
		cellTable.addCellPreviewHandler(new Handler<AgreementDTO>() {

			@Override
			public void onCellPreview(CellPreviewEvent<AgreementDTO> event) {
				boolean isClick = "click".equals(event.getNativeEvent().getType());
				if (isClick) {
					BusyIndicator.on();
					ServiceFactory.getService().getAgreement(event.getValue().getId(),
							new AsyncCallback<AgreementWholeDTO>() {

								@Override
								public void onFailure(Throwable caught) {
									BusyIndicator.off();
									Window.alert("Чето случилось договор не отрисовался");

								}

								@Override
								public void onSuccess(AgreementWholeDTO result) {
									good.drawPanel(new AgreementPanel(result, good));
									BusyIndicator.off();

								}
							});
				}

			}

		});

		FilterListDataProvider<AgreementDTO> dataProvider = new FilterListDataProvider<AgreementDTO>(idTb, nameTb,
				contTb);
		dataProvider.addDataDisplay(cellTable);
		List<AgreementDTO> list = dataProvider.getList();
		list.addAll(result);

		ListHandler<AgreementDTO> dateColHandler = new ListHandler<AgreementDTO>(list);
		dateColHandler.setComparator(date, new Comparator<AgreementDTO>() {

			@Override
			public int compare(AgreementDTO o1, AgreementDTO o2) {
				return (int) (o1.getDate().getTime() - o2.getDate().getTime());
			}
		});
		ListHandler<AgreementDTO> nameColHandler = new ListHandler<AgreementDTO>(list);

		cellTable.addColumnSortHandler(nameColHandler);
		nameColHandler.setComparator(name, new Comparator<AgreementDTO>() {

			@Override
			public int compare(AgreementDTO o1, AgreementDTO o2) {

				return o1.getName().length() - o2.getName().length();
			}
		});
		cellTable.getColumnSortList().push(name);
		cellTable.addColumnSortHandler(dateColHandler);
		cellTable.getColumnSortList().push(date);
		cellTable.setWidth("100%");
		cellTable.setHeight("100%");
		add(cellTable);
		add(aggremAdd());
	}

	private Widget aggremAdd() {
		Button add = new Button("Добавить договор", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				BusyIndicator.on();
				ServiceFactory.getService().getContragents(new AsyncCallback<List<ContragentDTO>>() {

					@Override
					public void onSuccess(List<ContragentDTO> result) {
						good.drawPanel(new EditAgreement(null, result, good));
						BusyIndicator.off();
					}

					@Override
					public void onFailure(Throwable caught) {
						BusyIndicator.off();
						Window.alert("Чето случилось добавлялка не хочет работать");

					}
				});

			}
		});
		return add;
	}

	private void addFilters() {
		Label idLabel = new Label(AgreementCols.ID);
		idTb = new TextBox();
		Label nameLabel = new Label(AgreementCols.NAME);
		nameTb = new TextBox();
		Label contLabel = new Label(AgreementCols.KONTRAGENT);
		contTb = new TextBox();

		HorizontalPanel hp = new HorizontalPanel();
		VerticalPanel vpId = new VerticalPanel();
		vpId.add(idLabel);
		vpId.add(idTb);

		VerticalPanel vpName = new VerticalPanel();
		vpName.add(nameLabel);
		vpName.add(nameTb);

		VerticalPanel vpCont = new VerticalPanel();
		vpCont.add(contLabel);
		vpCont.add(contTb);

		hp.add(vpId);
		hp.add(vpName);
		hp.add(vpCont);

		hp.setCellWidth(vpId, "33%");
		hp.setCellWidth(vpName, "33%");
		hp.setCellWidth(vpCont, "34%");
		hp.setCellHorizontalAlignment(vpId, HasHorizontalAlignment.ALIGN_LEFT);
		hp.setCellHorizontalAlignment(vpName, HasHorizontalAlignment.ALIGN_CENTER);
		hp.setCellHorizontalAlignment(vpCont, HasHorizontalAlignment.ALIGN_RIGHT);
		hp.setWidth("100%");
		add(hp);
	}
}
