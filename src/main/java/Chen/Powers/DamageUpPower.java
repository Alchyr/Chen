package Chen.Powers;

import Chen.Abstracts.Power;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DamageUpPower extends Power {
    public static final String NAME = "DamageUpPower";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;

    public DamageUpPower(final AbstractCreature owner, final int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL)
            return damage * (1.0f + this.amount / 2.0f);

        return damage;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this.ID));
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.type == DamageInfo.DamageType.NORMAL)
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner, owner, this.ID, this.amount));
    }

    @Override
    public void updateDescription() {
        this.description = descriptions[0] + "#b" + (this.amount * 50) + "%"    + descriptions[1];
    }
}