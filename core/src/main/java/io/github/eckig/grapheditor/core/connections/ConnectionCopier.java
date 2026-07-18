package io.github.eckig.grapheditor.core.connections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;

import io.github.eckig.grapheditor.model.GConnection;
import io.github.eckig.grapheditor.model.GConnector;
import io.github.eckig.grapheditor.model.GNode;


/**
 * Helper methods to copy {@link GConnection connections}
 */
public final class ConnectionCopier
{

    /**
     * Static class.
     */
    private ConnectionCopier()
    {
    }

    /**
     * Copies connection information from one set of nodes to another.
     *
     * <p>
     * Connections between nodes in the <b>keys</b> of the input map are copied
     * (including their joint information) and the new connections are set
     * inside the corresponding nodes in the <b>values</b> of the input map.
     * </p>
     *
     * <p>
     * The new connection information is set <em>directly</em>. EMF commands are
     * not used
     * </p>
     *
     * @param copies
     *            a map that links source nodes to their copies in its key-value
     *            pairs
     * @return the list of created connections
     */
    public static List<GConnection> copyConnections(final Map<GNode, GNode> copies)
    {
        final Map<GConnection, GConnection> copiedConnections = new HashMap<>();

        for (final var entry : copies.entrySet())
        {
            copyNodeConnections(entry.getKey(), entry.getValue(), copies, copiedConnections);
        }

        return new ArrayList<>(copiedConnections.values());
    }

    private static void copyNodeConnections(
            final GNode originalNode,
            final GNode copiedNode,
            final Map<GNode, GNode> copies,
            final Map<GConnection, GConnection> copiedConnections)
    {
        for (final var connector : originalNode.getConnectors())
        {
            final var connectorIndex = originalNode.getConnectors().indexOf(connector);
            final var copiedConnector = copiedNode.getConnectors().get(connectorIndex);

            copiedConnector.getConnections().clear();

            copyConnectorConnections(
                    connector,
                    copiedConnector,
                    copies,
                    copiedConnections);
        }
    }

    private static void copyConnectorConnections(
            final GConnector connector,
            final GConnector copiedConnector,
            final Map<GNode, GNode> copies,
            final Map<GConnection, GConnection> copiedConnections)
    {
        for (final var connection : connector.getConnections())
        {
            final var opposingNode = getOpposingNode(connector, connection);

            if (!copies.containsKey(opposingNode))
            {
                continue;
            }

            final var copiedConnection = getOrCreateCopiedConnection(
                    connection,
                    copiedConnections);

            updateConnectionEndpoint(
                    connection,
                    connector,
                    copiedConnector,
                    copiedConnection);

            copiedConnector.getConnections().add(copiedConnection);
        }
    }

    private static GConnection getOrCreateCopiedConnection(
            final GConnection connection,
            final Map<GConnection, GConnection> copiedConnections)
    {
        if (!copiedConnections.containsKey(connection))
        {
            copiedConnections.put(connection, EcoreUtil.copy(connection));
        }

        return copiedConnections.get(connection);
    }

    private static void updateConnectionEndpoint(
            final GConnection originalConnection,
            final GConnector originalConnector,
            final GConnector copiedConnector,
            final GConnection copiedConnection)
    {
        if (originalConnection.getSource().equals(originalConnector))
        {
            copiedConnection.setSource(copiedConnector);
        }
        else
        {
            copiedConnection.setTarget(copiedConnector);
        }
    }

    /**
     * Gets the node on the other side of the connection to the given connector.
     *
     * @param connector
     *            a {@link GConnector} instance
     * @param connection
     *            a {@link GConnection} attached to this connector
     * @return the {@link GNode} on the other side of the connection, or
     *         {@code null} if none exists
     */
    private static GNode getOpposingNode(final GConnector connector, final GConnection connection)
    {
        GConnector opposingConnector;
        if (connection.getSource().equals(connector))
        {
            opposingConnector = connection.getTarget();
        }
        else
        {
            opposingConnector = connection.getSource();
        }

        if (opposingConnector != null && opposingConnector.getParent() instanceof GNode)
        {
            return opposingConnector.getParent();
        }
        else
        {
            return null;
        }
    }
}
