package Chen.Cards.Skills;

import Chen.Abstracts.BaseCard;
import Chen.Powers.PreventAllBlockPower;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Chen.ChenMod.makeID;

public class SelfConfidence extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SelfConfidence",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int SELF_DEBUFF = 1;
    private final static int UPGRADE_COST = 0;
    private final static int BUFF = 3;

    public SelfConfidence()
    {
        super(cardInfo, false);

        setCostUpgrade(UPGRADE_COST);
        setMagic(BUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(p, p));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PreventAllBlockPower(p, SELF_DEBUFF), SELF_DEBUFF));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber));
    }
}