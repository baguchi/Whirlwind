package baguchan.whirl_wind.client.particle;

import baguchan.whirl_wind.entity.WhirlWind;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;

public class WindParticle<T extends WhirlWind> extends TextureSheetParticle {
    private final SpriteSet animatedSprite;
    @Nullable
    protected final WhirlWind whirlwind;

    public WindParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprite) {
        super(level, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);
        this.animatedSprite = sprite;
        this.whirlwind = level.getEntitiesOfClass(WhirlWind.class, new AABB(this.x - 3, this.y - 3, this.z - 3, this.x + 3, this.y + 3, this.z + 3), living -> {
            return true;
        }).getFirst();
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
        this.setSpriteFromAge(this.animatedSprite);
        if (this.whirlwind != null && this.whirlwind.isAlive()) {
            float x = (float) (this.whirlwind.getX() - this.x);
            float y = (float) (this.whirlwind.getY() - this.y);
            float z = (float) (this.whirlwind.getZ() - this.z);
            this.xd = x * 0.1;
            this.zd = z * 0.1;
            this.yd = y * 0.1;

        }

        this.xd *= 0.98F;
        this.zd *= 0.98F;
        this.yd *= 0.98F;
        this.move(this.xd, this.yd, this.zd);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public record Factory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            WindParticle particle = new WindParticle<>(level, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet());
            particle.pickSprite(this.spriteSet());
            return particle;
        }
    }
}