package Chen.Cards.Skills;

import Chen.Abstracts.BaseCard;
import Chen.Actions.GenericActions.DoublePowerAction;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.FocusPower;

import static Chen.ChenMod.makeID;

public class Clarity extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Clarity",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    public Clarity()
    {
        super(cardInfo, true);

        setExhaust(true, false);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(FocusPower.POWER_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new DoublePowerAction(p.getPower(FocusPower.POWER_ID)));
        }
    }
}