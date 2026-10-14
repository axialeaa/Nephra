package com.axialeaa.nephra.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static com.axialeaa.nephra.data.registry.ModBlocks.*;

@Mixin(RuinedPortalPiece.class)
public class RuinedPortalPieceMixin {

	@Shadow @Final private RuinedPortalPiece.Properties properties;

	@Shadow private static ProcessorRule getBlockReplaceRule(Block source, float probability, Block target) {
		throw new UnsupportedOperationException("Implemented via mixin");
	}

	@Inject(method = "makeSettings(Lnet/minecraft/core/HolderLookup$Provider;Lnet/minecraft/world/level/block/Mirror;Lnet/minecraft/world/level/block/Rotation;Lnet/minecraft/world/level/levelgen/structure/structures/RuinedPortalPiece$VerticalPlacement;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/levelgen/structure/structures/RuinedPortalPiece$Properties;)Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructurePlaceSettings;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/structures/RuinedPortalPiece;getBlockReplaceRule(Lnet/minecraft/world/level/block/Block;FLnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/levelgen/structure/templatesystem/ProcessorRule;", ordinal = 0))
	private static void replaceSomeNetherrack(HolderLookup.Provider registries, Mirror mirror, Rotation rotation, RuinedPortalPiece.VerticalPlacement verticalPlacement, BlockPos pivot, RuinedPortalPiece.Properties properties, CallbackInfoReturnable<StructurePlaceSettings> cir, @Local(name = "rules") List<ProcessorRule> rules) {
		if (properties.cold())
			rules.add(getBlockReplaceRule(Blocks.NETHERRACK, 0.15F, NEPHRA.get()));
	}

	@ModifyExpressionValue(method = "getLavaProcessorRule", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;NETHERRACK:Lnet/minecraft/world/level/block/Block;", opcode = Opcodes.GETSTATIC))
	private static Block replaceLavaInColdBiomes(Block original) {
		return NEPHRA.get();
	}

	@Inject(method = "placeNetherrackOrMagma", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;NETHERRACK:Lnet/minecraft/world/level/block/Block;", opcode = Opcodes.GETSTATIC), cancellable = true)
	private void placeSomeNephra(RandomSource random, LevelAccessor level, BlockPos pos, CallbackInfo ci) {
		if (this.properties.cold() && random.nextFloat() < 0.15F) {
			level.setBlock(pos, NEPHRA.get().defaultBlockState(), Block.UPDATE_ALL);
			ci.cancel();
		}
	}

}
