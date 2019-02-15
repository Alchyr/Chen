package Chen.Powers;

import Chen.Abstracts.Power;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CounterPower extends Power {
    public static final String NAME = "Counter";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = true;

    private DamageInfo thornsInfo;

    public CounterPower(final AbstractCreature owner, final int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);

        this.thornsInfo = new DamageInfo(owner, this.amount, DamageInfo.DamageType.THORNS);
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);

        this.thornsInfo = new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS);
        this.updateDescription();
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type == DamageInfo.DamageType.NORMAL && info.owner != this.owner) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new DamageAction(info.owner, this.thornsInfo, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(info.owner, this.owner, new Hemorrhage(info.owner, this.owner, this.amount), this.amount));
        }

        return damageAmount;
    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    @Override
    public void updateDescription() {
        this.description = descriptions[0] + "#b" + this.amount + descriptions[1] + "#b" + this.amount + descriptions[2];
    }
}