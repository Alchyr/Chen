package Chen.Actions.ChenActions;

import Chen.Actions.GenericActions.ApplyDamageAction;
import Chen.Actions.GenericActions.VFXIfAliveAction;
import Chen.Util.DamageInfoUtil;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

public class PhoenixWingsAction extends AbstractGameAction {
    private int[] multiDamage;

    public PhoenixWingsAction(AbstractCreature source, AbstractCreature target, int[] multiDamage, DamageInfo.DamageType damageType, AttackEffect effect) {
        this.setValues(target, source);
        this.actionType = ActionType.DAMAGE;
        this.damageType = damageType;
        this.attackEffect = effect;
        this.multiDamage = multiDamage;
    }

    public void update() {
        if (target != null)
        {
            AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(source, multiDamage, damageType, attackEffect));

            int totalDamage = 0;

            for (int i = 0; i < AbstractDungeon.getMonsters().monsters.size(); i++) {
                if (!AbstractDungeon.getMonsters().monsters.get(i).isDeadOrEscaped()) {
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new InflameEffect(AbstractDungeon.getMonsters().monsters.get(i))));
                    if (i < multiDamage.length)
                        totalDamage += multiDamage[i];
                }
            }

            DamageInfo finalHitInfo = new DamageInfo(source, totalDamage, DamageInfo.DamageType.NORMAL);

            DamageInfoUtil.ApplyTargetPowers(finalHitInfo, target);

            AbstractDungeon.actionManager.addToBottom(new VFXIfAliveAction(target, new FireBurstParticleEffect(target.hb.cX, target.hb.cY)));
            AbstractDungeon.actionManager.addToBottom(new VFXIfAliveAction(target, new InflameEffect(target)));
            AbstractDungeon.actionManager.addToBottom(new ApplyDamageAction(target, finalHitInfo));
        }

        this.isDone = true;
    }
}

