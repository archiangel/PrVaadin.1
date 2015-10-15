package com.example.prvaadin1;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("prvaadin1")
public class Prvaadin1UI extends UI {

    HorizontalSplitPanel splitPanel;
    DragAndDropWrapper wrapperA;
    DragAndDropWrapper wrapperB;

    DragAndDropWrapper splitPaneWrapper;
    Button buttonA;
    Button buttonB;
    private boolean isDragMode = false;
    
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = Prvaadin1UI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		final HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();

        Button buttonA = new Button("Button A");
        Button buttonB = new Button("Button B");

        final DragAndDropWrapper wrapperA = new DragAndDropWrapper(buttonA);
        final DragAndDropWrapper wrapperB = new DragAndDropWrapper(buttonB);

        final VerticalLayout leftPanel = new VerticalLayout();
        final VerticalLayout rightPanel = new VerticalLayout();

        DragAndDropWrapper leftPanelWrapper = new DragAndDropWrapper(leftPanel);
        DragAndDropWrapper rightPanelWrapper = new     DragAndDropWrapper(rightPanel);

        buttonA.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Button A was clicked");

            }

        });
        
        buttonB.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Button B was clicked");

            }

        });

        leftPanelWrapper.setDropHandler(new DropHandler() {

            @Override
            public void drop(DragAndDropEvent event) {
                leftPanel.addComponent(event.getTransferable().getSourceComponent());

            }

            @Override
            public AcceptCriterion getAcceptCriterion() {
                return AcceptAll.get();
            }

        });

        rightPanelWrapper.setDropHandler(new DropHandler() {

            @Override
            public void drop(DragAndDropEvent event) {
                rightPanel.addComponent(event.getTransferable().getSourceComponent());

            }

            @Override
            public AcceptCriterion getAcceptCriterion() {
                return AcceptAll.get();
            }

        });

        final Button dragMode = new Button("Drag Mode On");

        dragMode.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                isDragMode = !isDragMode;
                if (isDragMode) {
                    dragMode.setCaption("Drag Mode Off");
                    wrapperA.setDragStartMode(DragStartMode.WRAPPER);
                    wrapperB.setDragStartMode(DragStartMode.WRAPPER);
                } else {
                    dragMode.setCaption("Drag Mode On");
                    wrapperA.setDragStartMode(DragStartMode.NONE);
                    wrapperB.setDragStartMode(DragStartMode.NONE);
                }

            }

        });

        leftPanel.setSizeFull();
        rightPanel.setSizeFull();
        leftPanelWrapper.setSizeFull();
        rightPanelWrapper.setSizeFull();

        leftPanel.addComponent(wrapperA);
        rightPanel.addComponent(wrapperB);

        splitPanel.setFirstComponent(leftPanelWrapper);
        splitPanel.setSecondComponent(rightPanelWrapper);
        splitPanel.setSizeFull();

        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(dragMode);
        layout.addComponent(splitPanel);
        layout.setSizeFull();

        this.setContent(layout);
        this.setSizeFull();
	}

}