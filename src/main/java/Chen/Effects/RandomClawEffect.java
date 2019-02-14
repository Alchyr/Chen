package Chen.Effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;

import static Chen.ChenMod.logger;

public class RandomClawEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private Color color2;

    public static final float DISTANCE = 50;
    public static final float TRAVEL_DISTANCE = 200;

    public RandomClawEffect(float x, float y, Color color1, Color color2)
    {
        this.x = x;
        this.y = y;
        this.color = color1.cpy();
        this.color2 = color2.cpy();
        this.startingDuration = 0.1F;
        this.duration = this.startingDuration;
    }

    public void update() {
        if (MathUtils.randomBoolean()) {
            CardCrawlGame.sound.playA("ATTACK_DAGGER_5", MathUtils.random(0.0F, -0.3F));
        } else {
            CardCrawlGame.sound.playA("ATTACK_DAGGER_6", MathUtils.random(0.0F, -0.3F));
        }

        if (MathUtils.randomBoolean()) {
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x + 35.0F, this.y + 35.0F, 150.0F, -150.0F, -135.0F, this.color, this.color2));
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x, this.y, 150.0F, -150.0F, -135.0F, this.color, this.color2));
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x - 35.0F, this.y - 35.0F, 150.0F, -150.0F, -135.0F, this.color, this.color2));
        }
        else {
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x - 35.0F, this.y + 35.0F, -150.0F, -150.0F, -45.0F, this.color, this.color2));
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x, this.y, -150.0F, -150.0F, -45.0F, this.color, this.color2));
            AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x + 35.0F, this.y - 35.0F, -150.0F, -150.0F, -45.0F, this.color, this.color2));
        }
        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}