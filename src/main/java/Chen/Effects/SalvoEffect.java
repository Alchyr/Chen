package Chen.Effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.FallingIceEffect;

public class SalvoEffect extends AbstractGameEffect {
    private int salvoCount;
    private boolean flipped = false;

    public SalvoEffect(int salvoCount, boolean flipped) {
        this.salvoCount = 5 + salvoCount;
        this.flipped = flipped;
        if (this.salvoCount > 50) {
            this.salvoCount = 50;
        }

    }

    public void update() {
        CardCrawlGame.sound.playA("ORB_FROST_CHANNEL", -0.25F - (float)this.salvoCount / 200.0F);
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.MED, true);
        AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.ORANGE.cpy()));

        int sfx = 3;
        for(int i = 0; i < this.salvoCount; ++i) {
            --sfx;
            AbstractDungeon.effectsQueue.add(new FallingDarkEffect(this.salvoCount, sfx > 0, this.flipped));
        }

        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}