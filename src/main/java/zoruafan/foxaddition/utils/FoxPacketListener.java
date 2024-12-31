package zoruafan.foxaddition.utils;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.packettype.PacketTypeCommon;
import com.github.retrooper.packetevents.protocol.player.DiggingAction;
import com.github.retrooper.packetevents.protocol.player.InteractionHand;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.protocol.world.BlockFace;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.util.Vector3f;
import com.github.retrooper.packetevents.util.Vector3i;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientAnimation;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEntityAction;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEntityAction.Action;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientHeldItemChange;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity.InteractAction;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientKeepAlive;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerAbilities;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerBlockPlacement;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerDigging;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerPosition;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerPositionAndRotation;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerRotation;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientSteerVehicle;
import io.github.retrooper.packetevents.util.folia.FoliaScheduler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import zoruafan.foxaddition.FoxAdditionAPI;
import zoruafan.foxaddition.utils.listeners.*;
import java.util.Optional;

public class FoxPacketListener extends FoliaScheduler implements PacketListener {
    FoxAdditionAPI api = FoxAdditionAPI.INSTANCE;
    JavaPlugin pl = api.getPlugin();
    
    public void onPacketReceive(PacketReceiveEvent ev) {
    	User user = ev.getUser();
    	if (user.getUUID() == null) { return; }
        Player e = Bukkit.getPlayer(user.getUUID());
        if (e == null) { return; }
        PacketTypeCommon p = ev.getPacketType();
        
        if (p == PacketType.Play.Client.PLAYER_ABILITIES) {
        	WrapperPlayClientPlayerAbilities a = new WrapperPlayClientPlayerAbilities(ev);
    	    final Optional<Boolean> flying = Optional.of(a.isFlying());
    	    final Optional<Boolean> godMode = a.isInGodMode();
    	    final Optional<Boolean> flightAllowed = a.isFlightAllowed();
    	    final Optional<Boolean> creativeMode = a.isInCreativeMode();
    	    final Optional<Float> flySpeed = a.getFlySpeed();
    	    final Optional<Float> walkSpeed = a.getWalkSpeed();
		    AbilitiesEvent abi = new AbilitiesEvent(e, flying, godMode, flightAllowed, creativeMode, flySpeed, walkSpeed);
		    Bukkit.getServer().getPluginManager().callEvent(abi);
		    if (abi.isCancelled()) { ev.setCancelled(true); }
        } else if (p == PacketType.Play.Client.PLAYER_DIGGING) {
        	WrapperPlayClientPlayerDigging d = new WrapperPlayClientPlayerDigging(ev);
        	Vector3i b = d.getBlockPosition();
    		int x = b.getX();
    		int y = b.getY();
    		int z = b.getZ();
        	Vector3i bP = new Vector3i(x, y, z);
        	BlockFace f = d.getBlockFace();
        	DiggingAction a = d.getAction();
        	BlockDigEvent bde = new BlockDigEvent(e, a, bP, f);
	        Bukkit.getServer().getPluginManager().callEvent(bde);
	        if (bde.isCancelled()) { ev.setCancelled(true); }
        } else if (p == PacketType.Play.Client.PLAYER_BLOCK_PLACEMENT) {
        	WrapperPlayClientPlayerBlockPlacement bp = new WrapperPlayClientPlayerBlockPlacement(ev);
        	Vector3i b = bp.getBlockPosition();
    		int x = b.getX();
    		int y = b.getY();
    		int z = b.getZ();
        	Vector3i bP = new Vector3i(x, y, z);
        	BlockFace f = bp.getFace();
        	long tm = bp.getSequence();
        	InteractionHand hand = bp.getHand();
        	BlockPlaceEvent bpe = new BlockPlaceEvent(e, bP, f, tm, hand);
        	Bukkit.getServer().getPluginManager().callEvent(bpe);
        	if (bpe.isCancelled()) { ev.setCancelled(true); }
        } else if (p == PacketType.Play.Client.KEEP_ALIVE) {
        	WrapperPlayClientKeepAlive k = new WrapperPlayClientKeepAlive(ev);
        	long id = k.getId();
        	KeepAliveEvent kae = new KeepAliveEvent(e, id);
	    	Bukkit.getServer().getPluginManager().callEvent(kae);
	    	if (kae.isCancelled()) { ev.setCancelled(true); }
        } else if (p == PacketType.Play.Client.HELD_ITEM_CHANGE) {
        	WrapperPlayClientHeldItemChange hi = new WrapperPlayClientHeldItemChange(ev);
        	int slot = hi.getSlot();
	    	HeldItemEvent hie = new HeldItemEvent(e, slot);
	    	Bukkit.getServer().getPluginManager().callEvent(hie);
	    	if (hie.isCancelled()) { ev.setCancelled(true); }
        } else if (p == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION) {
        	WrapperPlayClientPlayerPositionAndRotation po = new WrapperPlayClientPlayerPositionAndRotation(ev);
        	boolean onGround = po.isOnGround();
        	Vector3d X = po.getPosition();
        	double xP = X.getX();
        	double yP = X.getY();
        	double zP = X.getZ();
        	float wP = po.getYaw();
        	float pP = po.getPitch();
        	PositionType ty = PositionType.POSITION_ROTATION;
        	boolean iF = WrapperPlayClientPlayerFlying.isFlying(ev.getPacketType());
	    	PositionEvent pos = new PositionEvent(e, xP, yP, zP, wP, pP, onGround, iF, ev, ty);
	    	Bukkit.getServer().getPluginManager().callEvent(pos);
	    	if (pos.isCancelled()) { ev.setCancelled(true); }
        	LookEvent loe = new LookEvent(e, (float) wP, (float) pP);
           	Bukkit.getServer().getPluginManager().callEvent(loe);
        	if (loe.isCancelled()) { ev.setCancelled(true); }
        } else if (p == PacketType.Play.Client.PLAYER_POSITION) {
        	WrapperPlayClientPlayerPosition po = new WrapperPlayClientPlayerPosition(ev);
        	boolean onGround = po.isOnGround();
        	Vector3d X = po.getPosition();
        	double xP = X.getX();
        	double yP = X.getY();
        	double zP = X.getZ();
        	float wP = e.getLocation().getYaw();
        	float pP = e.getLocation().getPitch();
        	PositionType ty = PositionType.POSITION;
        	boolean iF = WrapperPlayClientPlayerFlying.isFlying(ev.getPacketType());
	    	PositionEvent pos = new PositionEvent(e, xP, yP, zP, wP, pP, onGround, iF, ev, ty);
	    	Bukkit.getServer().getPluginManager().callEvent(pos);
	    	if (pos.isCancelled()) { ev.setCancelled(true); }
        } else if (p == PacketType.Play.Client.INTERACT_ENTITY) {
        	WrapperPlayClientInteractEntity ie = new WrapperPlayClientInteractEntity(ev);
        	int id = ie.getEntityId();
        	InteractAction ac = ie.getAction();
        	InteractionHand ha = ie.getHand();
        	Optional<Vector3f> ta = ie.getTarget();
        	Optional<Boolean> sn = ie.isSneaking();
        	InteractEntityEvent iee = new InteractEntityEvent(e, id, ac, ha, ta, sn);
	    	Bukkit.getServer().getPluginManager().callEvent(iee);
	    	if (iee.isCancelled()) { ev.setCancelled(true); }
        } else if(p == PacketType.Play.Client.STEER_VEHICLE) {
        	WrapperPlayClientSteerVehicle sv = new WrapperPlayClientSteerVehicle(ev);
        	float fo = sv.getForward();
        	float si = sv.getSideways();
        	byte fl = sv.getFlags();
        	SteerVehicleEvent sve = new SteerVehicleEvent(e, fo, si, fl);
	    	Bukkit.getServer().getPluginManager().callEvent(sve);
	    	if (sve.isCancelled()) { ev.setCancelled(true); }
        } else if(p == PacketType.Play.Client.ENTITY_ACTION) {
        	WrapperPlayClientEntityAction ea = new WrapperPlayClientEntityAction(ev);
        	int eI = ea.getEntityId();
        	Action ac = ea.getAction();
        	int jB = ea.getJumpBoost();
        	EntityActionEvent eae = new EntityActionEvent(e, eI, ac, jB);
	    	Bukkit.getServer().getPluginManager().callEvent(eae);
	    	if (eae.isCancelled()) { ev.setCancelled(true); }
        } else if(p == PacketType.Play.Client.ANIMATION) {
        	WrapperPlayClientAnimation an = new WrapperPlayClientAnimation(ev);
        	InteractionHand ih = an.getHand();
        	AnimationEvent eae = new AnimationEvent(e, ih);
        	Bukkit.getServer().getPluginManager().callEvent(eae);
        	if (eae.isCancelled()) { ev.setCancelled(true); }
        } else if (p == PacketType.Play.Client.PLAYER_ROTATION) {
        	WrapperPlayClientPlayerRotation pr = new WrapperPlayClientPlayerRotation(ev);
        	double pitch = pr.getPitch();
        	double yaw = pr.getYaw();
        	LookEvent loe = new LookEvent(e, (float) yaw, (float) pitch);
           	Bukkit.getServer().getPluginManager().callEvent(loe);
        	if (loe.isCancelled()) { ev.setCancelled(true); }
    	}
        else return;
    }
}
