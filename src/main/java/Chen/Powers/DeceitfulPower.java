package Chen.Powers;

import Chen.Abstracts.Power;
import Chen.Interfaces.OnShiftSubscriber;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DeceitfulPower extends Power implements OnShiftSubscriber {
    public static final String NAME = "Deceitful";
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private boolean triggeredThisTurn = false;

    public DeceitfulPower(final AbstractCreature owner, final int amount) {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);
    }

    @Override
    public void atEndOfRound() {
        this.triggeredThisTurn = false;
    }

    @Override
    public void OnShiftForm() {
        if (!this.triggeredThisTurn)
        {
            this.triggeredThisTurn = true;
            this.flash();
            AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(randomMonster, this.owner, new Disoriented(randomMonster, this.amount), this.amount, true));
        }
    }

    @Override
    public void updateDescription() {
        this.description = descriptions[0] + "#b" + this.amount + descriptions[1];
    }
}