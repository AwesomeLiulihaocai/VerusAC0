package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity.EntityUseAction;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
        type = CheckType.BAD_PACKETS,
        subType = "C",
        friendlyName = "KillAura",
        version = CheckVersion.RELEASE,
        unsupportedAtleast = ClientVersion.V1_9,
        unsupportedServerAtleast = ServerVersion.v1_11_R1
)
public class BadPacketsC extends Check implements PacketHandler {

    private boolean received;

    public void handle(VPacketPlayInArmAnimation vPacket) {
        this.received = true;
    }

    public void handle(VPacketPlayInFlying vPacket) {
        this.received = false;
    }

    public void handle(VPacketPlayInUseEntity vPacket) {
        if (vPacket.getAction() == EntityUseAction.ATTACK && this.playerData.isSurvival()) {
            if (!this.received) {
                this.handleViolation();
            }
            this.received = false;
        }
    }
}