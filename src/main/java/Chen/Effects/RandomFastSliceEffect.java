package Chen.Effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;

public class RandomFastSliceEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private Color color2;

    private static final float FAST_SLASH_DURATION = 0.1F;

    public RandomFastSliceEffect(float x, float y, Color color1, Color color2) {
        this.x = x;
        this.y = y;
        this.color = color1.cpy();
        this.color2 = color2.cpy();
        this.startingDuration = 0.1F;
        this.duration = this.startingDuration;
    }

    public void update() {
        if (MathUtils.randomBoolean()) {
            if (MathUtils.randomBoolean()) {
                CardCrawlGame.sound.playA("ATTACK_DAGGER_3", MathUtils.random(0.0F, -0.3F));
            }
            else
            {
                CardCrawlGame.sound.playA("ATTACK_DAGGER_4", MathUtils.random(0.0F, -0.3F));
            }
        } else {
            if (MathUtils.randomBoolean()) {
                CardCrawlGame.sound.playA("ATTACK_DAGGER_5", MathUtils.random(0.0F, -0.3F));
            }
            else
            {
                CardCrawlGame.sound.playA("ATTACK_DAGGER_6", MathUtils.random(0.0F, -0.3F));
            }
        }

        float angle = MathUtils.random(-180.0F, 180.0F);

        float dX = MathUtils.random(90.0F, 130.0F) * (MathUtils.randomBoolean() ? 1.0F : -1.0F);
        float dY = MathUtils.random(90.0F, 130.0F) * (MathUtils.randomBoolean() ? 1.0F : -1.0F);

        float maxScale = MathUtils.random(7.0F, 8.0F);

        AnimatedSlashEffect fastSlashEffect = new AnimatedSlashEffect(this.x, this.y, dX, dY, angle, maxScale, this.color, this.color2);

        fastSlashEffect.startingDuration = FAST_SLASH_DURATION;
        fastSlashEffect.duration = FAST_SLASH_DURATION;

        AbstractDungeon.effectsQueue.add(fastSlashEffect);
        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}