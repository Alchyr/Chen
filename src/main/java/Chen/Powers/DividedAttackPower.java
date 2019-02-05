package Chen.Powers;

import Chen.Abstracts.Power;
import Chen.ChenMod;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;

public class DividedAttackPower extends Power implements InvisiblePower {
    public static final String NAME = "DivideAttack";
    public static final PowerType TYPE = PowerType.DEBUFF;
    public static final boolean TURN_BASED = true;

    public static final String POWER_ID = ChenMod.makeID(NAME);

    private List<DamageInfo> alreadyDone;

    public DividedAttackPower(final AbstractCreature owner, final int amount)
    {
        super(NAME, TYPE, TURN_BASED, owner, null, amount);

        alreadyDone = new ArrayList<>();
    }

    //Damage reduction is done through patch, to ensure it applies first.
    //Hit doubling is done here.
    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);

        if (!alreadyDone.contains(info) && info.type == DamageInfo.DamageType.NORMAL) {
            DamageInfo copyDamageInfo = new DamageInfo(info.owner, info.output, info.type); //copy the final damage, since applyPowers won't get called
            //ChenMod.logger.info("Incoming damage: " + damageAmount);

            AbstractGameAction.AttackEffect attackEffect = AbstractGameAction.AttackEffect.NONE;

            if (AbstractDungeon.actionManager.currentAction != null && AbstractDungeon.actionManager.currentAction.actionType == AbstractGameAction.ActionType.DAMAGE)
            {
                if (AbstractDungeon.actionManager.currentAction.attackEffect != null)
                    attackEffect = AbstractDungeon.actionManager.currentAction.attackEffect;
            }

            alreadyDone.add(copyDamageInfo);

            int multiplier = (int)Math.pow(2, this.amount) - 1;

            for (int i = 0; i < multiplier; i++)
            {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(target,
                        copyDamageInfo, attackEffect));
            }
        }
    }

    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }
}
