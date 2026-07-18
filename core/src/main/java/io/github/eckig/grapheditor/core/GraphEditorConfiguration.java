package io.github.eckig.grapheditor.core;

import io.github.eckig.grapheditor.GConnectionSkin;
import io.github.eckig.grapheditor.GConnectorSkin;
import io.github.eckig.grapheditor.GConnectorValidator;
import io.github.eckig.grapheditor.GJointSkin;
import io.github.eckig.grapheditor.GNodeSkin;
import io.github.eckig.grapheditor.GTailSkin;
import io.github.eckig.grapheditor.model.GConnection;
import io.github.eckig.grapheditor.model.GConnector;
import io.github.eckig.grapheditor.model.GJoint;
import io.github.eckig.grapheditor.model.GNode;
import javafx.util.Callback;

public class GraphEditorConfiguration {

    private final GraphEditorController<?> controller;

    public GraphEditorConfiguration(GraphEditorController<?> controller) {
        this.controller = controller;
    }

    public void setNodeSkinFactory(Callback<GNode, GNodeSkin> factory) {
        controller.getSkinManager().setNodeSkinFactory(factory);
    }

    public void setConnectorSkinFactory(Callback<GConnector, GConnectorSkin> factory) {
        controller.getSkinManager().setConnectorSkinFactory(factory);
    }

    public void setConnectionSkinFactory(Callback<GConnection, GConnectionSkin> factory) {
        controller.getSkinManager().setConnectionSkinFactory(factory);
    }

    public void setJointSkinFactory(Callback<GJoint, GJointSkin> factory) {
        controller.getSkinManager().setJointSkinFactory(factory);
    }

    public void setTailSkinFactory(Callback<GConnector, GTailSkin> factory) {
        controller.getSkinManager().setTailSkinFactory(factory);
    }

    public void setConnectorValidator(GConnectorValidator validator) {
        controller.setConnectorValidator(validator);
    }
}