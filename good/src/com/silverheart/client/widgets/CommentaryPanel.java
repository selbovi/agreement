package com.silverheart.client.widgets;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.silverheart.client.Good;
import com.silverheart.client.ServiceFactory;
import com.silverheart.shared.dto.CommentaryDTO;
import com.silverheart.shared.dto.CommentaryDTO.Names;

public class CommentaryPanel extends Composite {
	private List<CommentaryDTO> comments;
	private String agreementId;
	LayoutPanel main = new LayoutPanel();

	public CommentaryPanel(String agreementId, List<CommentaryDTO> comments) {
		this.agreementId = agreementId;
		this.comments = comments;

		draw();

		initWidget(main);

	}

	private Widget createCommentsPanel() {
		VerticalPanel vp = new VerticalPanel();
		for (CommentaryDTO dto : comments) {
			Label lable = new Label(Good.getDateStr(dto.getDate()), true);
			lable.addStyleName("justBold");
			vp.add(lable);
			Label comment = new Label(dto.getComment(), true);
			comment.addStyleName("comment");
			vp.add(comment);
			vp.add(new Label("", true));
		}
		final ScrollPanel scrollPanel = new ScrollPanel(vp);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			
			@Override
			public void execute() {
				scrollPanel.scrollToBottom();
				
			}
		});
		return scrollPanel;

	}

	private Widget createCommentAddPanel() {
		VerticalPanel vp = new VerticalPanel();
		final TextArea area = new TextArea();
		Button add = new Button("Добавить");
		vp.setWidth("100%");
		add.setWidth("100%");
		area.setWidth("100%");
		vp.add(area);
		vp.add(add);
		add.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String text = area.getText();
				if (text.length() < 1)
					Window.alert("Заполните поле комментария");
				else {
					final CommentaryDTO dto = new CommentaryDTO(agreementId,
							text, new Date());
					ServiceFactory.getService().addComment(dto,
							new AsyncCallback<Void>() {

								@Override
								public void onSuccess(Void result) {
									comments.add(dto);
									draw();

								}

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Чето случилось и коммент не добавился");
								}
							});

				}
			}
		});
		return vp;

	}

	private void draw() {
		main.clear();

		Label commentLabel = new Label(Names.HEADER.title);
		commentLabel.addStyleName("headings");
		
		main.add(commentLabel);


		Widget allComments = createCommentsPanel();
		main.add(allComments);
		
		Widget addingCommentPanel = createCommentAddPanel();
		main.add(addingCommentPanel);
		main.setWidgetTopHeight(commentLabel, 0, Unit.PX, 30, Unit.PX);
		main.setWidgetTopBottom(allComments, 30, Unit.PX, 100, Unit.PX);
		main.setWidgetBottomHeight(addingCommentPanel, 0, Unit.PX, 100, Unit.PX);
		main.setWidgetLeftRight(addingCommentPanel, 0, Unit.PX, 0, Unit.PX);
	}

}
