package Chen.Effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

public class GashEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private Color color2;

    public GashEffect(float x, float y, Color color1, Color color2) {
        this.x = x;
        this.y = y;
        this.color = color1.cpy();
        this.color2 = color2.cpy();
        this.startingDuration = 0.1F;
        this.duration = this.startingDuration;
    }

    public void update() {
        CardCrawlGame.sound.playA("ATTACK_HEAVY", MathUtils.random(0.0F, -0.3F));
        if (MathUtils.randomBoolean()) {
            CardCrawlGame.sound.playA("ATTACK_DAGGER_5", MathUtils.random(0.0F, -0.3F));
        } else {
            CardCrawlGame.sound.playA("ATTACK_DAGGER_6", MathUtils.random(0.0F, -0.3F));
        }

        //Do something fancy here and use it.

        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
