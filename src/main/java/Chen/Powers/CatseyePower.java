package Chen.Powers;

import Chen.Abstracts.Power;
import Chen.Interfaces.BetterOnDamageGiveSubscriber;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CatseyePower extends Power implements BetterOnDamageGiveSubscriber {
    public static final String NAME = "Catseye";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;

    public CatseyePower(final AbstractCreature owner, final int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public float betterAtDamageGive(float dmg, DamageInfo.DamageType damageType, AbstractCreature target) {
        if (damageType == DamageInfo.DamageType.NORMAL)
        {
            if (target != null && target.hasPower(Hemorrhage.POWER_ID))
            {
                return dmg + (target.getPower(Hemorrhage.POWER_ID).amount * this.amount);
            }
        }
        return dmg;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this.ID));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this.ID));
    }

    @Override
    public void updateDescription() {
        if (this.amount > 1)
        {
            this.description = descriptions[1] + "#b" + this.amount + descriptions[2];
        }
        else
        {
            this.description = descriptions[0];
        }
    }
}