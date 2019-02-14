package Chen.Actions.ChenActions;

import Chen.Abstracts.AbstractXAction;
import Chen.Actions.GenericActions.VFXIfAliveAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BloodShotEffect;

public class KimontonkouAction extends AbstractXAction {
    public int[] multiDamage;
    private AbstractPlayer p;


    public KimontonkouAction(AbstractPlayer p, int[] multiDamage, DamageInfo.DamageType damageType)
    {
        this.multiDamage = multiDamage;
        this.damageType = damageType;
        this.p = p;

        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        if (amount > 0) {
            //use heart multi-attack vfx
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
            {
                AbstractDungeon.actionManager.addToBottom(new VFXIfAliveAction(m, new BloodShotEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY, this.amount)));
            }

            for(int i = 0; i < amount; ++i) {
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_FAST_1"));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.p, this.multiDamage, this.damageType, AttackEffect.NONE, true));
            }

            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
            //Ensure all damage actions end before moving to next action
        }

        this.isDone = true;
    }
}
