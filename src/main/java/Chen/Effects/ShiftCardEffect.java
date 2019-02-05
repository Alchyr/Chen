package Chen.Effects;

import Chen.Abstracts.ShiftChenCard;
import Chen.ChenMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ExhaustBlurEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;

public class ShiftCardEffect extends AbstractGameEffect {
    private ShiftChenCard c;
    private boolean targetForm;
    private static final float DUR = 0.5F;

    public ShiftCardEffect(ShiftChenCard c, boolean targetForm) {
        this.duration = DUR;
        this.targetForm = targetForm;
        this.c = c;
    }

    public void update() {
        if (this.duration == DUR) {
            CardCrawlGame.sound.play("SINGING_BOWL", 0.2F); //change this sound later

            int i;

            for(i = 0; i < 15; ++i) {
                AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(this.c.current_x, this.c.current_y));
            }
        }

        this.duration -= Gdx.graphics.getDeltaTime();

        if (!this.c.fadingOut) {
            this.c.fadingOut = true;
            this.c.isGlowing = false;
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
            this.c.fadingOut = false;
            this.c.targetTransparency = 1.0f;
            this.c.transparency = 1.0f;
            c.Shift(targetForm);
            c.flash(ChenMod.CHEN_COLOR);
        }
    }

    public void render(SpriteBatch sb) {
        this.c.render(sb);
    }

    public void dispose() {
    }
}