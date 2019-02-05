package Chen.Actions.ChenActions;

import Chen.Actions.GenericActions.ApplyDamageAction;
import Chen.Actions.GenericActions.VFXIfAliveAction;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.actions.unique.WhirlwindAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class PhoenixEggAction extends AbstractGameAction {
    private int hits;
    private DamageInfo info;

    public PhoenixEggAction(AbstractCreature source, DamageInfo info, int hits) {
        this.setValues(null, source, info.base);
        this.info = info;
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

            AbstractDungeon.actionManager.addToTop(new PhoenixEggAction(this.source, info, --hits));

            if (Settings.FAST_MODE)
            {
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
            }
            else
            {
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.2F));
            }

            AbstractDungeon.actionManager.addToTop(new ApplyDamageAction(cardTarget, info));
            AbstractDungeon.actionManager.addToTop(new VFXIfAliveAction(cardTarget, new ExplosionSmallEffect(cardTarget.hb.cX + MathUtils.random(-35.0f * Settings.scale, 35.0f * Settings.scale), cardTarget.hb.cY + MathUtils.random(0, 20.0f * Settings.scale))));

        }

        this.isDone = true;
    }
}
