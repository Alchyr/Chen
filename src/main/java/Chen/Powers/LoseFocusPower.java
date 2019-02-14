package Chen.Powers;

import Chen.ChenMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;

public class LoseFocusPower extends AbstractPower {
    public static final String POWER_ID = ChenMod.makeID("LoseFocusPower");
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public LoseFocusPower(AbstractCreature owner, int setAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = setAmount;
        this.updateDescription();
        this.loadRegion("bias");
        this.type = PowerType.DEBUFF;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new FocusPower(this.owner, -this.amount), -this.amount));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + "#b" + this.amount + DESCRIPTIONS[1];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}