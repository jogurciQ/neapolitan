package com.minecraftabnormals.neapolitan.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChimpanzeeHungryParticle extends SpriteTexturedParticle {
	private ChimpanzeeHungryParticle(ClientWorld world, double x, double y, double z) {
		super(world, x, y, z, 0.0D, 0.0D, 0.0D);
		this.motionX *= (double)0.01F;
		this.motionY *= (double)0.01F;
		this.motionZ *= (double)0.01F;
		this.motionY += 0.1D;
		this.particleScale *= 1.5F;
		this.maxAge = 16;
		this.canCollide = false;
	}

	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	public float getScale(float scaleFactor) {
		return this.particleScale * MathHelper.clamp(((float)this.age + scaleFactor) / (float)this.maxAge * 32.0F, 0.0F, 1.0F);
	}

	public void tick() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if (this.age++ >= this.maxAge) {
			this.setExpired();
		} else {
			this.move(this.motionX, this.motionY, this.motionZ);
			if (this.posY == this.prevPosY) {
				this.motionX *= 1.1D;
				this.motionZ *= 1.1D;
			}

			this.motionX *= (double)0.86F;
			this.motionY *= (double)0.86F;
			this.motionZ *= (double)0.86F;
			if (this.onGround) {
				this.motionX *= (double)0.7F;
				this.motionZ *= (double)0.7F;
			}

		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;

		public Factory(IAnimatedSprite spriteSet) {
			this.spriteSet = spriteSet;
		}

		public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			ChimpanzeeHungryParticle heartparticle = new ChimpanzeeHungryParticle(worldIn, x, y, z);
			heartparticle.selectSpriteRandomly(this.spriteSet);
			return heartparticle;
		}
	}
}
