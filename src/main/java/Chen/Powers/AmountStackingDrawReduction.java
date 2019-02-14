package Chen.Powers;

import Chen.ChenMod;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AmountStackingDrawReduction extends AbstractPower {
    public static final String POWER_ID = ChenMod.makeID("Draw Reduction");
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public AmountStackingDrawReduction(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Draw Reduction";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("lessdraw");
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
    }

    public void onInitialApplication() {
        if (amount > AbstractDungeon.player.gameHandSize)
            amount = AbstractDungeon.player.gameHandSize;

        AbstractDungeon.player.gameHandSize -= amount;
    }

    @Override
    public void stackPower(int stackAmount) {
        if (stackAmount > AbstractDungeon.player.gameHandSize)
            stackAmount = AbstractDungeon.player.gameHandSize;

        AbstractDungeon.player.gameHandSize -= stackAmount;
        super.stackPower(stackAmount);

        updateDescription();
    }

    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    public void onRemove() {
        AbstractDungeon.player.gameHandSize += this.amount;
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + "#b" + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + "#b" + this.amount + DESCRIPTIONS[2];
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}