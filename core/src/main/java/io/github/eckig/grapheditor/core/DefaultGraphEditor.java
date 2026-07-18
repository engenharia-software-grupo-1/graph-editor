/*
 * Copyright (C) 2005 - 2014 by TESIS DYNAware GmbH
 */
package io.github.eckig.grapheditor.core;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;

import io.github.eckig.grapheditor.GConnectionSkin;
import io.github.eckig.grapheditor.GConnectorSkin;
import io.github.eckig.grapheditor.GConnectorValidator;
import io.github.eckig.grapheditor.GJointSkin;
import io.github.eckig.grapheditor.GNodeSkin;
import io.github.eckig.grapheditor.GTailSkin;
import io.github.eckig.grapheditor.GraphEditor;
import io.github.eckig.grapheditor.SelectionManager;
import io.github.eckig.grapheditor.SkinLookup;
import io.github.eckig.grapheditor.core.connections.ConnectionEventManager;
import io.github.eckig.grapheditor.core.view.GraphEditorView;
import io.github.eckig.grapheditor.model.GConnection;
import io.github.eckig.grapheditor.model.GConnector;
import io.github.eckig.grapheditor.model.GJoint;
import io.github.eckig.grapheditor.model.GModel;
import io.github.eckig.grapheditor.model.GNode;
import io.github.eckig.grapheditor.utils.GraphEditorProperties;
import io.github.eckig.grapheditor.utils.RemoveContext;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.scene.layout.Region;
import javafx.util.Callback;


/**
 * Default implementation of the {@link GraphEditor}.
 */
public class DefaultGraphEditor implements GraphEditor
{

    private final GraphEditorProperties mProperties;
    private final GraphEditorView mView;
    private final ConnectionEventManager mConnectionEventManager = new ConnectionEventManager();
    private final GraphEditorController<DefaultGraphEditor> mController;
    private final GraphEditorConfiguration configuration;
    private final GraphEditorEventManager eventManager;

    private final ObjectProperty<GModel> mModelProperty = new ObjectPropertyBase<>()
    {

        @Override
        public Object getBean()
        {
            return DefaultGraphEditor.this;
        }

        @Override
        public String getName()
        {
            return "model";
        }

    };

    /**
     * Creates a new default implementation of the {@link GraphEditor}.
     */
    public DefaultGraphEditor()
    {
        this(null);
    }

    /**
     * Creates a new default implementation of the {@link GraphEditor}.
     */
    public DefaultGraphEditor(final GraphEditorProperties pProperties)
    {
        mProperties = pProperties == null ? new GraphEditorProperties() : pProperties;
        mView = new GraphEditorView(mProperties);
        mController = new GraphEditorController<>(this, mView, mConnectionEventManager, mProperties);
        configuration = new GraphEditorConfiguration(mController);
        eventManager = new GraphEditorEventManager(
        mConnectionEventManager,
        mController.getModelEditingManager());
    }

    @Override
    public void setNodeSkinFactory(final Callback<GNode, GNodeSkin> pSkinFactory)
    {
        configuration.setNodeSkinFactory(pSkinFactory);
    }

    @Override
    public void setConnectorSkinFactory(final Callback<GConnector, GConnectorSkin> pConnectorSkinFactory)
    {
        configuration.setConnectorSkinFactory(pConnectorSkinFactory);
    }

    @Override
    public void setConnectionSkinFactory(final Callback<GConnection, GConnectionSkin> pConnectionSkinFactory)
    {
        configuration.setConnectionSkinFactory(pConnectionSkinFactory);
    }

    @Override
    public void setJointSkinFactory(final Callback<GJoint, GJointSkin> pJointSkinFactory)
    {
        configuration.setJointSkinFactory(pJointSkinFactory);
    }

    @Override
    public void setTailSkinFactory(final Callback<GConnector, GTailSkin> pTailSkinFactory)
    {
        configuration.setTailSkinFactory(pTailSkinFactory);
    }

    @Override
    public void setConnectorValidator(final GConnectorValidator pValidator)
    {
        configuration.setConnectorValidator(pValidator);
    }

    @Override
    public void setModel(final GModel pModel)
    {
        mModelProperty.set(pModel);
    }

    @Override
    public GModel getModel()
    {
        return mModelProperty.get();
    }

    @Override
    public void flush()
    {
        mController.flush();
    }

    @Override
    public ObjectProperty<GModel> modelProperty()
    {
        return mModelProperty;
    }

    @Override
    public Region getView()
    {
        return mView;
    }

    @Override
    public GraphEditorProperties getProperties()
    {
        return mProperties;
    }

    @Override
    public SkinLookup getSkinLookup()
    {
        return mController.getSkinManager();
    }

    @Override
    public SelectionManager getSelectionManager()
    {
        return mController.getSelectionManager();
    }

    @Override
    public void setOnConnectionCreated(final Function<GConnection, Command> pConsumer)
    {
        eventManager.setOnConnectionCreated(pConsumer);
    }

    @Override
    public void setOnConnectionRemoved(final BiFunction<RemoveContext, GConnection, Command> pOnConnectionRemoved)
    {
        eventManager.setOnConnectionRemoved(pOnConnectionRemoved);
    }

    @Override
    public void setOnNodeRemoved(final BiFunction<RemoveContext, GNode, Command> pOnNodeRemoved)
    {
        eventManager.setOnNodeRemoved(pOnNodeRemoved);
    }

    @Override
    public void delete(Collection<EObject> pItems)
    {
        eventManager.delete(pItems);
    }

}
