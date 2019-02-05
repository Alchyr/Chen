package Chen.Powers;

import Chen.Abstracts.Power;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EnvenomPower;

public class Sharpen extends Power {
    public static final String NAME = "Sharpen";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    public Sharpen(final AbstractCreature owner, final int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && target != this.owner && info.type == DamageInfo.DamageType.NORMAL) {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, this.owner, new Hemorrhage(target, this.owner, this.amount), this.amount, true));
        }

    }

    @Override
    public void updateDescription() {
        this.description = descriptions[0] + "#b" + this.amount + descriptions[1];
    }
}