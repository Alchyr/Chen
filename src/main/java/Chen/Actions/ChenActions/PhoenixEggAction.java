package Chen.Actions.ChenActions;

import Chen.Actions.GenericActions.VFXIfAliveAction;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

public class PhoenixEggAction extends AbstractGameAction {
    private AbstractCard c;
    private int hits;

    public PhoenixEggAction(AbstractCreature source, AbstractCard c, int hits) {
        this.source = source;
        this.c = c;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.NONE;
        this.hits = hits;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
        }
        else if (hits > 0)
        {
            AbstractMonster cardTarget = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

            if (cardTarget != null)
            {
                AbstractDungeon.actionManager.addToTop(new PhoenixEggAction(this.source, c, --hits));

                if (Settings.FAST_MODE)
                {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
                }
                else
                {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(0.2F));
                }

                addToTop(new AttackDamageRandomEnemyAction(c, this.attackEffect));
                AbstractDungeon.actionManager.addToTop(new VFXIfAliveAction(cardTarget, new ExplosionSmallEffect(cardTarget.hb.cX + MathUtils.random(-35.0f * Settings.scale, 35.0f * Settings.scale), cardTarget.hb.cY + MathUtils.random(0, 20.0f * Settings.scale))));
            }
        }

        this.isDone = true;
    }
}
