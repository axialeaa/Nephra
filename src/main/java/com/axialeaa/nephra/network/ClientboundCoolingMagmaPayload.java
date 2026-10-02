package com.axialeaa.nephra.network;

import com.axialeaa.nephra.Nephra;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;

import java.util.List;

public record ClientboundCoolingMagmaPayload(BlockPos pos) implements CustomPacketPayload {

	private static final double SEND_RANGE = 32.0;

    public static final Type<ClientboundCoolingMagmaPayload> TYPE = Nephra.payloadType("clientbound/cooling_magma_v1");
    public static final StreamCodec<FriendlyByteBuf, ClientboundCoolingMagmaPayload> STREAM_CODEC = StreamCodec.composite(
		BlockPos.STREAM_CODEC, ClientboundCoolingMagmaPayload::pos,
		ClientboundCoolingMagmaPayload::new
	);

    @Override
	public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

	public static void sendToNearbyPlayers(ServerLevel level, BlockPos pos) {
		List<ServerPlayer> players = level.getPlayers(player -> pos.closerToCenterThan(player.position(), SEND_RANGE));

		for (ServerPlayer player : players)
			ServerPlayNetworking.send(player, new ClientboundCoolingMagmaPayload(pos));
	}

	public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<ClientboundCoolingMagmaPayload> {

		@Override
		public void receive(ClientboundCoolingMagmaPayload payload, ClientPlayNetworking.Context context) {
			BlockPos pos = payload.pos();
			ClientLevel level = context.client().level;

			assert level != null;
			RandomSource random = level.getRandom();

			level.playLocalSound(pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F, false);

			for (Direction face : Direction.values())
				addParticles(level, pos, random, ParticleTypes.LARGE_SMOKE, 2, 4, 0.7, face);
		}

		private static void addParticles(ClientLevel level, BlockPos pos, RandomSource random, ParticleOptions particleOptions, int minCount, int maxCount, double offset, Direction face) {
			for (int i = 0; i < UniformInt.of(minCount, maxCount).sample(random); i++) {
				if (!level.getBlockState(pos.relative(face)).isSolidRender()) {
					Direction.Axis axis = face.getAxis();

					double dx = axis == Direction.Axis.X ? 0.5 + offset * face.getStepX() : random.nextFloat();
					double dy = axis == Direction.Axis.Y ? 0.5 + offset * face.getStepY() : random.nextFloat();
					double dz = axis == Direction.Axis.Z ? 0.5 + offset * face.getStepZ() : random.nextFloat();

					level.addParticle(particleOptions, pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz, 0.0, 0.0, 0.0);
				}
			}
		}

	}

}
