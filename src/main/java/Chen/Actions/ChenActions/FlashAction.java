package Chen.Actions.ChenActions;

import Chen.Effects.RandomFastSliceEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FlashAction extends AbstractGameAction {
    private DamageInfo damageInfo;
    private int hits;

    public FlashAction(DamageInfo damageInfo, int hits)
    {
        this.actionType = ActionType.DAMAGE;
        this.source = damageInfo.owner;
        this.damageInfo = damageInfo;
        this.attackEffect = AttackEffect.SLASH_HORIZONTAL;
        this.hits = hits;
    }

    @Override
    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (this.target == null || hits <= 0) {
            this.isDone = true;
        } else if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
        } else {
            if (this.target.currentHealth > 0) {
                this.damageInfo.applyPowers(this.damageInfo.owner, this.target);
                AbstractDungeon.effectList.add(new RandomFastSliceEffect(target.hb.cX, target.hb.cY, Color.GOLD, Color.WHITE));
                this.target.damage(this.damageInfo);

                if (this.hits > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    --this.hits;
                    AbstractDungeon.actionManager.addToTop(new FlashAction(damageInfo, hits));
                }

                AbstractDungeon.actionManager.addToTop(new WaitAction(0.05F));
            }

            this.isDone = true;
        }
    }
}
