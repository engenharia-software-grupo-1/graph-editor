package io.github.eckig.grapheditor.core;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;

import io.github.eckig.grapheditor.model.GConnection;
import io.github.eckig.grapheditor.model.GNode;
import io.github.eckig.grapheditor.core.connections.ConnectionEventManager;
import io.github.eckig.grapheditor.utils.RemoveContext;

public class GraphEditorEventManager {

    private final ConnectionEventManager connectionEventManager;
    private final ModelEditingManager modelEditingManager;

    public GraphEditorEventManager(
            ConnectionEventManager connectionEventManager,
            ModelEditingManager modelEditingManager) {

        this.connectionEventManager = connectionEventManager;
        this.modelEditingManager = modelEditingManager;
    }

    public void setOnConnectionCreated(Function<GConnection, Command> consumer) {
        connectionEventManager.setOnConnectionCreated(consumer);
    }

    public void setOnConnectionRemoved(
            BiFunction<RemoveContext, GConnection, Command> callback) {

        connectionEventManager.setOnConnectionRemoved(callback);
        modelEditingManager.setOnConnectionRemoved(callback);
    }

    public void setOnNodeRemoved(
            BiFunction<RemoveContext, GNode, Command> callback) {

        modelEditingManager.setOnNodeRemoved(callback);
    }

    public void delete(Collection<EObject> items) {
        modelEditingManager.remove(items);
    }
}