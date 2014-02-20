package com.silverheart.client.table;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.Range;
import com.silverheart.shared.dto.AgreementDTO;

public class FilterListDataProvider<T> extends ListDataProvider<T> {

	private TextBox idTb;
	private TextBox contTb;
	private TextBox nameTb;

	List<TextBox> tbList = new ArrayList<TextBox>();

	public FilterListDataProvider(TextBox idTb, TextBox nameTb, TextBox contTb) {
		this.idTb = idTb;
		this.nameTb = nameTb;
		this.contTb = contTb;

		ValueChangeHandler<String> handler = new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				refresh();
			}
		};
		KeyUpHandler keyHandeler = new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				refresh();
			}
		};
		idTb.addValueChangeHandler(handler);
		nameTb.addValueChangeHandler(handler);
		contTb.addValueChangeHandler(handler);
		idTb.addKeyUpHandler(keyHandeler);
		nameTb.addKeyUpHandler(keyHandeler);
		contTb.addKeyUpHandler(keyHandeler);
	}

	@Override
	protected void updateRowData(HasData<T> display, int start, List<T> values) {
		if (!hasFilters()) { // we don't need to filter, so call base class
			super.updateRowData(display, start, values);
		} else {
			int end = start + values.size();
			Range range = display.getVisibleRange();
			int curStart = range.getStart();
			int curLength = range.getLength();
			int curEnd = curStart + curLength;
			if (start == curStart || (curStart < end && curEnd > start)) {
				int realStart = curStart < start ? start : curStart;
				int realEnd = curEnd > end ? end : curEnd;
				int realLength = realEnd - realStart;
				List<T> resulted = new ArrayList<T>(realLength);
				for (int i = realStart - start; i < realStart - start + realLength; i++) {
					AgreementDTO agreementDTO = (AgreementDTO) values.get(i);
					if (passFilter(idTb, agreementDTO.getId()) && passFilter(nameTb, agreementDTO.getName())
							&& passFilter(contTb, agreementDTO.getContragent())) {
						resulted.add((T) agreementDTO);
					}
				}
				display.setRowData(realStart, resulted);
				display.setRowCount(resulted.size());
			}
		
		}
	}

	private boolean passFilter(TextBox tb, String fieldValue) {
		if (!isValid(tb))
			return true;
		return fieldValue.toLowerCase().contains(tb.getText().toLowerCase());
	}

	public boolean hasFilters() {
		return (isValid(idTb)) || (isValid(nameTb)) || (isValid(contTb));
	}

	private boolean isValid(TextBox tb) {
		return tb != null && tb.getText().length() > 0;
	}

	public void resetFilters() {
		idTb.setText("");
		nameTb.setText("");
		contTb.setText("");
		refresh();
	}
}
