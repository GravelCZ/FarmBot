package cz.GravelCZLP.MinecraftBot.Entites;

import java.util.UUID;

import org.spacehq.mc.protocol.data.game.entity.metadata.Position;
import org.spacehq.mc.protocol.data.game.entity.type.PaintingType;
import org.spacehq.mc.protocol.data.game.entity.type.object.HangingDirection;

public class Painting {
	
	private int entityId;
	private UUID uuid;
	private PaintingType type;
	private HangingDirection direction;
	private Position position;
	
	public Painting(int entityId, UUID uuid, PaintingType type, HangingDirection direction, Position position) {
		super();
		this.entityId = entityId;
		this.uuid = uuid;
		this.type = type;
		this.direction = direction;
		this.position = position;
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public PaintingType getType() {
		return type;
	}

	public void setType(PaintingType type) {
		this.type = type;
	}

	public HangingDirection getDirection() {
		return direction;
	}

	public void setDirection(HangingDirection direction) {
		this.direction = direction;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
}
