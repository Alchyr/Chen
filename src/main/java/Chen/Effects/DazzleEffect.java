package Chen.Effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;

public class DazzleEffect extends AbstractGameEffect {
    private float x;
    private float y;

    public DazzleEffect(float x, float y) {
        this.x = x;
        this.y = y;
        this.duration = 0.2F;
    }

    public void update() {
        if (this.duration == 0.2F) {
            CardCrawlGame.sound.play("TINGSHA");

            for(int i = 0; i < 50; ++i) {
                AbstractDungeon.effectsQueue.add(new LightFlareParticleEffect(this.x, this.y, Color.WHITE));
            }
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
