package io.github.eckig.grapheditor.core;

import io.github.eckig.grapheditor.core.connections.ConnectorDragManager;
import io.github.eckig.grapheditor.core.model.ModelLayoutUpdater;
import io.github.eckig.grapheditor.core.selections.DefaultSelectionManager;
import io.github.eckig.grapheditor.core.skins.GraphEditorSkinManager;
import io.github.eckig.grapheditor.model.GConnection;
import io.github.eckig.grapheditor.model.GConnector;
import io.github.eckig.grapheditor.model.GJoint;
import io.github.eckig.grapheditor.model.GNode;

public class GraphElementManager {

    private final GraphEditorSkinManager skinManager;
    private final DefaultSelectionManager selectionManager;
    private final ModelLayoutUpdater layoutUpdater;
    private final ConnectorDragManager connectorDragManager;

    public GraphElementManager(
            GraphEditorSkinManager skinManager,
            DefaultSelectionManager selectionManager,
            ModelLayoutUpdater layoutUpdater,
            ConnectorDragManager connectorDragManager) {

        this.skinManager = skinManager;
        this.selectionManager = selectionManager;
        this.layoutUpdater = layoutUpdater;
        this.connectorDragManager = connectorDragManager;
    }

    public void addNode(GNode node) {

        skinManager.lookupOrCreateNode(node);

        for (GConnector connector : node.getConnectors()) {
            addConnector(connector);
        }
    }

    public void removeNode(GNode node) {

        for (GConnector connector : node.getConnectors()) {
            removeConnector(connector);
        }

        selectionManager.removeNode(node);
        selectionManager.clearSelection(node);

        layoutUpdater.removeNode(node);

        skinManager.removeNode(node);
    }


    public void addConnector(GConnector connector) {

        skinManager.lookupOrCreateConnector(connector);
    }

    public void removeConnector(GConnector connector) {

        selectionManager.removeConnector(connector);

        connectorDragManager.removeConnector(connector);

        skinManager.removeConnector(connector);
    }


    public void addConnection(GConnection connection) {

        skinManager.lookupOrCreateConnection(connection);
    }

    public void removeConnection(GConnection connection) {

        selectionManager.removeConnection(connection);

        selectionManager.clearSelection(connection);

        skinManager.removeConnection(connection);

        for (GJoint joint : connection.getJoints()) {
            removeJoint(joint);
        }
    }


    public void addJoint(GJoint joint) {

        layoutUpdater.addJoint(joint);

        selectionManager.addJoint(joint);

        skinManager.updateJoints(joint.getConnection());
    }

    public void removeJoint(GJoint joint) {

        selectionManager.removeJoint(joint);

        selectionManager.clearSelection(joint);

        layoutUpdater.removeJoint(joint);

        skinManager.removeJoint(joint);
    }
}