package com.silverheart.client.widgets;

import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.silverheart.client.ServiceFactory;
import com.silverheart.client.indicator.BusyIndicator;
import com.silverheart.shared.dto.AttachedFileDTO;

public class FilesPanel extends Composite {

	private String agreementId;
	private ScrollPanel sp;

	public FilesPanel(String agreementId, List<AttachedFileDTO> list) {
		this.agreementId = agreementId;
		DockPanel dock = new DockPanel();
		sp = new ScrollPanel();
		dock.add(sp, DockPanel.CENTER);
		drawCellTable(list);
		Widget add = createForm();
		dock.add(add, DockPanel.SOUTH);
		dock.setWidth("100%");
		dock.setHeight("100%");

		initWidget(dock);
	}

	private Widget createForm() {
		String parametr = "?id=" + agreementId;
		String UPLOAD_ACTION_URL = GWT.getModuleBaseURL() + "upload" + parametr;
		;
		// Create a FormPanel and point it at a service.
		final FormPanel form = new FormPanel();
		form.setAction(UPLOAD_ACTION_URL);
		// Because we're going to add a FileUpload widget, we'll
		// need to set
		// the
		// form to use the POST method, and multipart MIME encoding.
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		// Create a panel to hold all of the form widgets.
		VerticalPanel panel = new VerticalPanel();
		form.setWidget(panel);
		final FileUpload file = new FileUpload();
		file.setName("uploadFormElement");
		panel.add(file);
		Button submit = new Button("Отправить");
		panel.add(submit);

		submit.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				form.submit();
			}
		});

		form.addSubmitHandler(new SubmitHandler() {

			@Override
			public void onSubmit(SubmitEvent event) {
				if (file.getFilename().length() < 1) {
					Window.alert("Надо файл сначала выбрать");
					event.cancel();
				}
				BusyIndicator.on();

			}
		});
		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {

			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				ServiceFactory.getService().getFiles(agreementId, new AsyncCallback<List<AttachedFileDTO>>() {
					
					@Override
					public void onSuccess(List<AttachedFileDTO> result) {
						drawCellTable(result);
						BusyIndicator.off();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						BusyIndicator.off();
						Window.alert("Файлы не смог получить для Документа: " + agreementId);
					}
				});
			}
		});

		return form;
	}

	private CellTable<AttachedFileDTO> drawCellTable(List<AttachedFileDTO> list) {
		sp.clear();
		if (list.size() == 0){
			sp.add(new Label("Файлов нет"));
			return null;
		}
		CellTable<AttachedFileDTO> fileTable = new CellTable<AttachedFileDTO>(
				10);
		TextColumn<AttachedFileDTO> name = new TextColumn<AttachedFileDTO>() {

			@Override
			public String getValue(AttachedFileDTO file) {
				return file.getName() + "." + file.getExt();
			}
		};
		TextColumn<AttachedFileDTO> size = new TextColumn<AttachedFileDTO>() {

			@Override
			public String getValue(AttachedFileDTO file) {
				return file.getHrSize();
			}
		};
		ButtonCell bc = new ButtonCell();
		Column<AttachedFileDTO, String> downCol = new Column<AttachedFileDTO, String>(
				bc) {

			@Override
			public String getValue(AttachedFileDTO object) {
				return "Скачать";
			}
		};
		downCol.setFieldUpdater(new FieldUpdater<AttachedFileDTO, String>() {

			@Override
			public void update(int index, AttachedFileDTO object, String value) {
				String url = GWT.getModuleBaseURL() + "download?fileId="
						+ object.getId();
				Window.open(url, "_blank",
						"status=0,toolbar=0,menubar=0,location=0");

			}
		});
		fileTable.addColumn(name, "Имя файла");
		fileTable.addColumn(size, "Размер");
		fileTable.addColumn(downCol, "Скачать");
		fileTable.setRowData(0, list);

		sp.add(fileTable);
		return fileTable;

	}

}
